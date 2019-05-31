package org.team5499.monkeyLib.math.units.native

import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.derived.Acceleration

typealias NativeUnitAcceleration = Acceleration<NativeUnit>

fun <T : SIUnit<T>> Acceleration<T>.toNativeUnitAcceleration(model: NativeUnitModel<T>): NativeUnitAcceleration =
        model.toNativeUnitAcceleration(this)
fun <T : SIUnit<T>> NativeUnitAcceleration.fromNativeUnitAcceleration(model: NativeUnitModel<T>): Acceleration<T> =
        model.fromNativeUnitAcceleration(this)

val Number.nativeUnitsPer100msPerSecond get() = NativeUnitAcceleration(toDouble() * 10.0, NativeUnit.kZero)
val NativeUnitAcceleration.nativeUnitsPer100msPerSecond get() = value / 10.0
