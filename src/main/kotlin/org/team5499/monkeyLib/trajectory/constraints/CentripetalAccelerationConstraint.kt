package org.team5499.monkeyLib.trajectory.constraints

import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature

import kotlin.math.absoluteValue
import kotlin.math.sqrt

class CentripetalAccelerationConstraint internal constructor(
    private val maxCentripetalAcceleration: Double
) : TimingConstraint<Pose2dWithCurvature> {

    override fun getMaxVelocity(state: Pose2dWithCurvature) =
        sqrt((maxCentripetalAcceleration / state.curvature).absoluteValue)

    override fun getMinMaxAcceleration(
        state: Pose2dWithCurvature,
        velocity: Double
    ) = TimingConstraint.MinMaxAcceleration.noLimits
}
