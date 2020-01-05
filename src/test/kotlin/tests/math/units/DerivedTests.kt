package tests.math.units

import org.junit.Test
import org.team5419.fault.math.epsilonEquals
import org.team5419.fault.math.units.meters
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.units.unitlessValue
import org.team5419.fault.math.units.inSeconds
import org.team5419.fault.math.units.inMeters
import org.team5419.fault.math.units.amps
import org.team5419.fault.math.units.derived.inFeetPerMinute
import org.team5419.fault.math.units.derived.inFeetPerSecond
import org.team5419.fault.math.units.derived.volts
import org.team5419.fault.math.units.operations.div
import org.team5419.fault.math.units.operations.times

class DerivedTests {

    @Test
    fun testVelocity() {
        val one = 5.meters
        val two = 2.seconds

        val three = one / two

        assert(three.value == 2.5)
    }

    @Test
    fun testVelocityAdjust() {
        val one = 5.meters
        val two = 2.seconds

        val three = one / two

        val four = three.inFeetPerMinute()

        assert(four epsilonEquals 492.12598425196853)
    }

    @Test
    fun testAcceleration() {
        val one = 10.meters / 2.seconds / 4.seconds

        assert(one.value == 1.25)
    }

    @Test
    fun testAccelerationToVelocity() {
        val one = 10.meters / 1.6.seconds / 2.seconds
        val two = 5.seconds

        val three = one * two

        val four = three.inFeetPerSecond()

        assert(four epsilonEquals 51.26312335958006)
    }

    @Test
    fun testVelocityToLength() {
        val one = 5.meters / 2.seconds
        val two = 6.seconds

        val three = one * two
        val four = three.inMeters()

        assert(four == 15.0)
    }

    @Test
    fun testVelocityAndAccelerationToTime() {
        val one = 22.meters / 2.seconds
        val two = 18.meters / 0.5.seconds / 4.seconds

        val three = one / two

        assert(three.inSeconds() epsilonEquals 1.2222222222222223)
    }

    @Test
    fun testAccelerationDividedByAcceleration() {
        val one = 33.meters / 1.seconds / 1.seconds
        val two = 22.meters / 2.seconds / 1.seconds

        val three = (one / two).unitlessValue

        assert(three == 3.0)
    }

    @Test
    fun testVelocityDividedByVelocity() {
        val one = 33.meters / 1.seconds
        val two = 22.meters / 2.seconds

        val three = (one / two).unitlessValue

        assert(three epsilonEquals 3.0)
    }

    @Test
    fun testVoltage() {
        val one = 1.volts
        val two = 5.amps

        val three = one * two

        assert(three.value epsilonEquals 5.0)
    }

    @Test
    fun testOhm() {
        val one = 2.volts
        val two = 5.amps

        val three = one / two

        assert(three.value epsilonEquals 0.4)
    }
}
