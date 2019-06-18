package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.trajectory.types.Trajectory
import org.team5499.monkeyLib.trajectory.types.TimedEntry
import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.subsystems.drivetrain.AbstractTankDrive
import org.team5499.monkeyLib.trajectory.followers.TrajectoryFollower

public class DriveTrajectoryAction(
    private val drivetrain: AbstractTankDrive,
    private val trajectoryFollower: TrajectoryFollower,
    private val trajectory: Trajectory<Time, TimedEntry<Pose2dWithCurvature>>
) : Action() {

    init {
        finishCondition += { timedOut() }
        finishCondition += { trajectoryFollower.isFinished }
    }

    override fun start() {
        trajectoryFollower.reset(trajectory)
    }

    override fun update() {
        drivetrain.setOutput(trajectoryFollower.nextState(drivetrain.robotPosition))
//        val referencePoint = trajectoryFollower.referencePoint
//        if (referencePoint != null) {
//            val referencePose = referencePoint.state.state.pose
//        }
    }

    override fun finish() {
        drivetrain.zeroOutputs()
    }
}
