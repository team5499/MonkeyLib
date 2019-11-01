package org.team5419.fault.hardware.ctre

import com.ctre.phoenix.motorcontrol.IMotorController
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.NeutralMode

import org.team5419.fault.hardware.AbstractBerkeliumMotor
import org.team5419.fault.hardware.BerkeliumMotor
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.native.NativeUnitModel

import kotlin.properties.Delegates

abstract class CTREBerkeliumMotor<T : SIUnit<T>> internal constructor(
    val motorController: IMotorController,
    val model: NativeUnitModel<T>
) : AbstractBerkeliumMotor<T>() {

    private var mLastDemand = Demand(ControlMode.Disabled, 0.0, DemandType.Neutral, 0.0)

    override val encoder = CTREBerkeliumEncoder(motorController, 0, model)

    override val voltageOutput: Double
        get() = motorController.motorOutputVoltage

    override var outputInverted: Boolean by Delegates.observable(false) { _, _, newValue ->
        motorController.inverted = newValue
    }

    override var brakeMode: Boolean by Delegates.observable(false) { _, _, newValue ->
        motorController.setNeutralMode(if (newValue) NeutralMode.Brake else NeutralMode.Coast)
    }

    override var voltageCompSaturation: Double by Delegates.observable(kCompVoltage) { _, _, newValue ->
            motorController.configVoltageCompSaturation(newValue, 0)
            motorController.enableVoltageCompensation(true)
    }

    override var motionProfileCruiseVelocity: Double by Delegates.observable(0.0) { _, _, newValue ->
        motorController.configMotionCruiseVelocity((model.toNativeUnitVelocity(newValue) / 10.0).toInt(), 0)
    }

    override var motionProfileAcceleration: Double by Delegates.observable(0.0) { _, _, newValue ->
        motorController.configMotionAcceleration((model.toNativeUnitAcceleration(newValue) / 10.0).toInt(), 0)
    }

    init {
        motorController.configVoltageCompSaturation(kCompVoltage, 0)
        motorController.enableVoltageCompensation(true)
    }

    override fun setVoltage(voltage: Double, arbitraryFeedForward: Double) = sendDemand(
            Demand(
                    ControlMode.PercentOutput, voltage / kCompVoltage,
                    DemandType.ArbitraryFeedForward, arbitraryFeedForward / kCompVoltage
            )
    )

    override fun setPercent(percent: Double, arbitraryFeedForward: Double) = sendDemand(
            Demand(
                    ControlMode.PercentOutput, percent,
                    DemandType.ArbitraryFeedForward, arbitraryFeedForward / kCompVoltage
            )
    )

    override fun setVelocity(velocity: Double, arbitraryFeedForward: Double) = sendDemand(
            Demand(
                    ControlMode.Velocity, model.toNativeUnitVelocity(velocity) / 10.0,
                    DemandType.ArbitraryFeedForward, arbitraryFeedForward / kCompVoltage
            )
    )

    override fun setPosition(position: Double, arbitraryFeedForward: Double) = sendDemand(
            Demand(
                    if (useMotionProfileForPosition) ControlMode.MotionMagic else ControlMode.Position,
                    model.toNativeUnitPosition(position),
                    DemandType.ArbitraryFeedForward, arbitraryFeedForward / kCompVoltage
            )
    )

    override fun setNeutral() = sendDemand(
            Demand(
                    ControlMode.Disabled,
                    0.0,
                    DemandType.Neutral,
                    0.0
            )
    )

    fun sendDemand(demand: Demand) {
        if (demand != mLastDemand) {
            motorController.set(demand.mode, demand.demand0, demand.demand1Type, demand.demand1)
            mLastDemand = demand
        }
    }

    override fun follow(motor: BerkeliumMotor<*>): Boolean =
            if (motor is CTREBerkeliumMotor<*>) {
                motorController.follow(motor.motorController)
                true
            } else {
                super.follow(motor)
            }

    data class Demand(
        val mode: ControlMode,
        val demand0: Double,
        val demand1Type: DemandType = DemandType.Neutral,
        val demand1: Double = 0.0
    )

    companion object {
        private const val kCompVoltage = 12.0
    }
}
