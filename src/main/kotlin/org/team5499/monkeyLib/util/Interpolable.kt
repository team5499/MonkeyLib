package org.team5499.monkeyLib.util

interface Interpolable<T> {
    public fun interpolate(other: T, x: Double): T
}
