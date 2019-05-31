package org.team5499.monkeyLib.math.units.native

import org.team5499.monkeyLib.math.units.SIUnit

fun <T : SIUnit<T>> T.toNativeUnitPosition(model: NativeUnitModel<T>) = model.toNativeUnitPosition(this)

val Number.nativeUnits get() = NativeUnit(toDouble())

class NativeUnit(
    override val value: Double
) : SIUnit<NativeUnit> {

    override fun createNew(newValue: Double) = NativeUnit(newValue)

    fun <T : SIUnit<T>> fromNativeUnit(model: NativeUnitModel<T>) = model.fromNativeUnitPosition(this)

    companion object {
        val kZero = NativeUnit(0.0)
    }
}
