package org.team5419.fault.simulation

import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Twist2d
import org.team5419.fault.math.geometry.radian
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.units.Time
import org.team5419.fault.subsystems.drivetrain.IDifferentialFollowerDrive
import org.team5419.fault.trajectory.followers.TrajectoryFollower

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
