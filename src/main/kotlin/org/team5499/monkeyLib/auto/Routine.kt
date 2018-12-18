package org.team5499.monkeyLib.auto.routines

import org.team5499.monkeyLib.auto.Action

import org.team5499.monkeyLib.math.geometry.Rotation2d

class Routine(name: String, startHeading: Rotation2d, vararg actions: Action) {

    val actions: Array<out Action>
    var stepNumber: Int
        private set
        get() = field
    val mName: String
        get() = field
    val startHeading: Rotation2d
        get() = field

    init {
        this.stepNumber = 0
        this.mName = name
        this.startHeading = startHeading
        this.actions = actions.copyOf()
    }

    @Suppress("SpreadOperator")
    constructor(
        name: String,
        degreesHeading: Double,
        vararg actions: Action
    ): this(name, Rotation2d.fromDegrees(degreesHeading), *actions)

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

    public fun setActionIndex(index: Int) {
        stepNumber = index
    }

    public fun reset() {
        this.stepNumber = 0
    }

    public fun isLastStep(): Boolean {
        return (stepNumber >= (actions.size - 1))
    }
}
