package org.team5419.fault.math.units

object Second: SIKey

const val kMinuteToSecond = 60.0
const val kHourToSecond = kMinuteToSecond * 60.0

val Double.seconds get() = SIUnit<Second>(this)
val Double.minutes get() = SIUnit<Second>(times(kMinuteToSecond))
val Double.hours get() = SIUnit<Second>(times(kHourToSecond))

val Number.seconds get() = toDouble().seconds
val Number.minutes get() = toDouble().minutes
val Number.hours get() = toDouble().hours

fun SIUnit<Second>.inMinutes() = value.div(kMinuteToSecond)
fun SIUnit<Second>.inHours() = value.div(kHourToSecond)

fun SIUnit<Second>.inKiloseconds() = value.div(kKilo)
fun SIUnit<Second>.inHectoseconds() = value.div(kHecto)
fun SIUnit<Second>.inDecaseconds() = value.div(kDeca)
fun SIUnit<Second>.inSeconds() = value
fun SIUnit<Second>.inDeciseconds() = value.div(kDeci)
fun SIUnit<Second>.inCentiseconds() = value.div(kCenti)
fun SIUnit<Second>.inMilliseconds() = value.div(kMilli)
fun SIUnit<Second>.inMicroseconds() = value.div(kMicro)
fun SIUnit<Second>.inNanoseconds() = value.div(kNano)
