package org.team5419.fault.hardware

import org.team5419.fault.math.units.SIUnit

interface BerkeliumEncoder<T : SIUnit<T>> {

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
