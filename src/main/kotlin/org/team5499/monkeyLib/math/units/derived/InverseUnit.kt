package org.team5499.monkeyLib.math.units.derived

import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.SIValue

typealias Curvature = InverseUnit<Length>

@Suppress("FunctionNaming")
fun Curvature(value: Double) = Curvature(value, Length.kZero)

operator fun <T : SIUnit<T>> Number.div(other: T): InverseUnit<T> =
        InverseUnit(this.toDouble(), other)

class InverseUnit<T : SIUnit<T>>(
    override val value: Double,
    val type: T
) : SIValue<InverseUnit<T>> {
    override fun createNew(newValue: Double) = InverseUnit(newValue, type)
}
