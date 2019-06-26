package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.millisecond
import org.team5499.monkeyLib.math.units.second
import org.team5499.monkeyLib.subsystems.drivetrain.AbstractTankDrive
import org.team5499.monkeyLib.subsystems.drivetrain.CharacterizationData
import org.team5499.monkeyLib.util.time.DeltaTime

class StepVoltageCharacterizationAction(
    private val driveSubsystem: AbstractTankDrive,
    private val wheelRadius: Length,
    private val effectiveWheelBaseRadius: Length,
    private val turnInPlace: Boolean

) : Action() {

    private val data = ArrayList<CharacterizationData>()
    private val dtController = DeltaTime()

    init {
        withTimeout(kTimeout)
    }

    override fun start() {
        super.start()
        dtController.reset()
    }

    override fun update() {
        val dt = dtController.updateTime(System.currentTimeMillis().millisecond)

        driveSubsystem.setPercent(kStepVoltage / 12.0, kStepVoltage / 12.0 * if (turnInPlace) -1 else 1)

        val avgCompensatedVoltage =
                (driveSubsystem.leftMasterMotor.voltageOutput + driveSubsystem.rightMasterMotor.voltageOutput) / 2.0

        val wheelMotion = DifferentialDrive.WheelState(
                driveSubsystem.leftMasterMotor.encoder.velocity,
                driveSubsystem.rightMasterMotor.encoder.velocity
        )

        // Return robot speed in meters per second if linear, radians per second if angular
        val avgSpd: Double = if (turnInPlace) {
            (wheelMotion.right - wheelMotion.left) / (2.0 * effectiveWheelBaseRadius.value)
        } else {
            (wheelMotion.right + wheelMotion.left) / 2.0
        }

        data.add(CharacterizationData(avgCompensatedVoltage, avgSpd, dt.second))
    }

    override fun finish() {
        println("ACCELERATION DATA")
        data.forEach { System.out.println(it.toCSV()) }
    }

    companion object {
        private val kTimeout = 2.second
        private const val kStepVoltage = 6.0
    }
}