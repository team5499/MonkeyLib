package org.team5419.fault.hardware.ctre

import com.ctre.phoenix.motorcontrol.can.VictorSPX
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.units.Length
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.native.NativeUnitModel

typealias LinearMonkeySPX = BerkeliumSPX<Length>
typealias AngularMonkeySPX = BerkeliumSPX<Rotation2d>

class BerkeliumSPX<T : SIUnit<T>>(
    val victorSPX: VictorSPX,
    model: NativeUnitModel<T>
) : CTREBerkeliumMotor<T>(victorSPX, model) {

    init {
        victorSPX.configFactoryDefault()
    }

    constructor(id: Int, model: NativeUnitModel<T>): this(VictorSPX(id), model)
}
