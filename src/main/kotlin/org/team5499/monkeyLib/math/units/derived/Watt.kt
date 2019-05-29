package org.team5499.monkeyLib.math.units.derived

import org.team5499.monkeyLib.math.units.SIValue
import org.team5499.monkeyLib.math.units.SIConstants

val Number.watt get() = Watt(toDouble())

val Number.gigawatt get() = Watt(toDouble() * SIConstants.kGiga)
val Number.megawatt get() = Watt(toDouble() * SIConstants.kMega)
val Number.kilowatt get() = Watt(toDouble() * SIConstants.kKilo)

class Watt(
    override val value: Double
) : SIValue<Watt> {
    override fun createNew(newValue: Double) = Watt(value)

    companion object {
        val kZero = Watt(0.0)
    }
}
