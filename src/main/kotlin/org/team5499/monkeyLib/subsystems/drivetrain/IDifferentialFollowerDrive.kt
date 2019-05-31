package org.team5499.monkeyLib.subsystems.drivetrain

import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.trajectory.followers.TrajectoryFollower.TrajectoryFollowerOutput

interface IDifferentialFollowerDrive : ITrajectoryFollowingDrive {

    val differentialDrive: DifferentialDrive

    override fun setOutput(output: TrajectoryFollowerOutput) {
        setOutputFromDynamics(
                output.differentialDriveVelocity,
                output.differentialDriveAcceleration
        )
    }

    fun setOutputFromKinematics(chassisVelocity: DifferentialDrive.ChassisState) {
        val wheelVelocities = differentialDrive.solveInverseKinematics(chassisVelocity)
        val feedForwardVoltages = differentialDrive.getVoltagesFromkV(wheelVelocities)

        setOutput(wheelVelocities, feedForwardVoltages)
    }

    fun setOutputFromDynamics(
        chassisVelocity: DifferentialDrive.ChassisState,
        chassisAcceleration: DifferentialDrive.ChassisState
    ) {
        val dynamics = differentialDrive.solveInverseDynamics(chassisVelocity, chassisAcceleration)

        setOutput(dynamics.wheelVelocity, dynamics.voltage)
    }

    fun setOutput(
        wheelVelocities: DifferentialDrive.WheelState,
        wheelVoltages: DifferentialDrive.WheelState
    ) {
        leftMasterMotor.setVelocity(
                wheelVelocities.left * differentialDrive.wheelRadius,
                wheelVoltages.left
        )
        rightMasterMotor.setVelocity(
                wheelVelocities.right * differentialDrive.wheelRadius,
                wheelVoltages.right
        )
    }
}
