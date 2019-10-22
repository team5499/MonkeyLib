package tests.math.splines

import org.junit.Test
import org.junit.Assert.assertEquals

import org.team5419.fault.math.Epsilon

import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Pose2dWithCurvature

import org.team5419.fault.math.splines.SplineGenerator
import org.team5419.fault.math.splines.QuinticHermiteSpline

class SplineGeneratorTest {

    @Test
    fun testGenerator() {
        val p1 = Pose2d(Vector2(0, 0), Rotation2d())
        val p2 = Pose2d(Vector2(15, 10), Rotation2d(1, -5, true))
        var s = QuinticHermiteSpline(p1, p2)

        var samples: MutableList<Pose2dWithCurvature> = SplineGenerator.parameterizeSpline(s)
        var arclength = 0.0
        var curPose = samples.get(0)
        for (pose in samples) {
            val t = Pose2d.log(curPose.pose.inverse().transformBy(pose.pose))
            arclength += t.dx
            curPose = pose
        }
        assertEquals(curPose.pose.translation.x, 15.0, Epsilon.EPSILON)
        assertEquals(curPose.pose.translation.y, 10.0, Epsilon.EPSILON)
        assertEquals(curPose.pose.rotation.degrees, -78.69006752597981, Epsilon.EPSILON)
        assertEquals(arclength, 23.17291953186379, Epsilon.EPSILON)

        // for (p in samples) {
        //     println(p.toString())
        // }
    }
}
