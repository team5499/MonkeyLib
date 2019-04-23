package org.team5499.monkeyLib.trajectory

import org.team5499.monkeyLib.math.geometry.State
import org.team5499.monkeyLib.trajectory.types.Trajectory
import org.team5499.monkeyLib.trajectory.types.TrajectorySamplePoint

abstract class TrajectoryIterator<U : Comparable<U>, S : State<S>>(
    val trajectory: Trajectory<U, S>
) {

    protected abstract fun addition(a: U, b: U): U

    var progress = trajectory.firstInterpolant
    private var sample = trajectory.sample(progress)

    val isDone: Boolean
        get() = progress >= trajectory.lastInterpolant
    val currentState get() = sample

    fun advance(amount: U): TrajectorySamplePoint<S> {
        progress = addition(progress, amount).coerceIn(trajectory.firstInterpolant, trajectory.lastInterpolant)
        sample = trajectory.sample(progress)
        return sample
    }

    fun preview(amount: U): TrajectorySamplePoint<S> {
        progress = addition(progress, amount).coerceIn(trajectory.firstInterpolant, trajectory.lastInterpolant)
        return trajectory.sample(progress)
    }
}
