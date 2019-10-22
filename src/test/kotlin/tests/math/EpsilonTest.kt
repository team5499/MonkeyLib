package tests.math

import org.team5419.fault.math.Epsilon

import org.junit.Test
import org.junit.Assert.assertTrue

class EpsilonTest {

    @Test
    public fun testEpsilon() {
        var temp = 3.3333333
        assertTrue(Epsilon.epsilonEquals(temp, 3.333333334))
        temp = 1E-10
        assertTrue(Epsilon.epsilonEquals(temp))
    }
}
