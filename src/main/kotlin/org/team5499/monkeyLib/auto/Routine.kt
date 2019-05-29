package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.Pose2d

import org.team5499.monkeyLib.auto.actions.Action
import org.team5499.monkeyLib.math.geometry.degree

class Routine(name: String, startPose: Pose2d, vararg actions: Action) {

    private val actions: Array<out Action>
    var stepNumber: Int
    val name: String
    val startPose: Pose2d

    init {
        this.stepNumber = 0
        this.name = name
        this.startPose = startPose
        this.actions = actions.copyOf()
    }

    @Suppress("SpreadOperator")
    public constructor(name: String, vararg actions: Action) :
        this(name, Pose2d(Vector2(0, 0), 0.0.degree), *actions)

    public fun getCurrentAction(): Action {
        return actions.get(stepNumber)
    }

    public fun advanceRoutine(): Boolean {
        if (isLastStep()) {
            return false
        }
        stepNumber++
        return true
    }

    public fun reset() {
        this.stepNumber = 0
    }

    public fun isLastStep(): Boolean {
        return (stepNumber >= (actions.size - 1))
    }
}
