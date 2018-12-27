package tests.input

import org.team5499.monkeyLib.input.DriveSignal
import org.team5499.monkeyLib.input.TankDriveHelper
import org.team5499.monkeyLib.input.SpaceDriveHelper
import org.team5499.monkeyLib.input.CheesyDriveHelper

import org.team5499.monkeyLib.math.Epsilon

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class DriveHelperTest {

    @Test
    fun testCheesyDriveHelper() {
        val helper = CheesyDriveHelper()
    }

    @Suppress("LongMethod")
    @Test
    fun testSpaceDriveHelper() {
        val helper = SpaceDriveHelper(0.02, 0.4)
        var output = helper.calculateOutput(0.01, 0.01, false)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)

        output = helper.calculateOutput(1.0, 0.0, false)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, 1.0, Epsilon.EPSILON)

        output = helper.calculateOutput(-1.0, 0.0, false)
        assertEquals(output.left, -1.0, Epsilon.EPSILON)
        assertEquals(output.right, -1.0, Epsilon.EPSILON)

        output = helper.calculateOutput(0.0, 0.5, true)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, -0.5, Epsilon.EPSILON)

        output = helper.calculateOutput(0.0, -0.5, true)
        assertEquals(output.left, -0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)

        output = helper.calculateOutput(0.0, 0.5, false)
        assertEquals(output.left, 0.5 * 0.4, Epsilon.EPSILON)
        assertEquals(output.right, -0.5 * 0.4, Epsilon.EPSILON)

        output = helper.calculateOutput(0.0, -0.5, false)
        assertEquals(output.left, -0.5 * 0.4, Epsilon.EPSILON)
        assertEquals(output.right, 0.5 * 0.4, Epsilon.EPSILON)
    }

    @Suppress("LongMethod")
    @Test
    fun testTankDriveHelper() {
        val helper = TankDriveHelper(0.02, 0.5)
        var output = helper.calculateOutput(0.01, 0.01, false)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)

        output = helper.calculateOutput(-0.01, -0.01, false)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)

        output = helper.calculateOutput(0.5, 0.5, false)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)

        output = helper.calculateOutput(1.0, 1.0, false)
        assertEquals(output.left, 1.0, Epsilon.EPSILON)
        assertEquals(output.right, 1.0, Epsilon.EPSILON)

        output = helper.calculateOutput(1.0, 1.0, true)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)

        output = helper.calculateOutput(0.01, 1.0, true)
        assertEquals(output.left, 0.0, Epsilon.EPSILON)
        assertEquals(output.right, 0.5, Epsilon.EPSILON)

        output = helper.calculateOutput(1.0, 0.01, true)
        assertEquals(output.left, 0.5, Epsilon.EPSILON)
        assertEquals(output.right, 0.0, Epsilon.EPSILON)
    }

    @Test
    fun testDriveSignal() {
        var signal = DriveSignal.NEUTRAL
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
