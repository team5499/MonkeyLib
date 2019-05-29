package tests.math

import org.team5499.monkeyLib.math.Epsilon

import org.junit.Test
import org.junit.Assert.assertEquals
import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.degree

@Suppress("LargeClass")
public class GeometryTests {

    companion object {
        private const val testEpsilon = Epsilon.EPSILON
    }

    @Suppress("LongMethod")
    @Test
    fun testVector2() {
        var pos1 = Vector2()
        assertEquals(0.0, pos1.x, testEpsilon)
        assertEquals(0.0, pos1.y, testEpsilon)
        assertEquals(0.0, pos1.magnitude, testEpsilon)

        pos1 = Vector2(3.0, 4.0)
        assertEquals(3.0, pos1.x, testEpsilon)
        assertEquals(4.0, pos1.y, testEpsilon)
        assertEquals(5.0, pos1.magnitude, testEpsilon)

        pos1 = Vector2(3.152, 4.1666)
        var pos2 = -pos1
        assertEquals(-pos2.x, pos1.x, testEpsilon)
        assertEquals(-pos2.y, pos1.y, testEpsilon)
        assertEquals(pos1.magnitude, pos2.magnitude, testEpsilon)

        pos1 = Vector2(2, 0)
        var rot1 = 90.0.degree
        pos2 = pos1.rotateBy(rot1)
        assertEquals(0.0, pos2.x, testEpsilon)
        assertEquals(2.0, pos2.y, testEpsilon)
        assertEquals(pos1.magnitude, pos2.magnitude, testEpsilon)

        pos1 = Vector2(2, 0)
        rot1 = -45.0.degree
        pos2 = pos1.rotateBy(rot1)
        assertEquals(Math.sqrt(2.0), pos2.x, testEpsilon)
        assertEquals(-Math.sqrt(2.0), pos2.y, testEpsilon)

        pos1 = Vector2(2, 0)
        pos2 = Vector2(-2, 1)
        var pos3 = pos1.translateBy(pos2)
        assertEquals(0.0, pos3.x, testEpsilon)
        assertEquals(1.0, pos3.y, testEpsilon)
        assertEquals(1.0, pos3.magnitude, testEpsilon)

        val identity = Vector2()
        pos1 = Vector2(2.16612, -23.55)
        pos2 = pos1.translateBy(-pos1)
        assertEquals(identity.x, pos2.x, testEpsilon)
        assertEquals(identity.y, pos2.y, testEpsilon)
        assertEquals(identity.magnitude, pos2.magnitude, testEpsilon)

        pos1 = Vector2(0, 1)
        pos2 = Vector2(10, -1)
        pos3 = pos1.interpolate(pos2, 0.5)
        assertEquals(5.0, pos3.x, testEpsilon)
        assertEquals(0.0, pos3.y, testEpsilon)

        pos1 = Vector2(0, 1)
        pos2 = Vector2(10, -1)
        pos3 = pos1.interpolate(pos2, 0.75)
        assertEquals(7.5, pos3.x, testEpsilon)
        assertEquals(-0.5, pos3.y, testEpsilon)
    }

    @Suppress("LongMethod")
    @Test
    fun testTwist() {
    }

    @Suppress("LongMethod")
    @Test
    fun testRotation() {
    }

    @Suppress("LongMethod")
    @Test
    fun testPose2d() {
    }
}
