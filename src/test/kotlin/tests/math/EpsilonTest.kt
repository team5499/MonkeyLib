package tests.math

import frc.team5499.monkeyLib.math.Epsilon

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

class EpsilonTest {

    @Test
    public fun testEpsilon() {
        var temp = 3.3333333
        assertTrue(Epsilon.epsilonEquals(temp, 3.333333334))
        temp = 1E-10
        assertTrue(Epsilon.epsilonEquals(temp))
    }
}
