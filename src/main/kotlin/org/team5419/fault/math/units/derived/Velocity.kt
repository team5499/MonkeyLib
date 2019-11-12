package org.team5419.fault.math.units.derived

import org.team5419.fault.math.units.*

typealias Velocity<T> = Frac<T, Second>
typealias LinearVelocity = Velocity<Meter>
typealias AngularVelocity = Velocity<Radian>

val <K : SIKey> SIUnit<K>.velocity get() = SIUnit<Velocity<K>>(value)
fun SIUnit<LinearVelocity>.inFeetPerSecond() = value.div(kFeetToMeter)
fun SIUnit<LinearVelocity>.inFeetPerMinute() = inFeetPerSecond().times(kMinuteToSecond)
fun SIUnit<LinearVelocity>.inInchesPerSecond() = value.div(kInchToMeter)
