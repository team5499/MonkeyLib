package org.team5499.monkeyLib.math

fun Double.lerp(endValue: Double, t: Double) = this + (endValue - this) * t.coerceIn(0.0, 1.0)
