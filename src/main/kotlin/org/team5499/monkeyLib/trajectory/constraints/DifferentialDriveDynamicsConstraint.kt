package org.team5499.monkeyLib.trajectory.constraints

import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature

class DifferentialDriveDynamicsConstraint internal constructor(
    private val drive: DifferentialDrive,
    private val maxVoltage: Double
) : TimingConstraint<Pose2dWithCurvature> {

    override fun getMaxVelocity(state: Pose2dWithCurvature) =
        drive.getMaxAbsVelocity(state.curvature, maxVoltage)

    override fun getMinMaxAcceleration(
        state: Pose2dWithCurvature,
        velocity: Double
    ): TimingConstraint.MinMaxAcceleration {
        val minMax = drive.getMinMaxAcceleration(
            DifferentialDrive.ChassisState(velocity, velocity * state.curvature),
            state.curvature,
            maxVoltage
        )
        return TimingConstraint.MinMaxAcceleration(minMax.min, minMax.max)
    }
}
