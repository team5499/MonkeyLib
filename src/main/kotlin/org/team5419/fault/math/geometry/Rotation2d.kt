package org.team5419.fault.math.geometry

import org.team5419.fault.math.epsilonEquals
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.util.CSVWritable

val Number.radian get() = Rotation2d(toDouble())
val Number.degree get() = Math.toRadians(toDouble()).radian

class Rotation2d : SIUnit<Rotation2d>, CSVWritable {

    override val value: Double
    val cos: Double
    val sin: Double

    constructor(value: Double) : this(Math.cos(value), Math.sin(value), true)

    constructor(x: Double, y: Double, normalize: Boolean) {
        if (normalize) {
            val magnitude = Math.hypot(x, y)
            if (!(magnitude epsilonEquals 0.0)) {
                sin = y / magnitude
                cos = x / magnitude
            } else {
                sin = 0.0
                cos = 1.0
            }
        } else {
            cos = x
            sin = y
        }
        value = Math.atan2(sin, cos)
    }

    val radian get() = value // should be between -PI and PI already. // % (Math.PI * 2)
    val degree get() = Math.toDegrees(value)

    fun isParallel(rotation: Rotation2d) = this.radian epsilonEquals rotation.radian

    override fun plus(other: Rotation2d): Rotation2d {
        return Rotation2d(
                cos * other.cos - sin * other.sin,
                cos * other.sin + sin * other.cos,
                true
        )
    }

    override fun minus(other: Rotation2d) = plus(-other)

    override fun createNew(newValue: Double) = Rotation2d(newValue)

    override fun equals(other: Any?) = other is Rotation2d && this.value epsilonEquals other.value

    override fun hashCode() = this.value.hashCode()

    override fun toCSV() = degree.toString()

    companion object {
        val kZero = Rotation2d(0.0)
        val kRotation = 360.degree
    }
}
