package org.team5419.fault.hardware.ctre

import com.ctre.phoenix.motorcontrol.can.TalonSRX
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.Radian
import org.team5419.fault.math.units.native.NativeUnitModel

typealias LinearMonkeySRX = BerkeliumSRX<Meter>
typealias AngularMonkeySRX = BerkeliumSRX<Radian>

class BerkeliumSRX<T : SIKey>(
    val talonSRX: TalonSRX,
    model: NativeUnitModel<T>
) : CTREBerkeliumMotor<T>(talonSRX, model) {

    constructor(id: Int, model: NativeUnitModel<T>): this(TalonSRX(id), model)

    init {
        talonSRX.configFactoryDefault(0)
    }

    fun configCurrentLimit(enabled: Boolean, config: CurrentLimitConfig? = null) {
        talonSRX.enableCurrentLimit(enabled)
        if (enabled && config != null) {
            talonSRX.configContinuousCurrentLimit(config.continuousCurrentLimit.inAmps().toInt())
            talonSRX.configPeakCurrentLimit(config.peakCurrentLimit.inAmps().toInt())
            talonSRX.configPeakCurrentDuration(config.peakCurrentLimitDuration.inMilliseconds().toInt())
        }
    }

    data class CurrentLimitConfig(
        val peakCurrentLimit: SIUnit<Ampere>,
        val peakCurrentLimitDuration: SIUnit<Second>,
        val continuousCurrentLimit: SIUnit<Ampere>
    )
}
