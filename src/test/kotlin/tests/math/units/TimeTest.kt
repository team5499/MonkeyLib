package tests.math.units

import org.junit.Test
import org.team5419.fault.math.epsilonEquals
import org.team5419.fault.math.units.*

    class TimeTest {

        @Test
        fun testDivision() {
            val one = 45.hours

            val two = one / 3

            assert(two.inHours() == 15.0)
        }

        @Test
        fun testAddition() {
            val one = 2.5.minutes
            val two = 360.seconds

            val three = one + two

            assert(three.inMinutes() epsilonEquals 8.5)
        }
    }
