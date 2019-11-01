package org.team5419.fault.math.units

val Number.lb get() = Mass(toDouble() * Mass.kLbOffsetKilo)
val Number.kilogram get() = Mass(toDouble())
val Number.gram get() = Mass(toDouble() * SIConstants.kBaseOffsetKilo)
val Number.milligram get() = Mass(toDouble() * SIConstants.kMilliOffsetKilo)

class Mass(
    override val value: Double
) : SIUnit<Mass> {

    val lb get() = value / kLbOffsetKilo

    val kilogram get() = value
    val gram get() = value / SIConstants.kBaseOffsetKilo
    val milligram get() = value / SIConstants.kMilliOffsetKilo

    override fun createNew(newValue: Double) = Mass(newValue)

    companion object {
        val kZero by lazy { Mass(0.0) }

        const val kLbOffsetKilo = 0.453592
    }
}
