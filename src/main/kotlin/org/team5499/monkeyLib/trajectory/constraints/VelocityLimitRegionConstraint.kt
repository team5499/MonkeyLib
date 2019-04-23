package org.team5499.monkeyLib.trajectory.constraints

import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.geometry.Vector2

public class VelocityLimitRegionConstraint internal constructor(
    private val lowerLeft: Vector2,
    private val upperRight: Vector2,
    private val velocityLimit: Double
) : TimingConstraint<Pose2dWithCurvature> {

    init {
        require(lowerLeft.x < upperRight.x) {
            "Upper right x value must be larger than lower left x value"
        }
        require(lowerLeft.y < upperRight.y) {
            "Upper right y value must be larger than lower left y value"
        }
    }

    override fun getMaxVelocity(state: Pose2dWithCurvature) =
        @Suppress("ComplexCondition")
        if (
            state.translation.x >= lowerLeft.x &&
            state.translation.x <= upperRight.x &&
            state.translation.y >= lowerLeft.y &&
            state.translation.y <= upperRight.y
        ) {
            velocityLimit
        } else {
            Double.POSITIVE_INFINITY
        }

    override fun getMinMaxAcceleration(state: Pose2dWithCurvature, velocity: Double) =
        TimingConstraint.MinMaxAcceleration.noLimits
}
