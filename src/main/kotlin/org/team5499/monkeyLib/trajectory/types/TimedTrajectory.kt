package org.team5499.monkeyLib.trajectory.types

import org.team5499.monkeyLib.trajectory.TrajectoryIterator
import org.team5499.monkeyLib.math.geometry.State
import org.team5499.monkeyLib.math.lerp
import org.team5499.monkeyLib.math.Epsilon
import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature
import org.team5499.monkeyLib.math.geometry.Pose2d

class TimedTrajectory<S : State<S>>(
    override val points: List<TimedEntry<S>>,
    override val reversed: Boolean
) : Trajectory<Double, TimedEntry<S>> {

    override fun sample(interpolant: Double) = when {
        interpolant >= lastInterpolant -> TrajectorySamplePoint(getPoint(points.size - 1))
        interpolant <= firstInterpolant -> TrajectorySamplePoint(getPoint(0))
        else -> {
            val (index, entry) = points.asSequence()
                .withIndex()
                .first { (index, entry) -> index != 0 && entry.t >= interpolant }
            val prevEntry = points[ index - 1 ]
            if (Epsilon.epsilonEquals(entry.t, prevEntry.t)) {
                TrajectorySamplePoint(entry, index, index)
            } else {
                TrajectorySamplePoint(
                    prevEntry.interpolate(
                        entry,
                        (interpolant - prevEntry.t) / (entry.t - prevEntry.t)
                    ),
                    index - 1,
                    index
                )
            }
        }
    }

    override val firstState get() = points.first()
    override val lastState get() = points.last()

    override val firstInterpolant get() = firstState.t
    override val lastInterpolant get() = lastState.t

    override fun iterator() = TimedIterator(this)
}

data class TimedEntry<S : State<S>> internal constructor(
    val state: S,
    internal val t: Double = 0.0,
    internal val velocity: Double = 0.0,
    internal val acceleration: Double = 0.0
) : State<TimedEntry<S>> {

    override fun interpolate(endValue: TimedEntry<S>, t: Double): TimedEntry<S> {
        val newT = t.lerp(endValue.t, t)
        val dt = newT - this.t
        if (dt < 0.0) return endValue.interpolate(this, 1.0 - t)

        val reversing = velocity < 0.0 || Epsilon.epsilonEquals(velocity) && acceleration < 0.0

        val newV = velocity + acceleration * dt
        val newS = (if (reversing) -1.0 else 1.0) * (velocity * dt + 0.5 * acceleration * dt * dt)

        return TimedEntry(
            state.interpolate(endValue.state, newS / state.distance(endValue.state)),
            newT,
            newV,
            acceleration
        )
    }

    override fun distance(other: TimedEntry<S>) = state.distance(other.state)

    override fun toCSV() = "$t, $state, $velocity, $acceleration"
}

class TimedIterator<S : State<S>>(
    trajectory: TimedTrajectory<S>
) : TrajectoryIterator<Double, TimedEntry<S>>(trajectory) {
    override fun addition(a: Double, b: Double) = a + b
}

fun Trajectory<Double, TimedEntry<Pose2dWithCurvature>>.mirror() =
    TimedTrajectory(points.map { TimedEntry(it.state.mirror(), it.t, it.velocity, it.acceleration) }, this.reversed)

fun Trajectory<Double, TimedEntry<Pose2dWithCurvature>>.transform(transform: Pose2d) =
    TimedTrajectory(
        points.map { TimedEntry(it.state + transform, it.t, it.velocity, it.acceleration) },
        this.reversed
    )

val Trajectory<Double, TimedEntry<Pose2dWithCurvature>>.duration get() = this.lastState.t
