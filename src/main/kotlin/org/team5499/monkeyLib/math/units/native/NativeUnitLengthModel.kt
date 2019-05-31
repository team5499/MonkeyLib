package org.team5499.monkeyLib.math.units.native

import org.team5499.monkeyLib.math.units.Length
import kotlin.math.PI

class NativeUnitLengthModel(
    nativeUnitsPerRotation: NativeUnit,
    wheelRadius: Length
) : NativeUnitModel<Length>(Length.kZero) {

    private val nativeUnitsPerRotation = nativeUnitsPerRotation.value
    private val wheelRadius = wheelRadius.value

    override fun fromNativeUnitPosition(nativeUnits: Double): Double =
            wheelRadius * ((nativeUnits / nativeUnitsPerRotation) * (2.0 * PI))

    override fun toNativeUnitPosition(modelledUnits: Double): Double =
        nativeUnitsPerRotation * (modelledUnits / (wheelRadius * (2.0 * PI)))
}
