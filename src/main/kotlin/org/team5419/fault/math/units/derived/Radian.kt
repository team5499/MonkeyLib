package org.team5419.fault.math.units.derived

import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.Unitless

typealias Radian = Unitless

val Double.radians get() = SIUnit<Radian>(this)
val Double.degrees get() = SIUnit<Radian>(Math.toRadians(this))

val Number.radians get() = toDouble().radians
val Number.degrees get() = toDouble().degrees

fun SIUnit<Radian>.inRadians() = value
fun SIUnit<Radian>.inDegrees() = Math.toDegrees(value)

fun SIUnit<Radian>.toRotation2d() = Rotation2d(inRadians())
fun Rotation2d.toUnbounded() = SIUnit<Radian>(radian)
