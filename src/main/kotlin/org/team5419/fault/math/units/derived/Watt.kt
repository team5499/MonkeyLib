package org.team5419.fault.math.units.derived

import org.team5419.fault.math.units.*

typealias Watt = Frac<Mult<Kilogram, Mult<Meter, Meter>>, Mult<Second, Mult<Second, Second>>>

val Double.watts get() = SIUnit<Watt>(this)
val Number.watts get() = toDouble().watts
