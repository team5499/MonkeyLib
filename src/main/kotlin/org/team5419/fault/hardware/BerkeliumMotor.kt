package org.team5419.fault.hardware

import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.units.Length
import org.team5419.fault.math.units.SIUnit

typealias LinearBerkeliumMotor = BerkeliumMotor<Length>
typealias AngularBerkeliumMotor = BerkeliumMotor<Rotation2d>

interface BerkeliumMotor<T : SIUnit<T>> {

    /**
     * The encoder attached to the motor
     */
    val encoder: BerkeliumEncoder<T>
    /**
     * The voltage output of the motor controller in volts
     */
    val voltageOutput: Double

    /**
     * Inverts the output given to the motor
     */
    var outputInverted: Boolean

    /**
     *  When enabled, motor leads are commonized electrically to reduce motion
     */
    var brakeMode: Boolean

    /**
     * Configures the max voltage output given to the motor
     */
    var voltageCompSaturation: Double

    /**
     *  Peak target velocity that the on board motion profile generator will use
     *  Unit is [T]/s
     */
    var motionProfileCruiseVelocity: Double
    /**
     *  Acceleration that the on board motion profile generator will
     *  Unit is [T]/s/s
     */
    var motionProfileAcceleration: Double
    /**
     * Enables the use of on board motion profiling for position mode
     */
    var useMotionProfileForPosition: Boolean

    fun follow(motor: BerkeliumMotor<*>): Boolean

    /**
     * Sets the output [voltage] in volts and [arbitraryFeedForward] in volts
     */
    fun setVoltage(voltage: Double, arbitraryFeedForward: Double = 0.0)

    /**
     * Sets the output [percent] in percent and [arbitraryFeedForward] in volts
     */
    fun setPercent(percent: Double, arbitraryFeedForward: Double = 0.0)

    /**
     * Sets the output [velocity] in [T]/s and [arbitraryFeedForward] in volts
     */
    fun setVelocity(velocity: Double, arbitraryFeedForward: Double = 0.0)

    /**
     * Sets the output [position] in [T] and [arbitraryFeedForward] in volts
     */
    fun setPosition(position: Double, arbitraryFeedForward: Double = 0.0)

    /**
     * Sets the output of the motor to neutral
     */
    fun setNeutral()
}
