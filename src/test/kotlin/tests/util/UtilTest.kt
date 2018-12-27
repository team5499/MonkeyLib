package tests.utils

import org.team5499.monkeyLib.util.Utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

public class UtilTest {

    private val kTestWheelCir = 6.0 * Math.PI
    private val kTestTicksPerRotation = 1000

    @Test
    fun testInchesToEncoderTicks() {
        val inches = 2.0
        val output = Utils.inchesToEncoderTicks(kTestTicksPerRotation, kTestWheelCir, inches)
        assertTrue(output == (2.0 * kTestTicksPerRotation / kTestWheelCir).toInt())
    }
}
