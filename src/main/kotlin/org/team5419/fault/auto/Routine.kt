package org.team5419.fault.auto

import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2d

class Routine(name: String, startPose: Pose2d, vararg actions: Action) {

    private val actions: Array<out Action>
    var stepNumber: Int
        get() = field
    val name: String
        get() = field
    val startPose: Pose2d
        get() = field

    init {
        this.stepNumber = 0
        this.name = name
        this.startPose = startPose
        this.actions = actions.copyOf()
    }

    @Suppress("SpreadOperator")
    public constructor(name: String, vararg actions: Action) :
        this(name, Pose2d(Vector2(0, 0), Rotation2d.fromDegrees(0.0)), *actions)

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
