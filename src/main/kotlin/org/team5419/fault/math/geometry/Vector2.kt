package org.team5419.fault.math.geometry

import org.team5419.fault.math.units.*
import kotlin.math.hypot

fun Rotation2d.toTranslation() = Vector2(cos.meters, sin.meters)

data class Vector2 constructor(
    val x: SIUnit<Meter>,
    val y: SIUnit<Meter>
) : State<Vector2> {

    constructor() : this(0.0.meters, 0.0.meters)

    // Vector to Translation3d
    constructor(
        distance: SIUnit<Meter> = 0.0.meters,
        rotation: Rotation2d = Rotation2d()
    ) : this(distance * rotation.cos, distance * rotation.sin)

    val norm get() = hypot(x.value, y.value).meters

    override fun interpolate(endValue: Vector2, t: Double) = when {
        t <= 0 -> this
        t >= 1 -> endValue
        else -> Vector2(
                x.lerp(endValue.x, t),
                y.lerp(endValue.y, t)
        )
    }

    override fun distance(other: Vector2): Double {
        val x = this.x.value - other.x.value
        val y = this.y.value - other.y.value
        return hypot(x, y)
    }

    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)

    operator fun times(other: Rotation2d) = Vector2(
            x * other.cos - y * other.sin,
            x * other.sin + y * other.cos
    )

    operator fun times(other: Number): Vector2 {
        val factor = other.toDouble()
        return Vector2(x * factor, y * factor)
    }

    operator fun div(other: Number): Vector2 {
        val factor = other.toDouble()
        return Vector2(x / factor, y / factor)
    }

    operator fun unaryMinus() = Vector2(-x, -y)

    override fun toCSV() = "${x.value}, ${y.value}"

    override fun toString() = toCSV()

    override fun hashCode() = super.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other is Vector2) {
            if (other.x epsilonEquals this.x && other.y epsilonEquals this.y) return true
        }
        return false
    }
}
