package org.team5499.monkeyLib.trajectory.followers

import org.team5499.monkeyLib.trajectory.TrajectoryIterator
import org.team5499.monkeyLib.trajectory.types.TimedEntry

import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.Rotation2d

import kotlin.math.pow

public class PurePursuitFollower(
    private val kLat: Double,
    private val kLookaheadTime: Double,
    private val kMinLookaheadDistance: Double = 18.0 // inches
) : TrajectoryFollower() {

    override fun calculateState(
        iterator: TrajectoryIterator<Double, TimedEntry<Pose2dWithCurvature>>,
        robotPose: Pose2d
    ): TrajectoryFollower.TrajectoryFollowerVelocityOutput {
        val referencePoint = iterator.currentState

        // Compute the lookahead state.
        val lookaheadState: Pose2d = calculateLookaheadPose2d(iterator, robotPose)

        // Find the appropriate lookahead point.
        val lookaheadTransform = lookaheadState inFrameOfReferenceOf robotPose

        // Calculate latitude error.
        val xError = (referencePoint.state.state.pose inFrameOfReferenceOf robotPose).translation.x

        // Calculate the velocity at the reference point.
        val vd = referencePoint.state.velocity

        // Calculate the distance from the robot to the lookahead.
        val l = lookaheadTransform.translation.magnitude

        // Calculate the curvature of the arc that connects the robot and the lookahead point.
        val curvature = 2 * lookaheadTransform.translation.y / l.pow(2)

        // Adjust the linear velocity to compensate for the robot lagging behind.
        val adjustedLinearVelocity = vd * lookaheadTransform.rotation.cosAngle + kLat * xError

        return TrajectoryFollower.TrajectoryFollowerVelocityOutput(
            linearVelocity = adjustedLinearVelocity,
            // v * curvature = omega
            angularVelocity = adjustedLinearVelocity * curvature
        )
    }

    @Suppress("ReturnCount")
    private fun calculateLookaheadPose2d(
        iterator: TrajectoryIterator<Double, TimedEntry<Pose2dWithCurvature>>,
        robotPose: Pose2d
    ): Pose2d {
        val lookaheadPoseByTime = iterator.preview(kLookaheadTime).state.state.pose

        // The lookahead point is farther from the robot than the minimum lookahead distance.
        // Therefore we can use this point.
        if ((lookaheadPoseByTime inFrameOfReferenceOf robotPose).translation.magnitude >= kMinLookaheadDistance) {
            return lookaheadPoseByTime
        }

        var lookaheadPoseByDistance = iterator.currentState.state.state.pose
        var previewedTime = 0.0

        // Run the loop until a distance that is greater than the minimum lookahead distance is found or until
        // we run out of "trajectory" to search. If this happens, we will simply extend the end of the trajectory.
        while (iterator.progress > previewedTime) {
            previewedTime += 0.02

            lookaheadPoseByDistance = iterator.preview(previewedTime).state.state.pose
            val lookaheadDistance = (lookaheadPoseByDistance inFrameOfReferenceOf robotPose).translation.magnitude

            if (lookaheadDistance > kMinLookaheadDistance) {
                return lookaheadPoseByDistance
            }
        }

        // Extend the trajectory.
        val remaining =
            kMinLookaheadDistance - (lookaheadPoseByDistance inFrameOfReferenceOf robotPose).translation.magnitude

        return lookaheadPoseByDistance.transformBy(
            Pose2d(
                Vector2(remaining * if (iterator.trajectory.reversed) -1 else 1, 0.0),
                Rotation2d()
            )
        )
    }
}
