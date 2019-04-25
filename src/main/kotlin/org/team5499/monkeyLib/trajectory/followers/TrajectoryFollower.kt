package org.team5499.monkeyLib.trajectory.followers

import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.trajectory.TrajectoryIterator
import org.team5499.monkeyLib.trajectory.types.Trajectory
import org.team5499.monkeyLib.trajectory.types.TimedEntry

import edu.wpi.first.wpilibj.Timer

abstract class TrajectoryFollower {

    private var trajectoryIterator: TrajectoryIterator<Double, TimedEntry<Pose2dWithCurvature>>? = null
    private var lastTime: Double = 0.0
    private var previousVelocity: TrajectoryFollowerVelocityOutput? = null

    val referencePoint get() = trajectoryIterator?.currentState
    val isFinished get() = trajectoryIterator?.isDone ?: true

    fun reset(
        trajectory: Trajectory<Double, TimedEntry<Pose2dWithCurvature>>
    ) {
        trajectoryIterator = trajectory.iterator()
        previousVelocity = null
        lastTime = Timer.getFPGATimestamp()
    }

    fun nextState(
        currentRobotPose: Pose2d,
        currentTime: Double = Timer.getFPGATimestamp()
    ): TrajectoryFollowerOutput {
        val iterator = trajectoryIterator
        require(iterator != null) {
            "You cannot get the next state from the TrajectoryTracker without a" +
            "trajectory! Call TrajectoryTracker.reset() first!"
        }
        val dt = currentTime - lastTime
        iterator.advance(dt)
        val velocity = calculateState(iterator, currentRobotPose)
        val previousVelocity = this.previousVelocity
        this.previousVelocity = velocity

        return if (previousVelocity == null || dt <= 0.0) {
            TrajectoryFollowerOutput(
                linearVelocity = velocity.linearVelocity,
                linearAcceleration = 0.0,
                angularVelocity = velocity.angularVelocity,
                angularAcceleration = 0.0
            )
        } else {
            TrajectoryFollowerOutput(
                linearVelocity = velocity.linearVelocity,
                linearAcceleration = (velocity.linearVelocity - previousVelocity.linearVelocity) / dt,
                angularVelocity = velocity.angularVelocity,
                angularAcceleration = (velocity.angularVelocity - previousVelocity.angularVelocity) / dt
            )
        }
    }

    protected abstract fun calculateState(
        iterator: TrajectoryIterator<Double, TimedEntry<Pose2dWithCurvature>>,
        robotPose: Pose2d
    ): TrajectoryFollowerVelocityOutput

    protected data class TrajectoryFollowerVelocityOutput internal constructor(
        internal val linearVelocity: Double,
        internal val angularVelocity: Double
    )

    data class TrajectoryFollowerOutput(
        val linearVelocity: Double,
        val linearAcceleration: Double,
        val angularVelocity: Double,
        val angularAcceleration: Double
    ) {
        val differentialDriveVelocity by lazy {
            DifferentialDrive.ChassisState(
                linearVelocity,
                angularVelocity
            )
        }

        val differentialDriveAcceleration by lazy {
            DifferentialDrive.ChassisState(
                linearAcceleration,
                angularAcceleration
            )
        }
    }
}
