package org.team5419.fault.trajectory.constraints

import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.units.Length
import org.team5419.fault.math.units.derived.LinearVelocity

class VelocityLimitRadiusConstraint internal constructor(
    private val point: Vector2,
    private val radius: Double,
    private val velocityLimit: Double
) : TimingConstraint<Pose2dWithCurvature> {

    constructor(
        point: Vector2,
        radius: Length,
        velocityLimit: LinearVelocity
    ) : this(point, radius.value, velocityLimit.value)

    override fun getMaxVelocity(state: Pose2dWithCurvature) =
        if (state.pose.translation.distance(point) <= radius) velocityLimit else Double.POSITIVE_INFINITY

    override fun getMinMaxAcceleration(
        state: Pose2dWithCurvature,
        velocity: Double
    ) = TimingConstraint.MinMaxAcceleration.noLimits
}
