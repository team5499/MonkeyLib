package tests.math.units

import org.junit.Test
import org.team5419.fault.math.epsilonEquals
import org.team5419.fault.math.units.minute
import org.team5419.fault.math.units.second

class TimeTest {

    @Test
    fun testDivision() {
        val one = 45.minute

        val two = one / 3.0

        assert(two.minute == 15.0)
    }

    @Test
    fun testMultiplication() {
        val one = 15.minute

        val two = one * 3.0

        assert(two.minute epsilonEquals 45.0)
    }

    @Test
    fun testAddition() {
        val one = 2.5.minute
        val two = 360.second

        val three = one + two

        assert(three.minute epsilonEquals 8.5)
    }

    @Test
    fun testSubtraction() {
        val one = 1.minute
        val two = 30.second

        val three = one - two

        assert(three.second epsilonEquals 30.0)
    }
}
