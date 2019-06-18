package org.team5499.monkeyLib.math.geometry

import org.team5499.monkeyLib.math.epsilonEquals
import org.team5499.monkeyLib.math.units.Length

import kotlin.math.absoluteValue

class Twist2d constructor(
    val dx: Double,
    val dy: Double,
    val dTheta: Rotation2d
) {

    constructor(
        dx: Length,
        dy: Length,
        dTheta: Rotation2d
    ) : this(dx.value, dy.value, dTheta)

    val norm get() = if (dy == 0.0) dx.absoluteValue else Math.hypot(dx, dy)

    val asPose: Pose2d
        get() {
            val dTheta = this.dTheta.radian
            val sinTheta = Math.sin(dTheta)
            val cosTheta = Math.cos(dTheta)

            val (s, c) = if (dTheta epsilonEquals 0.0) {
                1.0 - 1.0 / 6.0 * dTheta * dTheta to .5 * dTheta
            } else {
                sinTheta / dTheta to (1.0 - cosTheta) / dTheta
            }
            return Pose2d(
                    Vector2(dx * s - dy * c, dx * c + dy * s),
                    Rotation2d(cosTheta, sinTheta, false)
            )
        }

    operator fun times(scale: Double) =
            Twist2d(dx * scale, dy * scale, dTheta * scale)
}
