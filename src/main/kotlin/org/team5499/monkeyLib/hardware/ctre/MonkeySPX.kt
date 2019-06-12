package org.team5499.monkeyLib.hardware.ctre

import com.ctre.phoenix.motorcontrol.can.VictorSPX
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.native.NativeUnitModel

typealias LinearMonkeySPX = MonkeySPX<Length>
typealias AngularMonkeySPX = MonkeySPX<Rotation2d>

class MonkeySPX<T : SIUnit<T>>(
    val victorSPX: VictorSPX,
    model: NativeUnitModel<T>
) : CTREMonkeyMotor<T>(victorSPX, model) {

    init {
        victorSPX.configFactoryDefault()
    }

    constructor(id: Int, model: NativeUnitModel<T>): this(VictorSPX(id), model)
}
