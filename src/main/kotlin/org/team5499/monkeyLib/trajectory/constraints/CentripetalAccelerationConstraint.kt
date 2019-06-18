package org.team5499.monkeyLib.trajectory.constraints

import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.units.derived.LinearAcceleration

import kotlin.math.absoluteValue
import kotlin.math.sqrt

class CentripetalAccelerationConstraint internal constructor(
    private val maxCentripetalAcceleration: Double
) : TimingConstraint<Pose2dWithCurvature> {

    constructor(maxCentripetalAcceleration: LinearAcceleration): this(maxCentripetalAcceleration.value)

    override fun getMaxVelocity(state: Pose2dWithCurvature) =
        sqrt((maxCentripetalAcceleration / state.curvature).absoluteValue)

    override fun getMinMaxAcceleration(
        state: Pose2dWithCurvature,
        velocity: Double
    ) = TimingConstraint.MinMaxAcceleration.noLimits
}
