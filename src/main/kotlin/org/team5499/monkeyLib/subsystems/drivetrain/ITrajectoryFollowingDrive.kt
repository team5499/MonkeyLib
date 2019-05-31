package org.team5499.monkeyLib.subsystems.drivetrain

import org.team5499.monkeyLib.hardware.LinearMonkeyMotor
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.trajectory.followers.TrajectoryFollower

interface ITrajectoryFollowingDrive {

    val leftMasterMotor: LinearMonkeyMotor
    val rightMasterMotor: LinearMonkeyMotor

    val trajectoryFollower: TrajectoryFollower

    val robotPosition: Pose2d

    fun setOutput(output: TrajectoryFollower.TrajectoryFollowerOutput)

    fun zeroOutputs() {
        leftMasterMotor.setNeutral()
        rightMasterMotor.setNeutral()
    }
}
