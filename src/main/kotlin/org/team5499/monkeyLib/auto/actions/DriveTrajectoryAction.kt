package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain

import org.team5499.monkeyLib.trajectory.types.Trajectory
import org.team5499.monkeyLib.trajectory.types.TimedEntry
import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.trajectory.followers.TrajectoryFollower

public class DriveTrajectoryAction(
    timeout: Double,
    private val drivetrain: IDrivetrain,
    private val trajectoryFollower: TrajectoryFollower,
    private val trajectory: Trajectory<Double, TimedEntry<Pose2dWithCurvature>>
) : Action(timeout) {

    override fun start() {
        trajectoryFollower.reset(trajectory)
    }

    override fun update() {
        val output = trajectoryFollower.nextState(drivetrain.pose)
        val dynamics = drivetrain.model.solveInverseDynamics(
            output.differentialDriveVelocity,
            output.differentialDriveAcceleration
        )
        // CONVERT TO INCHES/SEC??????/
        drivetrain.setVelocity(
            dynamics.wheelVelocity.left * drivetrain.model.wheelRadius,
            dynamics.wheelVelocity.right * drivetrain.model.wheelRadius,
            dynamics.voltage.left,
            dynamics.voltage.right
        )
    }

    override fun next(): Boolean {
        return trajectoryFollower.isFinished
    }
}