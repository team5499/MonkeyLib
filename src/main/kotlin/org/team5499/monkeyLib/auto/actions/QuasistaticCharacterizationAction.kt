package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain
import org.team5499.monkeyLib.subsystems.drivetrain.CharacterizationData
import org.team5499.monkeyLib.math.physics.DifferentialDrive

class QuasistaticCharacterizationAction(
    private val drivetrain: IDrivetrain,
    private val wheelRadius: Double, // meters
    private val effectiveWheelbaseRadius: Double, // meters,
    private val turnInPlace: Boolean = false
) : Action(0.0) {

    private val charData: MutableList<CharacterizationData> = mutableListOf()

    private var startTime = 0L
    private var lastTime = 0L
    private var commandedVoltage = 0.0 // V

    override fun start() {
        lastTime = System.currentTimeMillis()
        startTime = System.currentTimeMillis()
        commandedVoltage = 0.0
    }

    override fun update() {
        val currentTime = System.currentTimeMillis()
        commandedVoltage = kRampRate * ((currentTime - startTime) * 1000.0)
        val dt = (currentTime - lastTime) * 1000.0 // seconds
        lastTime = currentTime
        drivetrain.setPercent(commandedVoltage / 12.0, commandedVoltage / 12.0 * if (turnInPlace) -1.0 else 1.0)

        val avgOutputVoltage = drivetrain.averageOutputVoltage

        val wheelMotion = DifferentialDrive.WheelState(
            drivetrain.leftVelocity,
            drivetrain.rightVelocity
        )

        val avgSpeed = if (turnInPlace) {
            (wheelMotion.right - wheelMotion.left) / (2.0 - effectiveWheelbaseRadius)
        } else {
            (wheelMotion.right + wheelMotion.left) / 2.0
        }

        charData.add(CharacterizationData(avgOutputVoltage, avgSpeed, dt))
    }

    override fun next(): Boolean {
        return commandedVoltage > kMaxVoltage
    }

    override fun finish() {
        println("VELOCITY DATA")
        charData.forEach { println(it.toCSV()) }
    }

    companion object {
        private val kMaxVoltage = 4.0 // V
        private val kRampRate = 0.15 // V / s
    }
}
