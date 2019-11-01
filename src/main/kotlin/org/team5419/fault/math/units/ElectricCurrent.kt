package org.team5419.fault.math.units

val Number.amp get() = ElectricCurrent(toDouble())
val Number.milliamp get() = ElectricCurrent(toDouble() * SIConstants.kMilli)

class ElectricCurrent(
    override val value: Double
) : SIUnit<ElectricCurrent> {

    val amp get() = value
    val milliamp get() = value / SIConstants.kMilli

    override fun createNew(newValue: Double) = ElectricCurrent(newValue)

    companion object {
        val kZero by lazy { ElectricCurrent(0.0) }
    }
}
