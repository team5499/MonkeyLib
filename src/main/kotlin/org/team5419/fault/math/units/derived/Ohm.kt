package org.team5419.fault.math.units.derived

import org.team5419.fault.math.units.SIConstants
import org.team5419.fault.math.units.SIValue

val Number.ohm get() = Ohm(toDouble())
val Number.kiloohm get() = Ohm(toDouble() * SIConstants.kKilo)
val Number.milliohm get() = Ohm(toDouble() * SIConstants.kMilli)

class Ohm(
    override val value: Double
) : SIValue<Ohm> {
    override fun createNew(newValue: Double) = Ohm(value)

    companion object {
        val kZero = Ohm(0.0)
    }
}
