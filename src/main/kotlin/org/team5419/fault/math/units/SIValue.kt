package org.team5419.fault.math.units

import org.team5419.fault.math.lerp
import kotlin.math.absoluteValue
import org.team5419.fault.math.epsilonEquals

@Suppress("TooManyFunctions")
interface SIValue<T : SIValue<T>> : Comparable<T> {
    val value: Double

    fun createNew(newValue: Double): T

    val absoluteValue get() = createNew(value.absoluteValue)

    operator fun unaryMinus() = createNew(-value)

    operator fun plus(other: T) = createNew(value + other.value)
    operator fun minus(other: T) = createNew(value - other.value)

    operator fun div(other: T) = value / other.value
    operator fun rem(other: T) = value % other.value

    operator fun times(other: Number) = createNew(value * other.toDouble())
    operator fun div(other: Number) = createNew(value / other.toDouble())
    operator fun rem(other: Number) = createNew(value % other.toDouble())

    override operator fun compareTo(other: T) = value.compareTo(other.value)

    fun lerp(endValue: T, t: Double) = createNew(value.lerp(endValue.value, t))
    infix fun epsilonEquals(other: T) = this.value epsilonEquals other.value
}
