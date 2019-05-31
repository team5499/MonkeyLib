package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain

import org.team5499.monkeyLib.trajectory.types.Trajectory
import org.team5499.monkeyLib.trajectory.types.TimedEntry
import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.math.units.derived.volt
import org.team5499.monkeyLib.math.units.meter
import org.team5499.monkeyLib.trajectory.followers.TrajectoryFollower

public class DriveTrajectoryAction(
    timeout: Double,
    private val drivetrain: IDrivetrain,
    private val trajectoryFollower: TrajectoryFollower,
    private val trajectory: Trajectory<Time, TimedEntry<Pose2dWithCurvature>>
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

        drivetrain.setVelocity(
                (dynamics.wheelVelocity.left * drivetrain.model.wheelRadius).meter.velocity,
                (dynamics.wheelVelocity.right * drivetrain.model.wheelRadius).meter.velocity,
            dynamics.voltage.left.volt,
            dynamics.voltage.right.volt
        )
    }

    override fun next(): Boolean {
        return trajectoryFollower.isFinished
    }
}
