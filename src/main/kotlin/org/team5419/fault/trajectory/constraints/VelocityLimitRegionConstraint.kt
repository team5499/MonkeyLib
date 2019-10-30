package org.team5419.fault.trajectory.constraints

import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.math.geometry.Rectangle2d
import org.team5419.fault.math.units.derived.LinearVelocity

public class VelocityLimitRegionConstraint internal constructor(
    private val region: Rectangle2d,
    private val velocityLimit: Double
) : TimingConstraint<Pose2dWithCurvature> {

    constructor(
        region: Rectangle2d,
        velocityLimit: LinearVelocity
    ) : this(region, velocityLimit.value)

    override fun getMaxVelocity(state: Pose2dWithCurvature) =
        if (state.pose.translation in region) velocityLimit else Double.POSITIVE_INFINITY

    override fun getMinMaxAcceleration(state: Pose2dWithCurvature, velocity: Double) =
        TimingConstraint.MinMaxAcceleration.noLimits
}
