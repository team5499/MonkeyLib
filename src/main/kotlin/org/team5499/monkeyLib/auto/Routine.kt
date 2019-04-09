package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.Pose2d

class Routine(
    name: String,
    startPose: Pose2d,
    redStartAdjustment: Vector2 = Vector2(0, 0),
    blueStartAdjustment: Vector2 = Vector2(0, 0),
    vararg actions: Action
) {

    private val actions: Array<out Action>
    var stepNumber: Int
        get() = field
    val name: String
        get() = field
    val startPose: Pose2d
        get() = field
    val redStartPose: Pose2d
    val blueStartPose: Pose2d

    init {
        this.stepNumber = 0
        this.name = name
        this.startPose = startPose
        this.redStartPose = startPose.transformBy(redStartAdjustment)
        this.blueStartPose = startPose.transformBy(blueStartAdjustment)
        this.actions = actions.copyOf()
    }

    @Suppress("SpreadOperator")
    public constructor(name: String, vararg actions: Action) :
        this(name = name, startPose = Pose2d(Vector2(0, 0), Rotation2d.fromDegrees(0.0)), actions = *actions)

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
