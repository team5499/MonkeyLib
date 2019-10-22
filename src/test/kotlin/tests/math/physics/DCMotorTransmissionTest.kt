package tests.math.physics

import org.junit.Test
import org.junit.Assert.assertEquals

import org.team5419.fault.math.physics.DCMotorTransmission

class DCMotorTransmissionTest {
    val motor = DCMotorTransmission(1000.0, 0.5, 1.0, 120.0)
    val stallTorque = 5.5 // N•m
    val freeSpeed = 11000.0 // rad/s
    val epsilon = 0.1

    @Test
    fun stallTorqueTest() {
        val calculatedStallTorque = motor.getTorqueForVoltage(0.0, 12.0)
        println("Expected $stallTorque, but got $calculatedStallTorque.")
        assertEquals(stallTorque, calculatedStallTorque, epsilon)
    }

    @Test
    fun freeSpeedTest() {
        val calculatedFreeSpeed = motor.freeSpeedAtVoltage(12.0)
        println("Expected $freeSpeed, but got $calculatedFreeSpeed.")
        assertEquals(freeSpeed, calculatedFreeSpeed, epsilon)
    }

    @Test
    fun constantsTest() {
        assertEquals(1000.0, motor.speedPerVolt, 0.0)
        assertEquals(0.5, motor.torquePerVolt, 0.0)
        assertEquals(1.0, motor.frictionVoltage, 0.0)
    }

    @Test
    fun speedForVoltageAndAmperageTest() {
        val predictedSpeed = motor.getSpeedForVoltageAndAmperage(6.0, 24.0)
        println(predictedSpeed)
        assertEquals(2600.0, predictedSpeed, epsilon)
    }
}
