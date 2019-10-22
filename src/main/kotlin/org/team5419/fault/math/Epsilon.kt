package org.team5419.fault.math

object Epsilon {

    public const val EPSILON = 1E-5

    public fun epsilonEquals(value: Double, other: Double, epsilon: Double = EPSILON): Boolean {
        return (Math.abs(value - other) < epsilon)
    }

    public fun epsilonEquals(value: Double, epsilon: Double = EPSILON) =
        epsilonEquals(value, 0.0, epsilon)
}
