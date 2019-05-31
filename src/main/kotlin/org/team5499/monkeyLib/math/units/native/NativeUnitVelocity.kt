package org.team5499.monkeyLib.math.units.native

import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.math.units.derived.Velocity

typealias NativeUnitVelocity = Velocity<NativeUnit>

val Number.nativeUnitsPer100ms: NativeUnitVelocity get() = NativeUnitVelocity(toDouble() * 10, NativeUnit.kZero)

operator fun NativeUnit.div(other: Time) = NativeUnitVelocity(value / other.value, this)

val NativeUnitVelocity.nativeUnitsPer100ms get() = value / 10.0

fun <T : SIUnit<T>> Velocity<T>.toNativeUnitVelocity(model: NativeUnitModel<T>): NativeUnitVelocity =
        model.toNativeUnitVelocity(this)

fun <T : SIUnit<T>> NativeUnitVelocity.fromNativeUnitVelocity(model: NativeUnitModel<T>): Velocity<T> =
        model.fromNativeUnitVelocity(this)
