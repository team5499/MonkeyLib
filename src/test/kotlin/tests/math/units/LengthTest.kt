package tests.math.units

import org.junit.Test
import org.team5499.monkeyLib.math.epsilonEquals
import org.team5499.monkeyLib.math.units.centimeter
import org.team5499.monkeyLib.math.units.inch
import org.team5499.monkeyLib.math.units.meter
import org.team5499.monkeyLib.math.units.millimeter

class LengthTest {

    @Test
    fun testLength() {
        val one = 1.meter
        val two = 12.inch

        val three = one + two

        assert(three.meter epsilonEquals 1.3048)
    }

    @Test
    fun testPrefix() {
        val one = 1.meter
        val two = 100.centimeter

        val three = one + two
        assert(three.millimeter epsilonEquals 2000.0)
    }

    @Test
    fun testScalar() {
        val one = 12.meter
        val two = one / 3.0
        val three = two * 3.0

        assert(two.meter epsilonEquals 4.0)
        assert(three.meter epsilonEquals 12.0)
    }

    @Test
    fun testToMetric() {
        val one = 40.inch
        val two = one.millimeter

        assert(two.toInt() == 1016)
    }

    @Test
    fun testFromMetric() {
        val one = 1016.millimeter
        val two = one.inch

        assert(two.toInt() == 40)
    }
}
