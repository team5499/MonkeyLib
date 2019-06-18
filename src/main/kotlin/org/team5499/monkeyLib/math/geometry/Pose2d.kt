package org.team5499.monkeyLib.math.geometry

import org.team5499.monkeyLib.math.epsilonEquals
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.feet

data class Pose2d(
    val translation: Vector2 = Vector2(),
    val rotation: Rotation2d = 0.degree
) : State<Pose2d> {

    constructor(
        x: Length,
        y: Length,
        rotation: Rotation2d = 0.degree
    ) : this(Vector2(x, y), rotation)

    val twist: Twist2d
        get() {
            val dtheta = rotation.radian
            val halfDTheta = dtheta / 2.0
            val cosMinusOne = rotation.cos - 1.0

            val halfThetaByTanOfHalfDTheta = if (cosMinusOne epsilonEquals 0.0) {
                1.0 - 1.0 / 12.0 * dtheta * dtheta
            } else {
                -(halfDTheta * rotation.sin) / cosMinusOne
            }
            val translationPart = translation *
                    Rotation2d(halfThetaByTanOfHalfDTheta, -halfDTheta, false)
            return Twist2d(translationPart.x, translationPart.y, rotation)
        }

    val mirror get() = Pose2d(Vector2(translation.x, 27.feet.value - translation.y), -rotation)

    infix fun inFrameOfReferenceOf(fieldRelativeOrigin: Pose2d) = (-fieldRelativeOrigin) + this

    operator fun plus(other: Pose2d) = transformBy(other)

    operator fun minus(other: Pose2d) = this + -other

    fun transformBy(other: Pose2d) =
            Pose2d(
                    translation + (other.translation * rotation),
                    rotation + other.rotation
            )

    operator fun unaryMinus(): Pose2d {
        val invertedRotation = -rotation
        return Pose2d((-translation) * invertedRotation, invertedRotation)
    }

    fun isColinear(other: Pose2d): Boolean {
        if (!rotation.isParallel(other.rotation)) return false
        val twist = (-this + other).twist
        return twist.dy epsilonEquals 0.0 && twist.dTheta.value epsilonEquals 0.0
    }

    @Suppress("ReturnCount")
    override fun interpolate(endValue: Pose2d, t: Double): Pose2d {
        if (t <= 0) {
            return Pose2d(this.translation, this.rotation)
        } else if (t >= 1) {
            return Pose2d(endValue.translation, endValue.rotation)
        }
        val twist = (-this + endValue).twist
        return this + (twist * t).asPose
    }

    override fun distance(other: Pose2d) = (-this + other).twist.norm

    override fun toCSV() = "${translation.toCSV()},${rotation.toCSV()}"
}
