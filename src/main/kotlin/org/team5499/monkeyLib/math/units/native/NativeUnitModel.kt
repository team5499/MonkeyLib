package org.team5499.monkeyLib.math.units.native

import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.derived.Acceleration
import org.team5499.monkeyLib.math.units.derived.Velocity

@Suppress("TooManyFunctions")
abstract class NativeUnitModel<T : SIUnit<T>>(
    internal val zero: T
) {

    abstract fun fromNativeUnitPosition(nativeUnits: Double): Double
    abstract fun toNativeUnitPosition(modelledUnits: Double): Double

    open fun toNativeUnitError(modelledUnit: Double): Double =
            toNativeUnitPosition(modelledUnit) - toNativeUnitPosition(0.0)

    open fun fromNativeUnitVelocity(nativeUnitVelocity: Double) = fromNativeUnitPosition(nativeUnitVelocity)
    open fun toNativeUnitVelocity(nativeUnitVelocity: Double) = toNativeUnitPosition(nativeUnitVelocity)

    open fun fromNativeUnitAcceleration(nativeUnitAcceleration: Double) = fromNativeUnitPosition(nativeUnitAcceleration)
    open fun toNativeUnitAcceleration(nativeUnitAcceleration: Double) = toNativeUnitPosition(nativeUnitAcceleration)

    fun fromNativeUnitPosition(nativeUnits: NativeUnit) = zero.createNew(fromNativeUnitPosition(nativeUnits.value))
    fun toNativeUnitPosition(modelledUnit: T) = NativeUnit(toNativeUnitPosition(modelledUnit.value))

    fun toNativeUnitError(modelledUnit: T) = NativeUnit(toNativeUnitError(modelledUnit.value))

    fun fromNativeUnitVelocity(nativeUnitVelocity: NativeUnitVelocity) =
            Velocity(fromNativeUnitVelocity(nativeUnitVelocity.value), zero)
    fun toNativeUnitVelocity(modelledUnitVelocity: Velocity<T>) =
            NativeUnitVelocity(toNativeUnitVelocity(modelledUnitVelocity.value), NativeUnit.kZero)

    fun fromNativeUnitAcceleration(nativeUnitAcceleration: NativeUnitAcceleration) =
            Acceleration(fromNativeUnitAcceleration(nativeUnitAcceleration.value), zero)
    fun toNativeUnitAcceleration(modelledUnitAcceleration: Acceleration<T>) =
            NativeUnitAcceleration(toNativeUnitAcceleration(modelledUnitAcceleration.value), NativeUnit.kZero)
}
