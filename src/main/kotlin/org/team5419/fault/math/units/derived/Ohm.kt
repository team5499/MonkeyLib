package org.team5419.fault.math.units.derived

import org.team5419.fault.math.units.*

typealias Ohm = Frac<Mult<Kilogram, Mult<Meter, Meter>>,
        Mult<Second, Mult<Second, Mult<Second, Mult<Ampere, Ampere>>>>>

val Double.ohms get() = SIUnit<Ohm>(this)

val Number.ohms get() = toDouble().ohms
