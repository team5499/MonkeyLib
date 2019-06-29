package org.team5499.monkeyLib.simulation

import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Twist2d
import org.team5499.monkeyLib.math.geometry.radian
import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.subsystems.drivetrain.IDifferentialFollowerDrive
import org.team5499.monkeyLib.trajectory.followers.TrajectoryFollower

class SimulatedDifferentialDrive(
    override val differentialDrive: DifferentialDrive,
    override val leftMasterMotor: LinearSimulatedMonkeyMotor,
    override val rightMasterMotor: LinearSimulatedMonkeyMotor,
    override val trajectoryFollower: TrajectoryFollower,
    private val angularFactor: Double = 1.0

) : IDifferentialFollowerDrive {

    override var robotPosition = Pose2d()

    fun update(deltaTime: Time) {
        val wheelState = DifferentialDrive.WheelState(
                leftMasterMotor.velocity * deltaTime.value / differentialDrive.wheelRadius,
                rightMasterMotor.velocity * deltaTime.value / differentialDrive.wheelRadius
        )

        val forwardKinematics = differentialDrive.solveForwardKinematics(wheelState)

        robotPosition += Twist2d(
                forwardKinematics.linear,
                0.0,
                (forwardKinematics.angular * angularFactor).radian
        ).asPose
    }
}
