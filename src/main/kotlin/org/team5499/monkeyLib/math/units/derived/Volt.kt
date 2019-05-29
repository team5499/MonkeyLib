package org.team5499.monkeyLib.math.units.derived

import org.team5499.monkeyLib.math.units.ElectricCurrent
import org.team5499.monkeyLib.math.units.SIValue
import org.team5499.monkeyLib.math.units.SIConstants

val Number.volt get() = Volt(toDouble())
val Number.millivolt get() = Volt(toDouble() * SIConstants.kMilli)
val Number.microvolt get() = Volt(toDouble() * SIConstants.kMicro)

class Volt(
    override val value: Double
) : SIValue<Volt> {
    override fun createNew(newValue: Double) = Volt(value)

    operator fun times(other: ElectricCurrent) = Watt(value * other.value)
    operator fun div(other: ElectricCurrent) = Ohm(value / other.value)

    companion object {
        val kZero = Volt(0.0)
    }
}
