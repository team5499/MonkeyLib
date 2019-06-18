package tests.math

import org.junit.Test
import org.junit.Assert.assertTrue
import org.team5499.monkeyLib.math.epsilonEquals

class EpsilonTest {

    @Test
    fun testEpsilon() {
        var temp = 3.3333333333
        assertTrue(temp epsilonEquals 3.3333333333)
        temp = 1E-10
        assertTrue(temp epsilonEquals 0.0)
    }
}
