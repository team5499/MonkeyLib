package org.team5419.fault.hardware.ctre

import com.ctre.phoenix.motorcontrol.can.TalonSRX
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.units.ElectricCurrent
import org.team5419.fault.math.units.Length
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.Time
import org.team5419.fault.math.units.native.NativeUnitModel

typealias LinearMonkeySRX = BerkeliumSRX<Length>
typealias AngularMonkeySRX = BerkeliumSRX<Rotation2d>

class BerkeliumSRX<T : SIUnit<T>>(
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
