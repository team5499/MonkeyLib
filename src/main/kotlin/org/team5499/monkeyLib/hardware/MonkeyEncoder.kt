package org.team5499.monkeyLib.hardware

import org.team5499.monkeyLib.math.units.SIUnit

interface MonkeyEncoder<T : SIUnit<T>> {

    /**
     * The velocity of the encoder in [T]/s
     */
    val velocity: Double
    /**
     * The position of the encoder in [T]
     */
    val position: Double

    /**
     * The velocity of the encoder in NativeUnits/s
     */
    val rawVelocity: Double
    /**
     * The position of the encoder in NativeUnits
     */
    val rawPosition: Double

    fun resetPosition(newPosition: Double = 0.0)
}
