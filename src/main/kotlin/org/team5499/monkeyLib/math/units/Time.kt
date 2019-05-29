package org.team5499.monkeyLib.math.units

val Number.minute get() = Time(toDouble() * Time.kMinuteToSecond)
val Number.hour get() = Time(toDouble() * Time.kHourToSecond)

val Number.second get() = Time(toDouble())
val Number.millisecond get() = Time(toDouble() * SIConstants.kMilli)
val Number.microsecond get() = Time(toDouble() * SIConstants.kMicro)
val Number.nanosecond get() = Time(toDouble() * SIConstants.kNano)

class Time(
    override val value: Double
) : SIUnit<Time> {

    val minute get() = value / kMinuteToSecond
    val hour get() = value / kHourToSecond

    val second get() = value

    val millisecond get() = value / SIConstants.kMilli
    val microsecond get() = value / SIConstants.kMicro
    val nanosecond get() = value / SIConstants.kNano

    override fun createNew(newValue: Double) = Time(newValue)

    companion object {
        val kZero by lazy { Time(0.0) }

        const val kMinuteToSecond = 60.0
        const val kHourToSecond = kMinuteToSecond * 60.0
    }
}
