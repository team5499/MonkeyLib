package org.team5419.fault.simulation

import org.team5419.fault.hardware.BerkeliumEncoder
import org.team5419.fault.hardware.BerkeliumMotor
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.units.Length
import org.team5419.fault.math.units.SIUnit

typealias LinearSimulatedMonkeyMotor = SimulatedBerkeliumMotor<Length>
typealias AngularSimulatedMonkeyMotor = SimulatedBerkeliumMotor<Rotation2d>

class SimulatedBerkeliumMotor<T : SIUnit<T>> : BerkeliumMotor<T> {

    var velocity = 0.0
    override val voltageOutput = 0.0

    override val encoder = object : BerkeliumEncoder<T> {
        override val position: Double get() = rawPosition
        override val velocity: Double get() = rawVelocity
        override val rawPosition: Double get() = this@SimulatedBerkeliumMotor.velocity
        override val rawVelocity: Double get() = 0.0

        override fun resetPosition(newPosition: Double) {}
    }

    override var outputInverted: Boolean
        get() = TODO("not implemented")
        set(value) {}

    override var brakeMode: Boolean
        get() = TODO("not implemented")
        set(value) {}

    override fun follow(motor: BerkeliumMotor<*>): Boolean {
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
