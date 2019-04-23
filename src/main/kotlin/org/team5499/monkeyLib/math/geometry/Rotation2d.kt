package org.team5499.monkeyLib.math.geometry

import org.team5499.monkeyLib.math.Epsilon

import java.text.DecimalFormat

@Suppress("TooManyFunctions")
class Rotation2d(x: Double, y: Double, normalize: Boolean) : State<Rotation2d> {

    companion object {
        val identity = Rotation2d()

        fun fromRadians(radians: Double): Rotation2d {
            return Rotation2d(Math.cos(radians), Math.sin(radians), false)
        }

        fun fromDegrees(degrees: Double): Rotation2d {
            return fromRadians(Math.toRadians(degrees))
        }

        fun fromDegrees(degrees: Int) = fromDegrees(degrees.toDouble())
    }

    val cosAngle: Double
    val sinAngle: Double
    val tan: Double
        get() {
            if (Math.abs(cosAngle) < Epsilon.EPSILON) {
                if (sinAngle > 0.0) return Double.POSITIVE_INFINITY
                else return Double.NEGATIVE_INFINITY
            }
            return sinAngle / cosAngle
        }
    val radians: Double
        get() = Math.atan2(sinAngle, cosAngle)
    val degrees: Double
        get() = Math.toDegrees(radians)

    init {
        if (normalize) {
            val mag = Math.hypot(x, y)
            if (mag > Epsilon.EPSILON) {
                cosAngle = x / mag
                sinAngle = y / mag
            } else {
                cosAngle = 0.0
                sinAngle = 1.0
            }
        } else {
            cosAngle = x
            sinAngle = y
        }
    }

    constructor(): this(1.0, 0.0, false)
    constructor(other: Rotation2d): this(other.cosAngle, other.sinAngle, false)
    constructor(translation: Vector2, normalize: Boolean): this(translation.x, translation.y, normalize)
    constructor(x: Int, y: Int, normalize: Boolean): this(x.toDouble(), y.toDouble(), normalize)

    fun equals(other: Rotation2d): Boolean {
        val diff = Math.abs(other.degrees - degrees)
        if (diff < Epsilon.EPSILON) return false
        return true
    }

    operator fun plus(other: Rotation2d): Rotation2d = fromDegrees(degrees + other.degrees)
    operator fun minus(other: Rotation2d): Rotation2d = fromDegrees(degrees - other.degrees)

    operator fun unaryMinus() = inverse()

    fun rotateBy(other: Rotation2d): Rotation2d {
        return Rotation2d(
            cosAngle * other.cosAngle - sinAngle * other.sinAngle,
            cosAngle* other.sinAngle + sinAngle * other.cosAngle,
            true
        )
    }

    fun normal() = Rotation2d(-cosAngle, sinAngle, false)

    fun inverse() = Rotation2d(cosAngle, -sinAngle, false)

    @Suppress("ReturnCount")
    override fun interpolate(other: Rotation2d, x: Double): Rotation2d {
        if (x <= 0) {
            return Rotation2d(this)
        } else if (x >= 1) {
            return Rotation2d(other)
        }
        val angleDiff = inverse().rotateBy(other).radians
        return this.rotateBy(Rotation2d.fromRadians(angleDiff * x))
    }

    fun isParallel(other: Rotation2d): Boolean {
        val temp = Vector2.cross(toVector(), other.toVector())
        return Math.abs(temp) < Epsilon.EPSILON
    }

    fun toVector(): Vector2 {
        return Vector2(cosAngle, sinAngle)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Rotation2d) return false
        return degrees == other.degrees
    }

    override fun toCSV(): String {
        val format = DecimalFormat("#0.000")
        return format.format(degrees)
    }

    override fun toString(): String {
        val format = DecimalFormat("#0.000")
        return "${format.format(degrees)} degrees"
    }

    override fun distance(other: Rotation2d): Double {
        return inverse().rotateBy(other).radians
    }

    override fun hashCode() = super.hashCode()
}
