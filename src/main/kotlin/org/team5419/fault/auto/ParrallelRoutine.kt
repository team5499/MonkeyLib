
package org.team5419.fault.auto

import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2d

public class ParrallelRoutine(name: String, startPose: Pose2d, vararg routines: Routine) {
/*: Routine(name, startPose, routines) {*/

    private val routines: Array<out Routine>
    var stepNumber: Int
        get() = field
    val name: String
        get() = field
    val startPose: Pose2d
        get() = field

    @Suppress("SpreadOperator")
    public constructor(name: String, vararg routines: Routine) :
        this(name, Pose2d(Vector2(0, 0), Rotation2d.fromDegrees(0.0)), *routines)

    init {
        this.stepNumber = 0
        this.name = name
        this.startPose = startPose
        this.routines = routines.copyOf()
    }

    // use an array instead?
    // returns MutableList of all current Actions
    public fun getCurrentActions(): MutableList<Action> {
        val actions: MutableList<Action> = mutableListOf<Action>()
        for (r: Routine in this.routines) actions.add(r.getCurrentAction())
        return actions
    }

    // advance all subroutines
    public fun advanceRoutine() {
        if (!this.isLastStep()) {
            stepNumber++
            for (r: Routine in this.routines) r.advanceRoutine()
        }
    }

    // returns true if all subroutines are done
    public fun isLastStep(): Boolean {
        for (r: Routine in this.routines) {
            if (!r.isLastStep()) {
                return false
            }
        }
        return true
    }

    // resets all subroutines
    public fun reset() {
        this.stepNumber = 0
        for (r: Routine in this.routines) {
            r.reset()
        }
    }
}
