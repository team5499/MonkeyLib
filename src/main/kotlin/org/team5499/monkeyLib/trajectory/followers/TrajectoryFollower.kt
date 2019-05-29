@file:Suppress("ConstructorParameterNaming")
package org.team5499.monkeyLib.trajectory.followers

import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.trajectory.TrajectoryIterator
import org.team5499.monkeyLib.trajectory.types.Trajectory
import org.team5499.monkeyLib.trajectory.types.TimedEntry

import edu.wpi.first.wpilibj.Timer
import org.team5499.monkeyLib.math.geometry.radian
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.math.units.derived.LinearAcceleration
import org.team5499.monkeyLib.math.units.derived.AngularAcceleration
import org.team5499.monkeyLib.math.units.derived.LinearVelocity
import org.team5499.monkeyLib.math.units.derived.AngularVelocity
import org.team5499.monkeyLib.math.units.derived.acceleration
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.math.units.meter
import org.team5499.monkeyLib.math.units.millisecond
import org.team5499.monkeyLib.util.time.DeltaTime

abstract class TrajectoryFollower {

    private var trajectoryIterator: TrajectoryIterator<Time, TimedEntry<Pose2dWithCurvature>>? = null
    private var deltaTimeController = DeltaTime()
    private var previousVelocity: TrajectoryFollowerVelocityOutput? = null

    val referencePoint get() = trajectoryIterator?.currentState
    val isFinished get() = trajectoryIterator?.isDone ?: true

    fun reset(
        trajectory: Trajectory<Time, TimedEntry<Pose2dWithCurvature>>
    ) {
        trajectoryIterator = trajectory.iterator()
        deltaTimeController.reset()
        previousVelocity = null
    }

    fun nextState(
        currentRobotPose: Pose2d,
        currentTime: Time = Timer.getFPGATimestamp().millisecond
    ): TrajectoryFollowerOutput {
        val iterator = trajectoryIterator
        require(iterator != null) {
            "You cannot get the next state from the TrajectoryTracker without a" +
            "trajectory! Call TrajectoryTracker.reset() first!"
        }
        val deltaTime = deltaTimeController.updateTime(currentTime)
        iterator.advance(deltaTime)
        val velocity = calculateState(iterator, currentRobotPose)
        val previousVelocity = this.previousVelocity
        this.previousVelocity = velocity

        return if (previousVelocity == null || deltaTime.value <= 0.0) {
            TrajectoryFollowerOutput(
                _linearVelocity = velocity._linearVelocity,
                _linearAcceleration = 0.0,
                _angularVelocity = velocity._angularVelocity,
                _angularAcceleration = 0.0
            )
        } else {
            TrajectoryFollowerOutput(
                _linearVelocity = velocity._linearVelocity,
                _linearAcceleration = (velocity._linearVelocity - previousVelocity._linearVelocity) / deltaTime.value,
                _angularVelocity = velocity._angularVelocity,
                _angularAcceleration = (velocity._angularVelocity - previousVelocity._angularVelocity) / deltaTime.value
            )
        }
    }

    protected abstract fun calculateState(
        iterator: TrajectoryIterator<Time, TimedEntry<Pose2dWithCurvature>>,
        robotPose: Pose2d
    ): TrajectoryFollowerVelocityOutput

    protected data class TrajectoryFollowerVelocityOutput internal constructor(
        internal val _linearVelocity: Double,
        internal val _angularVelocity: Double
    ) {
        constructor(
            linearVelocity: LinearVelocity,
            angularVelocity: AngularVelocity
        ) : this(
                _linearVelocity = linearVelocity.value,
                _angularVelocity = angularVelocity.value
        )
    }

    data class TrajectoryFollowerOutput internal constructor(
        internal val _linearVelocity: Double,
        internal val _linearAcceleration: Double,
        internal val _angularVelocity: Double,
        internal val _angularAcceleration: Double
    ) {
        val linearVelocity get() = _linearVelocity.meter.velocity
        val linearAcceleration get() = _linearAcceleration.meter.acceleration
        val angularVelocity get() = _angularVelocity.radian.velocity
        val angularAcceleration get() = _angularAcceleration.radian.acceleration

        val differentialDriveVelocity
            get() = DifferentialDrive.ChassisState(
                _linearVelocity,
                _angularVelocity
            )

        val differentialDriveAcceleration
            get() = DifferentialDrive.ChassisState(
                    _linearAcceleration,
                    _angularAcceleration
            )

        constructor(
            linearVelocity: LinearVelocity,
            linearAcceleration: LinearAcceleration,
            angularVelocity: AngularVelocity,
            angularAcceleration: AngularAcceleration
        ) : this(
                _linearVelocity = linearVelocity.value,
                _linearAcceleration = linearAcceleration.value,
                _angularVelocity = angularVelocity.value,
                _angularAcceleration = angularAcceleration.value
        )
    }
}
