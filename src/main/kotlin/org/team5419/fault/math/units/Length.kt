package org.team5419.fault.math.units

val Number.inch get() = Length(toDouble() * Length.kInchToMeter)
val Number.feet get() = Length(toDouble() * Length.kFeetToMeter)
val Number.kilometer get() = Length(toDouble() * SIConstants.kKilo)
val Number.meter get() = Length(toDouble())
val Number.centimeter get() = Length(toDouble() * SIConstants.kCenti)
val Number.millimeter get() = Length(toDouble() * SIConstants.kMilli)

object SILengthConstants {
    const val kInchToMeter = 0.0254
    const val kFeetToMeter = kInchToMeter * 12
}

class Length(
    override val value: Double
) : SIUnit<Length> {

    val inch get() = value / kInchToMeter
    val feet get() = value / kFeetToMeter

    val kilometer get() = value / SIConstants.kKilo
    val meter get() = value
    val centimeter get() = value / SIConstants.kCenti
    val millimeter get() = value / SIConstants.kMilli

    override fun createNew(newValue: Double): Length = Length(newValue)

    companion object {
        val kZero by lazy { Length(0.0) }

        const val kInchToMeter = 0.0254
        const val kFeetToMeter = kInchToMeter * 12.0
    }
}
