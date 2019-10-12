@file:Suppress("ConstructorParameterNaming")
package org.team5419.fault.trajectory.types

import org.team5419.fault.math.epsilonEquals
import org.team5419.fault.trajectory.TrajectoryIterator
import org.team5419.fault.math.geometry.State
import org.team5419.fault.math.lerp
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.units.Time
import org.team5419.fault.math.units.derived.LinearAcceleration
import org.team5419.fault.math.units.derived.LinearVelocity
import org.team5419.fault.math.units.derived.acceleration
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.units.meter
import org.team5419.fault.math.units.second

class TimedTrajectory<S : State<S>>(
    override val points: List<TimedEntry<S>>,
    override val reversed: Boolean
) : Trajectory<Time, TimedEntry<S>> {

    override fun sample(interpolant: Time) = sample(interpolant.value)

    fun sample(interpolant: Double) = when {
        interpolant >= lastInterpolant.value -> TrajectorySamplePoint(getPoint(points.size - 1))
        interpolant <= firstInterpolant.value -> TrajectorySamplePoint(getPoint(0))
        else -> {
            val (index, entry) = points.asSequence()
                .withIndex()
                .first { (index, entry) -> index != 0 && entry.t.value >= interpolant }
            val prevEntry = points[ index - 1 ]
            if (entry.t epsilonEquals prevEntry.t) {
                TrajectorySamplePoint(entry, index, index)
            } else {
                TrajectorySamplePoint(
                    prevEntry.interpolate(
                        entry,
                        (interpolant - prevEntry.t.value) / (entry.t.value - prevEntry.t.value)
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
    internal val _t: Double = 0.0,
    internal val _velocity: Double = 0.0,
    internal val _acceleration: Double = 0.0
) : State<TimedEntry<S>> {

    val t get() = _t.second
    val velocity get() = _velocity.meter.velocity
    val acceleration get() = _acceleration.meter.acceleration

    constructor(
        state: S,
        t: Time,
        velocity: LinearVelocity,
        acceleration: LinearAcceleration
    ) : this(state, t.value, velocity.value, acceleration.value)

    override fun interpolate(other: TimedEntry<S>, x: Double): TimedEntry<S> {
        val newT = _t.lerp(other._t, x)
        val dt = newT - this.t.value
        if (dt < 0.0) return other.interpolate(this, 1.0 - x)

        val reversing = _velocity < 0.0 || _velocity epsilonEquals 0.0 && _acceleration < 0.0

        val newV = _velocity + _acceleration * dt
        val newS = (if (reversing) -1.0 else 1.0) * (_velocity * dt + 0.5 * _acceleration * dt * dt)

        return TimedEntry(
            state.interpolate(other.state, newS / state.distance(other.state)),
            newT,
            newV,
            _acceleration
        )
    }

    override fun distance(other: TimedEntry<S>) = state.distance(other.state)

    override fun toCSV() = "$t, $state, $velocity, $acceleration"
}

class TimedIterator<S : State<S>>(
    trajectory: TimedTrajectory<S>
) : TrajectoryIterator<Time, TimedEntry<S>>(trajectory) {
    override fun addition(a: Time, b: Time) = a + b
}

fun Trajectory<Time, TimedEntry<Pose2dWithCurvature>>.mirror() =
    TimedTrajectory(points.map { TimedEntry(it.state.mirror(), it._t, it._velocity, it._acceleration) }, this.reversed)

fun Trajectory<Time, TimedEntry<Pose2dWithCurvature>>.transform(transform: Pose2d) =
    TimedTrajectory(
        points.map { TimedEntry(it.state + transform, it._t, it._velocity, it._acceleration) },
        this.reversed
    )

val Trajectory<Time, TimedEntry<Pose2dWithCurvature>>.duration get() = this.lastState.t
