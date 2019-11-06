package org.team5419.fault.math.units.derived

import com.sun.jdi.event.MethodEntryEvent
import org.team5419.fault.math.units.*

typealias Volt = Frac<Mult<Kilogram, Mult<Meter, Meter>>,
        Mult<Ampere, Mult<Second, Mult<Second, Second>>>>

val Double.volts get() = SIUnit<Volt>(this)

val Number.volts get() = toDouble().volts