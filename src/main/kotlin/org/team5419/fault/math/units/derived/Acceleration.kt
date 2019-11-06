package org.team5419.fault.math.units.derived

import org.team5419.fault.math.units.*

typealias Acceleration<T> = Frac<T, Mult<Second, Second>>
typealias LinearAcceleration = Acceleration<Meter>
typealias AngularAcceleration = Acceleration<Radian>

val <K : SIKey> SIUnit<K>.acceleration get() = SIUnit<Acceleration<K>>(value)