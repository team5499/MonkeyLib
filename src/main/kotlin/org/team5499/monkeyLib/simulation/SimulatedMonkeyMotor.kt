package org.team5499.monkeyLib.simulation

import org.team5499.monkeyLib.hardware.MonkeyEncoder
import org.team5499.monkeyLib.hardware.MonkeyMotor
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.SIUnit

typealias LinearSimulatedMonkeyMotor = SimulatedMonkeyMotor<Length>
typealias AngularSimulatedMonkeyMotor = SimulatedMonkeyMotor<Rotation2d>

class SimulatedMonkeyMotor<T : SIUnit<T>> : MonkeyMotor<T> {

    var velocity = 0.0
    override val voltageOutput = 0.0

    override val encoder = object : MonkeyEncoder<T> {
        override val position: Double get() = rawPosition
        override val velocity: Double get() = rawVelocity
        override val rawPosition: Double get() = this@SimulatedMonkeyMotor.velocity
        override val rawVelocity: Double get() = 0.0

        override fun resetPosition(newPosition: Double) {}
    }

    override var outputInverted: Boolean
        get() = TODO("not implemented")
        set(value) {}

    override var brakeMode: Boolean
        get() = TODO("not implemented")
        set(value) {}

    override fun follow(motor: MonkeyMotor<*>): Boolean {
        TODO("not implemented")
    }

    override fun setVoltage(voltage: Double, arbitraryFeedForward: Double) {
        TODO("not implemented")
    }

    override fun setPercent(percent: Double, arbitraryFeedForward: Double) {
        TODO("not implemented")
    }

    override fun setVelocity(velocity: Double, arbitraryFeedForward: Double) {
        this.velocity = velocity
    }

    override fun setPosition(position: Double, arbitraryFeedForward: Double) {
        TODO("not implemented")
    }

    override fun setNeutral() {
        velocity = 0.0
    }

    override var voltageCompSaturation: Double
        get() = TODO("not implemented")
        set(value) {}

    override var motionProfileCruiseVelocity: Double
        get() = TODO("not implemented")
        set(value) {}

    override var motionProfileAcceleration: Double
        get() = TODO("not implemented")
        set(value) {}

    override var useMotionProfileForPosition: Boolean
        get() = TODO("not implemented")
        set(value) {}
}
