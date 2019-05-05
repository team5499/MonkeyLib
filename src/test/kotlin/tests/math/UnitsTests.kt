package tests.math

import org.junit.Test
import org.junit.Assert.assertEquals

import org.team5419.fault.math.Units

public class UnitConversionTests {
    @Test
    fun testInchToInch() {
        assertEquals(1.0.from(Units.INCHS).to(Units.INCHS) , 1.0)
    }
}