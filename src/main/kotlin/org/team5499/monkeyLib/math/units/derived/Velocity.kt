package org.team5499.monkeyLib.math.units.derived

import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.units.SIValue
import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.meter
import org.team5499.monkeyLib.math.units.minute

val <T : SIValue<T>> T.velocity: Velocity<T> get() = Velocity(value, this)
val Length.velocity: LinearVelocity get() = Velocity(value, this)
val Rotation2d.velocity: AngularVelocity get() = Velocity(value, this) // fix

typealias LinearVelocity = Velocity<Length>
typealias AngularVelocity = Velocity<Rotation2d> // fix this pls

private val meterToFeet = 1.meter.feet
private val meterToInches = 1.meter.inch
private val secondsPerMinute = 1.minute.second

val LinearVelocity.feetPerSecond get() = value * meterToFeet
val LinearVelocity.feetPerMinute get() = feetPerSecond * secondsPerMinute
val LinearVelocity.inchesPerSecond get() = value * meterToInches

class Velocity<T : SIValue<T>>(
    override val value: Double,
    internal val type: T
) : SIUnit<Velocity<T>> {

    override fun createNew(newValue: Double) = Velocity(newValue, type)

    operator fun times(other: Time) = type.createNew(value * other.value)
    operator fun div(other: Time) = Acceleration(value / other.value, type)
    operator fun div(other: Acceleration<T>) = Time(value / other.value)
}
