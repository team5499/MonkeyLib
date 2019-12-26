package tests.math.splines

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.splines.FunctionalQuadraticSpline

import org.team5419.fault.math.splines.QuinticHermiteSpline
import org.team5419.fault.math.units.derived.degrees
import org.team5419.fault.math.units.meters

class QuinticHermiteOptimizerTest {

    @Suppress("LongMethod")
    @Test
    fun testOptimizer() {
        // test porabola thing
        val t = FunctionalQuadraticSpline(Vector2(-1.0.meters, 0.015.meters), Vector2(0.0.meters, 0.015.meters), Vector2(1.0.meters, 0.016.meters)).vertexXCoordinate
        assertEquals(t.value, -0.5, 0.1)

        val a = Pose2d(Vector2(0.meters, 100.meters), 270.degrees)
        val b = Pose2d(Vector2(50.meters, 0.meters), 0.degrees)
        val c = Pose2d(Vector2(100.meters, 100.meters), 90.degrees)

        val d = Pose2d(Vector2(0.meters, 0.meters), 90.degrees)
        val e = Pose2d(Vector2(0.meters, 50.meters), 0.degrees)
        val f = Pose2d(Vector2(100.meters, 0.meters), 90.degrees)
        val g = Pose2d(Vector2(100.meters, 100.meters), 0.degrees)

        val h = Pose2d(Vector2(0.meters, 0.meters), 0.degrees)
        val i = Pose2d(Vector2(50.meters, 0.meters), 0.degrees)
        val j = Pose2d(Vector2(100.meters, 50.meters), 45.degrees)
        val k = Pose2d(Vector2(150.meters, 0.meters), 270.degrees)
        val l = Pose2d(Vector2(150.meters, -50.meters), 270.degrees)

        var startTime = System.currentTimeMillis()

        val s0 = QuinticHermiteSpline(a, b)
        println("Took ${System.currentTimeMillis() - startTime} ms to generate 1 splines")
        val s1 = QuinticHermiteSpline(b, c)

        val s2 = QuinticHermiteSpline(d, e)
        val s3 = QuinticHermiteSpline(e, f)
        val s4 = QuinticHermiteSpline(f, g)

        val s5 = QuinticHermiteSpline(h, i)
        val s6 = QuinticHermiteSpline(i, j)
        val s7 = QuinticHermiteSpline(j, k)
        val s8 = QuinticHermiteSpline(k, l)
        val dt = System.currentTimeMillis() - startTime
        println("Took $dt ms to generate 9 splines")
        println("Average: ${dt / 9.0} ms per spline\n")

        val splines = mutableListOf<QuinticHermiteSpline>()
        splines.add(s0)
        splines.add(s1)
        val splines1 = mutableListOf<QuinticHermiteSpline>()
        splines1.add(s2)
        splines1.add(s3)
        splines1.add(s4)
        val splines2 = mutableListOf<QuinticHermiteSpline>()
        splines2.add(s5)
        splines2.add(s6)
        splines2.add(s7)
        splines2.add(s8)

        // 2 splines
        startTime = System.currentTimeMillis()
        var temp = QuinticHermiteSpline.optimizeSpline(splines)
        println("3 Spline Optimization time (ms): ${System.currentTimeMillis() - startTime}")
        println("Optimized dCurvature by $temp\n")
        assertTrue(temp < 0.014)

        // 3 splines
        startTime = System.currentTimeMillis()
        temp = QuinticHermiteSpline.optimizeSpline(splines1)
        println("4 Spline Optimization time (ms): ${System.currentTimeMillis() - startTime}")
        println("Optimized dCurvature by $temp\n")
        assertTrue(temp < 0.16)

        // 4 splines
        startTime = System.currentTimeMillis()
        temp = QuinticHermiteSpline.optimizeSpline(splines2)
        println("5 Spline Optimization time (ms): ${System.currentTimeMillis() - startTime}")
        println("Optimized dCurvature by $temp\n")
        assertTrue(temp < 0.05)
    }
}
