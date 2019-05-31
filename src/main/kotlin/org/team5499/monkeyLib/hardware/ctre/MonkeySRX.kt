package org.team5499.monkeyLib.hardware.ctre

import com.ctre.phoenix.motorcontrol.can.TalonSRX
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.units.ElectricCurrent
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.math.units.native.NativeUnitModel

typealias LinearMonkeySRX = MonkeySRX<Length>
typealias AngularMonkeySRX = MonkeySRX<Rotation2d>

class MonkeySRX<T : SIUnit<T>>(
    val talonSRX: TalonSRX,
    model: NativeUnitModel<T>
) : CTREMonkeyMotor<T>(talonSRX, model) {

    constructor(id: Int, model: NativeUnitModel<T>): this(TalonSRX(id), model)

    init {
        talonSRX.configFactoryDefault(0)
    }

    fun configCurrentLimit(enabled: Boolean, config: CurrentLimitConfig? = null) {
        talonSRX.enableCurrentLimit(enabled)
        if (enabled && config != null) {
            talonSRX.configContinuousCurrentLimit(config.continuousCurrentLimit.amp.toInt())
            talonSRX.configPeakCurrentLimit(config.peakCurrentLimit.amp.toInt())
            talonSRX.configPeakCurrentDuration(config.peakCurrentLimitDuration.millisecond.toInt())
        }
    }

    data class CurrentLimitConfig(
        val peakCurrentLimit: ElectricCurrent,
        val peakCurrentLimitDuration: Time,
        val continuousCurrentLimit: ElectricCurrent
    )
}
