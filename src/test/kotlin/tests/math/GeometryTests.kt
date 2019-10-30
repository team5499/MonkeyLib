package tests.math

import org.junit.Assert
import org.junit.Test
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Rectangle2d
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.degree
import org.team5419.fault.math.kEpsilon
import org.team5419.fault.math.units.meter

@Suppress("LargeClass")
class GeometryTests {

    @Test
    fun testTransforms() {
        // Position of the static object
        val staticObjectPose = Pose2d(10.meter, 10.meter, 0.degree)

        // Position of the camera on the robot.
        // Camera is on the back of the robot (1 foot behind the center)
        // Camera is facing backward
        val robotToCamera = Pose2d((-1).meter, 0.meter, 180.degree)

        // The camera detects the static object 9 meter in front and 2 meter to the right of it.
        val cameraToStaticObject = Pose2d(9.meter, 2.meter, 0.degree)

        // Transform the static object into the robot's coordinates
        val robotToStaticObject = robotToCamera + cameraToStaticObject

        // Get the global robot pose
        val globalRobotPose = staticObjectPose - robotToStaticObject

        println(
                "X: ${globalRobotPose.translation.x.meter}, Y: ${globalRobotPose.translation.y.meter}, " +
                        "Angle: ${globalRobotPose.rotation.degree}"
        )

        Assert.assertEquals(0.0, globalRobotPose.translation.x, kEpsilon)
        Assert.assertEquals(8.0, globalRobotPose.translation.y, kEpsilon)
        Assert.assertEquals((-180).degree, globalRobotPose.rotation)
    }

    @Test
    fun testRectangleContains() {
        val rectangle = Rectangle2d(Vector2(0.meter, 0.meter), Vector2(10.meter, 10.meter))
        val translation = Vector2(5.meter, 7.meter)
        assert(rectangle.contains(translation))
    }

    @Suppress("LongMethod")
    @Test
    fun testPose() {
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
