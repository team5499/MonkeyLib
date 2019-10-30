package org.team5419.fault.subsystems.drivetrain

import org.team5419.fault.hardware.LinearBerkeliumMotor
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.trajectory.followers.TrajectoryFollower

interface ITrajectoryFollowingDrive {

    val leftMasterMotor: LinearBerkeliumMotor
    val rightMasterMotor: LinearBerkeliumMotor

    val trajectoryFollower: TrajectoryFollower

    val robotPosition: Pose2d

    fun setOutput(output: TrajectoryFollower.TrajectoryFollowerOutput)

    fun zeroOutputs() {
        leftMasterMotor.setNeutral()
        rightMasterMotor.setNeutral()
    }
}
