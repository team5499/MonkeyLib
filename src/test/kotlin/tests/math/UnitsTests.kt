package tests.math

import org.junit.Test
import org.junit.Assert.assertTrue

import org.team5419.fault.math.units.*

public class UnitConversionTests {
    @Test
    fun testInchToInch() {
        assertTrue(1.0 from INCHS to INCHS == 1.0)
    }

    @Test
    fun testInchToCentimeter() {
        assertTrue(124.0 from INCHS to CENTIMETERS == 314.96)
    }

    @Test
    fun testMilesPerHouresToFeetPerSeconds() {
        assertTrue(15.0 from MILES / HOURS to FEET / SECONDS == 22.0)
    }
}