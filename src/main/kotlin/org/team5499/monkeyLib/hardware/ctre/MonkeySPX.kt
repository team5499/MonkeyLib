package org.team5499.monkeyLib.hardware.ctre

import com.ctre.phoenix.motorcontrol.can.VictorSPX
import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.native.NativeUnitModel

class MonkeySPX<T : SIUnit<T>>(
    val victorSPX: VictorSPX,
    model: NativeUnitModel<T>
) : CTREMonkeyMotor<T>(victorSPX, model) {

    constructor(id: Int, model: NativeUnitModel<T>): this(VictorSPX(id), model)
}
