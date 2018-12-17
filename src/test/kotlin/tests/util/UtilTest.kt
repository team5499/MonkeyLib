package tests.utils

import frc.team5499.monkeyLib.input.DriveSignal
import frc.team5499.monkeyLib.util.Utils

import frc.team5499.monkeyLib.math.Epsilon

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

public class UtilTest {

    private val kTestWheelCir = 6.0 * Math.PI
    private val kTestTicksPerRotation = 1000

    @Test
    fun test_inches_to_encoder_ticks() {
        val inches = 2.0
        val output = Utils.inchesToEncoderTicks(kTestTicksPerRotation, kTestWheelCir, inches)
        assertTrue(output == (2.0 * kTestTicksPerRotation / kTestWheelCir).toInt())
    }

    @Test
    fun funTestDriveSignal() {
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
