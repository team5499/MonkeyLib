package tests.input

import org.team5419.fault.input.DriveSignal
import org.team5419.fault.input.TankDriveHelper
import org.team5419.fault.input.SpaceDriveHelper
import org.team5419.fault.input.CheesyDriveHelper

import org.team5419.fault.math.Epsilon

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse

class DriveHelperTest {

    @Suppress("LongMethod")
    @Test
    fun testCheesyDriveHelper() {
        val config = CheesyDriveHelper.CheesyDriveConfig()
        config.deadband = 0.02
        config.quickstopDeadband = 1.0
        config.quickstopWeight = 1.0
        config.quickstopScalar = 1.0
        config.highWheelNonlinearity = 1.0
        config.lowWheelNonlinearity = 1.0
        config.highNeginertiaScalar = 1.0
        config.highSensitivity = 1.0
        config.lowNeginertiaTurnScalar = 1.0
        config.lowNeginertiaThreshold = 1.0
        config.lowNeginertiaFarScalar = 1.0
        config.lowNeginertiaCloseScalar = 1.0
        config.lowSensitivity = 1.0

        // need to reset helper every time because it contains references to past calls
        var helper = CheesyDriveHelper(config)
        var output = helper.calculateOutput(0.5, 0.0, false, false)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(-0.5, 0.0, false, false)
        assertEquals(output.left, -0.5, Epsilon.EPSILON)
        assertEquals(output.right, -0.5, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(0.01, 0.01, false, false)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(1.0, 0.5, false, false)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, -0.486690, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(1.0, -0.5, false, false)
        assertEquals(output.left, -0.486690, Epsilon.EPSILON)
        assertEquals(output.right, 1.0, Epsilon.EPSILON)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(0.0, 1.0, true, false)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, -3.0, Epsilon.EPSILON)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(1.0, 0.0, true, false)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, 1.0, Epsilon.EPSILON)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(1.0, 0.0, false, true)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, 1.0, Epsilon.EPSILON)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(-1.0, 0.0, false, true)
        assertEquals(output.left, -1.0, Epsilon.EPSILON)
        assertEquals(output.right, -1.0, Epsilon.EPSILON)

        helper = CheesyDriveHelper(config)
        output = helper.calculateOutput(1.0, 0.5, true, true)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, -1.792037871853613, Epsilon.EPSILON)

        println(output.toString())
    }

    @Suppress("LongMethod")
    @Test
    fun testSpaceDriveHelper() {
        val helper = SpaceDriveHelper(0.02, 0.4, 0.5)
        var output = helper.calculateOutput(0.01, 0.01, false)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(1.0, 0.0, false)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, 1.0, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(-1.0, 0.0, false)
        assertEquals(output.left, -1.0, Epsilon.EPSILON)
        assertEquals(output.right, -1.0, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(0.0, 0.5, true)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, -0.5, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(0.0, -0.5, true)
        assertEquals(output.left, -0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(0.0, 0.5, false)
        assertEquals(output.left, 0.5 * 0.4, Epsilon.EPSILON)
        assertEquals(output.right, -0.5 * 0.4, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(0.0, -0.5, false)
        assertEquals(output.left, -0.5 * 0.4, Epsilon.EPSILON)
        assertEquals(output.right, 0.5 * 0.4, Epsilon.EPSILON)
        assertFalse(output.brakeMode)
    }

    @Suppress("LongMethod")
    @Test
    fun testTankDriveHelper() {
        val helper = TankDriveHelper(0.02, 0.5)
        var output = helper.calculateOutput(0.01, 0.01, false)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(-0.01, -0.01, false)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(0.5, 0.5, false)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(1.0, 1.0, false)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, 1.0, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(1.0, 1.0, true)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(0.01, 1.0, true)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)
        assertFalse(output.brakeMode)

        output = helper.calculateOutput(1.0, 0.01, true)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)
        assertFalse(output.brakeMode)
    }

    @Test
    fun testDriveSignal() {
        var signal = DriveSignal()
        assertEquals(signal.left, 0.0, Epsilon.EPSILON)
        assertEquals(signal.right, 0.0, Epsilon.EPSILON)
        assert(signal.brakeMode == false)
        println("toString: " + signal.toString())
        println("toCSV: " + signal.toCSV())

        signal = DriveSignal.NEUTRAL
        assertEquals(signal.left, 0.0, Epsilon.EPSILON)
        assertEquals(signal.right, 0.0, Epsilon.EPSILON)
        assert(signal.brakeMode == false)

        signal = DriveSignal.BRAKE
        assertEquals(signal.left, 0.0, Epsilon.EPSILON)
        assertEquals(signal.right, 0.0, Epsilon.EPSILON)
        assert(signal.brakeMode == true)

        signal = DriveSignal(0.973, -0.254)
        assertEquals(signal.left, 0.973, Epsilon.EPSILON)
        assertEquals(signal.right, -0.254, Epsilon.EPSILON)
        assert(signal.brakeMode == false)
    }
}
