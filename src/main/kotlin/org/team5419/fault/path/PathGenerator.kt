package org.team5419.fault.path

import org.team5419.fault.math.splines.QuinticHermiteSpline
import org.team5419.fault.math.splines.SplineGenerator
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.geometry.Pose2dWithCurvature

// length added to end of path to allow easier path following
const val PATH_EXTENSION_LENGTH = 12.0 // inches

class PathGenerator {

    private val mMaxVelocity: () -> Double // i / s
    private val mMaxAcceleration: () -> Double // in / s / s
    private val mStartPathVelocity: () -> Double // i / s
    private val mEndPathVelocity: () -> Double // i / s

    public constructor(
        defaultMaxVelocity: Double,
        defaultMaxAcceleration: Double,
        defaultStartVelocity: Double,
        defaultEndVelocity: Double
    ) {
        mMaxVelocity = { defaultMaxVelocity }
        mMaxAcceleration = { defaultMaxAcceleration }
        mStartPathVelocity = { defaultStartVelocity }
        mEndPathVelocity = { defaultEndVelocity }
    }

    public constructor(
        defaultMaxVelocity: () -> Double,
        defaultMaxAcceleration: () -> Double,
        defaultStartVelocity: () -> Double,
        defaultEndVelocity: () -> Double
    ) {
        mMaxVelocity = { defaultMaxVelocity() }
        mMaxAcceleration = { defaultMaxAcceleration() }
        mStartPathVelocity = { defaultStartVelocity() }
        mEndPathVelocity = { defaultEndVelocity() }
    }

    @Suppress("LongParameterList", "ComplexMethod")
    public fun generatePath(
        reversed: Boolean,
        waypoints: Array<Pose2d>,
        maxVelo: Double,
        maxAccel: Double,
        startVelo: Double,
        endVelo: Double
    ): Path {
        val waypointsMaybeFlipped = waypoints.toMutableList()
        val flip = Pose2d.fromRotation(Rotation2d(-1.0, 0.0, false))
        if (reversed) {
            waypointsMaybeFlipped.clear()
            for (pose in waypoints) {
                waypointsMaybeFlipped.add(pose.transformBy(flip))
            }
        }

        val splines: MutableList<QuinticHermiteSpline> = mutableListOf()
        for (i in 0..waypointsMaybeFlipped.size - 2) {
            splines.add(QuinticHermiteSpline(waypointsMaybeFlipped.get(i), waypointsMaybeFlipped.get(i + 1)))
        }
        QuinticHermiteSpline.optimizeSpline(splines)

        var samples = SplineGenerator.parameterizeSplines(splines)
        if (reversed) {
            val flipped = samples.toMutableList()
            flipped.clear()
            for (i in 0..samples.size - 1) {
                flipped.add(Pose2dWithCurvature(
                    samples.get(i).pose.transformBy(flip),
                    -samples.get(i).curvature,
                    samples.get(i).dCurvature
                ))
            }
            samples = flipped
        }

        // extend last segment by lookahead distance
        val lastNorm = (samples.get(samples.size - 1).translation -
            samples.get(samples.size - 2).translation).normalized
        val newSegment = samples.get(samples.size - 1).translation + (lastNorm * PATH_EXTENSION_LENGTH)
        samples.set(samples.size - 1, Pose2dWithCurvature(
            Pose2d(newSegment, samples.get(samples.size - 1).rotation),
            samples.get(samples.size - 1).curvature,
            samples.get(samples.size - 1).dCurvature)
        )

        val velocities = mutableListOf<Double>()
        for (i in 0..samples.size - 1) {
            velocities.add(Math.min(maxVelo, Math.abs(3.0 / samples.get(i).curvature)))
        }
        velocities.set(velocities.size - 1, endVelo)
        for (i in (samples.size - 2).downTo(0)) {
            val distance = samples.get(i).translation.distanceTo(samples.get(i + 1).translation)
            val value = Math.min(
                velocities.get(i),
                Math.sqrt(Math.pow(velocities.get(i + 1), 2.0) + 2.0 * maxAccel * distance)
            )
            velocities.set(i, value)
        }

        velocities.set(0, startVelo)
        for (i in 0..samples.size - 2) {
            val distance = samples.get(i).translation.distanceTo(samples.get(i + 1).translation)
            val value = Math.sqrt(Math.pow(velocities.get(i), 2.0) + 2.0 * maxAccel * distance)
            if (value < velocities.get(i + 1))
                velocities.set(i + 1, value)
        }

        return Path(samples, velocities, reversed)
    }

    public fun generatePath(reversed: Boolean, waypoints: Array<Pose2d>, maxVelo: Double, maxAccel: Double): Path {
        return generatePath(reversed, waypoints, maxVelo, maxAccel, mStartPathVelocity(), mEndPathVelocity())
    }

    public fun generatePath(reversed: Boolean, waypoints: Array<Pose2d>): Path {
        return generatePath(
            reversed, waypoints,
            mMaxVelocity(),
            mMaxAcceleration(),
            mStartPathVelocity(),
            mEndPathVelocity()
        )
    }

    @Suppress("LongParameterList")
    public fun generateMirroredPath(
        reversed: Boolean,
        waypoints: Array<Pose2d>,
        maxVelo: Double,
        maxAccel: Double,
        startVelocity: Double,
        endVelocity: Double
    ): MirroredPath {
        return MirroredPath(
            generatePath(
                reversed,
                waypoints,
                maxVelo,
                maxAccel,
                startVelocity,
                endVelocity
            )
        )
    }

    public fun generateMirroredPath(
        reversed: Boolean,
        waypoints: Array<Pose2d>,
        maxVelo: Double,
        maxAccel: Double
    ): MirroredPath {
        return MirroredPath(
            generatePath(
                reversed,
                waypoints,
                maxVelo,
                maxAccel
            )
        )
    }

    public fun generateMirroredPath(reversed: Boolean, waypoints: Array<Pose2d>): MirroredPath {
        return MirroredPath(
            generatePath(
                reversed,
                waypoints
            )
        )
    }
}
