package tests.math

import org.junit.Test
import org.junit.Assert.assertTrue

import org.team5419.fault.math.units.from
import org.team5419.fault.math.units.INCHS
import org.team5419.fault.math.units.CENTIMETERS
import org.team5419.fault.math.units.MILES
import org.team5419.fault.math.units.HOURS
import org.team5419.fault.math.units.FEET
import org.team5419.fault.math.units.SECONDS

public class UnitConverterTests {
    @Test
    fun testInchToInch() {
        assertTrue(1.0 from INCHS to INCHS == 1.0)
    }

    @Test
    fun testCentimeterToInch() {
        assertTrue(254.0 from CENTIMETERS to INCHS == 100.0)
    }

    @Test
    fun testMilesPerHouresToFeetPerSeconds() {
        assertTrue(15.0 from MILES / HOURS to FEET / SECONDS == 22.0)
    }
}
