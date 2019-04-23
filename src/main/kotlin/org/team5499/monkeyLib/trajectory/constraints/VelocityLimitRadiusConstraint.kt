package org.team5499.monkeyLib.trajectory.constraints

import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.geometry.Vector2

class VelocityLimitRadiusConstraint internal constructor(
    private val point: Vector2,
    private val radius: Double,
    private val velocityLimit: Double
) : TimingConstraint<Pose2dWithCurvature> {

    override fun getMaxVelocity(state: Pose2dWithCurvature) =
        if (state.pose.translation.distance(point) <= radius) velocityLimit else Double.POSITIVE_INFINITY

    override fun getMinMaxAcceleration(
        state: Pose2dWithCurvature,
        velocity: Double
    ) = TimingConstraint.MinMaxAcceleration.noLimits
}
