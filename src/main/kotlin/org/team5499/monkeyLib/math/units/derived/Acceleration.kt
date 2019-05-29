package org.team5499.monkeyLib.math.units.derived

import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.SIValue
import org.team5499.monkeyLib.math.units.Time

val <T : SIValue<T>> T.acceleration: Acceleration<T> get() = Acceleration(value, this)
val Length.acceleration: LinearAcceleration get() = Acceleration(value, this)
val Rotation2d.acceleration: AngularAcceleration get() = Acceleration(value, this)

typealias LinearAcceleration = Acceleration<Length>
typealias AngularAcceleration = Acceleration<Rotation2d>

class Acceleration<T : SIValue<T>>(
    override val value: Double,
    internal val type: T
) : SIUnit<Acceleration<T>> {

    override fun createNew(newValue: Double) = Acceleration(newValue, type)

    operator fun times(other: Time) = Velocity(value * other.value, type)
}
