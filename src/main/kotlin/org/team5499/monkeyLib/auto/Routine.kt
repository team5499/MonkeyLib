package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.degree
import java.util.concurrent.atomic.AtomicLong

class Routine(
    name: String? = null,
    val startPose: Pose2d = Pose2d(Vector2(0.0, 0.0), 0.degree),
    override val actions: MutableList<Action>
) : SerialAction(actions) {

    constructor(
        name: String? = null,
        startPose: Pose2d = Pose2d(Vector2(0.0, 0.0), 0.degree),
        vararg actions: Action
    ) : this(name, startPose, actions.toMutableList())

    val name = name ?: "Routine ${routineId.incrementAndGet()}"

    companion object {
        private val routineId = AtomicLong()
    }
}

fun <T : ActionGroup> T.toRoutine(name: String? = null, startPose: Pose2d = Pose2d()) = Routine(
        name,
        startPose,
        this.actions.toMutableList()
)
