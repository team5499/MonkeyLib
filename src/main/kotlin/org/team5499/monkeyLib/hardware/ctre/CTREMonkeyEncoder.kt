package org.team5499.monkeyLib.hardware.ctre

import com.ctre.phoenix.motorcontrol.IMotorController
import org.team5499.monkeyLib.hardware.AbstractMonkeyEncoder
import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.native.NativeUnitModel
import kotlin.properties.Delegates

class CTREMonkeyEncoder<T : SIUnit<T>>(
    val motorController: IMotorController,
    val pidIdx: Int = 0,
    model: NativeUnitModel<T>
) : AbstractMonkeyEncoder<T>(model) {
    override val rawPosition get() = motorController.getSelectedSensorPosition(pidIdx).toDouble()
    override val rawVelocity get() = motorController.getSelectedSensorVelocity(pidIdx).toDouble() * 10.0

    var encoderPhase by Delegates.observable(false) {
        _, _, newValue -> motorController.setSensorPhase(newValue)
    }

    /**
     * sets position of encoder [newPosition] where [newPosition] is defined by the units of the[model]
     */
    override fun resetPosition(newPosition: Double) {
        // TODO(make sure this works)
        motorController.setSelectedSensorPosition(model.toNativeUnitPosition(newPosition).toInt(), pidIdx, 0)
    }
}
