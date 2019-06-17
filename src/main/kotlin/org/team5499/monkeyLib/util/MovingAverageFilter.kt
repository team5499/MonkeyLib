package org.team5499.monkeyLib.util

class MovingAverageFilter(
    private val weight: Double,
    private val initial: Double = 0.0
) {

    private var prevValue = 0.0

    fun update(currentValue: Double): Double {
        prevValue = prevValue * (weight) + (1.0 - weight) * currentValue
        return prevValue
    }

    fun reset() {
        prevValue = initial
    }

    fun getLast() = prevValue
}
