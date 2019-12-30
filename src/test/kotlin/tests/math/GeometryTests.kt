package tests.math

import org.junit.Assert
import org.junit.Test
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Rectangle2d
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.kEpsilon
import org.team5419.fault.math.units.derived.degrees
import org.team5419.fault.math.units.derived.toRotation2d
import org.team5419.fault.math.units.meters

@Suppress("LargeClass")
class GeometryTests {

    @Test
    fun testTransforms() {
        // Position of the static object
        val staticObjectPose = Pose2d(10.meters, 10.meters, 0.degrees)

        // Position of the camera on the robot.
        // Camera is on the back of the robot (1 foot behind the center)
        // Camera is facing backward
        val robotToCamera = Pose2d((-1).meters, 0.meters, 180.degrees)

        // The camera detects the static object 9 meter in front and 2 meter to the right of it.
        val cameraToStaticObject = Pose2d(9.meters, 2.meters, 0.degrees)

        // Transform the static object into the robot's coordinates
        val robotToStaticObject = robotToCamera + cameraToStaticObject

        // Get the global robot pose
        val globalRobotPose = staticObjectPose - robotToStaticObject

        println(
                "X: ${globalRobotPose.translation.x.value}, Y: ${globalRobotPose.translation.y.value}, " +
                        "Angle: ${globalRobotPose.rotation.degree}"
        )

        Assert.assertEquals(0.0, globalRobotPose.translation.x.value, kEpsilon)
        Assert.assertEquals(8.0, globalRobotPose.translation.y.value, kEpsilon)
        println(globalRobotPose.rotation.toString())
        Assert.assertEquals((-180).degrees.toRotation2d(), globalRobotPose.rotation)
    }

    @Test
    fun testRectangleContains() {
        val rectangle = Rectangle2d(Vector2(0.meters, 0.meters), Vector2(10.meters, 10.meters))
        val translation = Vector2(5.meters, 7.meters)
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
