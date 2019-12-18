
package org.team5419.fault.math.geometry

import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.Meter
import org.team5419.fault.math.units.SIKey
import kotlin.math.hypot

fun Rotation2d.toTranslation() = Vector2(cos.meters, sin.meters)

typealias Vector2d = Vector2<Meter>

@Suppress("TooManyFunctions")
data class Vector2<T : SIKey> constructor(
    val x: SIUnit<T>,
    val y: SIUnit<T>
) : State<Vector2<T>> {

    // constructor( x: Double, y : Double) : this(SIUnit<T>(x), SIUnit<T>(y))
    constructor() : this(SIUnit<T>(0.0), SIUnit<T>(0.0))

    // Vector to Translation3d
    constructor(
        rotation: Rotation2d,
        distance: SIUnit<T> = SIUnit<T>(0.0)
    ) : this (distance * rotation.cos, distance * rotation.sin)

    val norm get() = hypot(x.value, y.value).meters

    override fun interpolate(endValue: Vector2<T>, t: Double) = when {
        t <= 0 -> this
        t >= 1 -> endValue
        else -> Vector2(
            x.lerp(endValue.x, t),
            y.lerp(endValue.y, t)
        )
    }

    override fun distance(other: Vector2<T>): Double {
        val x = this.x.value - other.x.value
        val y = this.y.value - other.y.value
        return hypot(x, y)
    }

    operator fun plus(other: Vector2<T>) = Vector2(x + other.x, y + other.y)
    operator fun minus(other: Vector2<T>) = Vector2(x - other.x, y - other.y)

    operator fun times(other: Rotation2d) = Vector2(
            x * other.cos - y * other.sin,
            x * other.sin + y * other.cos
    )

    operator fun times(other: Number): Vector2<T> {
        val factor = other.toDouble()
        return Vector2(x * factor, y * factor)
    }

    operator fun div(other: Number): Vector2<T> {
        val factor = other.toDouble()
        return Vector2(x / factor, y / factor)
    }

    operator fun unaryMinus() = Vector2(-x, -y)

    override fun toCSV() = "${x.value}, ${y.value}"

    override fun toString() = toCSV()

    override fun hashCode() = super.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other is Vector2<*>) {
            return other.x == this.x && other.y == this.y
        }
        return false
    }
}
