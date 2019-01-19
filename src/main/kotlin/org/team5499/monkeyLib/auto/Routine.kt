package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.math.geometry.Rotation2d

/**
 * A list of Actions to run sequentially
 * @param name The name of the routine
 * @param startHeading The rotation the robot starts (used to callibrate gyro)
 * @param actions The actions to run, in order of execution
 */
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

    /**
     * Returns the action the robot is currently executing
     * @return Current action
     */
    public fun getCurrentAction(): Action {
        return actions.get(stepNumber)
    }

    /**
     * Forces the robot to move to the next action
     * @return Is the last step in the routine
     */
    public fun advanceRoutine(): Boolean {
        if (isLastStep()) {
            return false
        }
        stepNumber++
        return true
    }

    /**
     * Abort the current action and run the sequence from the given action index
     */
    public fun setActionIndex(index: Int) {
        stepNumber = index
    }

    /**
     * Reset to the first action
     */
    public fun reset() {
        stepNumber = 0
    }

    /**
     * Gets whether the sequence is currently on it's last step
     * @return is last step?
     */
    public fun isLastStep(): Boolean {
        return (stepNumber >= (actions.size - 1))
    }
}
