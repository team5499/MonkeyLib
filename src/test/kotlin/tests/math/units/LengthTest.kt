package tests.math.units

import org.junit.Test
import org.team5419.fault.math.epsilonEquals
import org.team5419.fault.math.units.inches
import org.team5419.fault.math.units.inInches
import org.team5419.fault.math.units.meters
import org.team5419.fault.math.units.milli
import org.team5419.fault.math.units.centi
import org.team5419.fault.math.units.inMillimeters
import org.team5419.fault.math.units.inMeters

class LengthTest {

    @Test
    fun testLength() {
        val one = 1.0.meters
        val two = 12.0.inches

        val three = one + two

        assert(three.inMeters() epsilonEquals 1.3048)
    }

    @Test
    fun testPrefix() {
        val one = 1.0.meters
        val two = 100.centi.meters

        val three = one + two

        assert(three.inMillimeters() epsilonEquals 2000.0)
    }

    @Test
    fun testScalar() {
        val one = 12.meters

        val two = one / 3.0
        val three = two * 3.0

        assert(two.inMeters() epsilonEquals 4.0)
        assert(three.inMeters() epsilonEquals 12.0)
    }

    @Test
    fun testToMetric() {
        val one = 40.inches

        val two = one.inMillimeters()

        assert(two.toInt() == 1016)
    }

    @Test
    fun testFromMetric() {
        val one = 1016.milli.meters

        val two = one.inInches()

        assert(two.toInt() == 40)
    }
}
