package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain
import org.team5499.monkeyLib.subsystems.drivetrain.CharacterizationData
import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.millisecond
import org.team5499.monkeyLib.util.time.DeltaTime

class QuasistaticCharacterizationAction(
    private val drivetrain: IDrivetrain,
    private val wheelRadius: Length,
    private val effectiveWheelbaseRadius: Length, // meters,
    private val turnInPlace: Boolean = false
) : Action(0.0) {

    private val charData: MutableList<CharacterizationData> = mutableListOf()

    private val dtController = DeltaTime()

    private var startTime = 0.millisecond
    private var commandedVoltage = 0.0

    override fun start() {
        commandedVoltage = 0.0
        startTime = System.currentTimeMillis().millisecond
        dtController.reset()
    }

    override fun update() {

        commandedVoltage = kRampRate * (System.currentTimeMillis().millisecond - startTime).second
        val dt = dtController.updateTime(System.currentTimeMillis().millisecond)
        drivetrain.setPercent(commandedVoltage / 12.0, commandedVoltage / 12.0 * if (turnInPlace) -1.0 else 1.0)

        val avgOutputVoltage = drivetrain.averageOutputVoltage

        val wheelMotion = DifferentialDrive.WheelState(
            drivetrain.leftVelocity.value,
            drivetrain.rightVelocity.value // m / s
        )

        val avgSpeed = if (turnInPlace) {
            (wheelMotion.right - wheelMotion.left) / (2.0 - effectiveWheelbaseRadius.value)
        } else {
            (wheelMotion.right + wheelMotion.left) / 2.0
        }

        charData.add(CharacterizationData(avgOutputVoltage.value, avgSpeed, dt.second))
    }

    override fun next(): Boolean {
        return commandedVoltage > kMaxVoltage
    }

    override fun finish() {
        println("VELOCITY DATA")
        charData.forEach { println(it.toCSV()) }
    }

    companion object {
        private const val kMaxVoltage = 4.0 // V
        private const val kRampRate = 0.15 // V / s
    }
}
