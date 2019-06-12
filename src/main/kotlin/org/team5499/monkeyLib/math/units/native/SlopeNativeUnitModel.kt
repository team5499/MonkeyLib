package org.team5499.monkeyLib.math.units.native

import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.SIUnit
import kotlin.math.PI

class SlopeNativeUnitModel<T : SIUnit<T>>(
    val modelledSample: T,
    val nativeUnitSample: NativeUnit
) : NativeUnitModel<T>(modelledSample.createNew(0.0)) {

    private val slope = modelledSample.value / nativeUnitSample.value

    override fun fromNativeUnitPosition(nativeUnits: Double) =
            nativeUnits * slope

    override fun toNativeUnitPosition(modelledUnits: Double) =
            modelledUnits / slope
}

fun SlopeNativeUnitModel<Length>.wheelRadius(sensorUnitsPerRotation: NativeUnit) =
        Length(modelledSample.value / (nativeUnitSample.value / sensorUnitsPerRotation.value) / 2.0 / PI)
