package org.team5419.fault.math.units.native

import org.team5419.fault.math.geometry.Rotation2d
import kotlin.math.PI

class NativeUnitRotationModel(
    nativeUnitsPerRotation: NativeUnit
) : NativeUnitModel<Rotation2d>(Rotation2d.kZero) {

    private val nativeUnitsPerRotation = nativeUnitsPerRotation.value

    override fun toNativeUnitPosition(modelledUnits: Double): Double =
            (modelledUnits / (2.0 * PI)) * nativeUnitsPerRotation
    override fun fromNativeUnitPosition(nativeUnits: Double): Double =
            2.0 * PI * (nativeUnits / nativeUnitsPerRotation)
}
