package tests.math.units

import org.junit.Test
import org.team5419.fault.math.epsilonEquals
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.radians
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.operations.times
import org.team5419.fault.math.units.operations.div

class UnitTest {

    private val settings = NativeUnitLengthModel(
            1440.nativeUnits,
            3.0.inches
    )

    @Test
    fun testNativeUnits() {
        val nativeUnits = 360.nativeUnits.fromNativeUnitPosition(settings)

        assert(nativeUnits.inInches() epsilonEquals 4.71238898038469)
    }

    @Test
    fun testVelocitySTU() {
        val one = 1.meters / 1.seconds

        val two = one.toNativeUnitVelocity(settings)

        val three = two.nativeUnitsPer100ms

        assert(three.toInt() == 300)
    }

    @Test
    fun testAccelerationSTU() {
        val one = 1.meters / 1.seconds / 1.seconds

        val two = one.toNativeUnitAcceleration(settings)

        assert(two.nativeUnitsPer100msPerSecond.toInt() == 300)
    }

    @Test
    fun testFeetToMeter() {
        val one = 1.feet

        assert(one.inMeters() epsilonEquals 0.3048)
    }

    @Test
    fun testKgToPound() {
        val kg = SIUnit<Kilogram>(2.0)
        assert(kg.inLbs() epsilonEquals 4.409248840367555)
    }

    @Test
    fun testUnboundedRotationUnits() {
        val speed = 250.radians.velocity
        assert(speed.value epsilonEquals 250.0)
    }
}
