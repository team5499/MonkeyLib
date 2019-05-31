package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.trajectory.types.Trajectory
import org.team5499.monkeyLib.trajectory.types.TimedEntry
import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.subsystems.drivetrain.AbstractTankDrive
import org.team5499.monkeyLib.trajectory.followers.TrajectoryFollower

public class DriveTrajectoryAction(
    timeout: Double,
    private val drivetrain: AbstractTankDrive,
    private val trajectoryFollower: TrajectoryFollower,
    private val trajectory: Trajectory<Time, TimedEntry<Pose2dWithCurvature>>
) : Action(timeout) {

    override fun start() {
        trajectoryFollower.reset(trajectory)
    }

    override fun update() {
        drivetrain.setOutput(trajectoryFollower.nextState(drivetrain.robotPosition))
        val referencePoint = trajectoryFollower.referencePoint
        if (referencePoint != null) {
            val referencePose = referencePoint.state.state.pose
        }
    }

    override fun next(): Boolean {
        return trajectoryFollower.isFinished
    }

    override fun finish() {
        drivetrain.zeroOutputs()
    }
}
