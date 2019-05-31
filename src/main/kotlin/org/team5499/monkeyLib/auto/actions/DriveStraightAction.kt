package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.derived.LinearVelocity
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.math.units.inch
import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain

import kotlin.math.absoluteValue

class DriveStraightAction(
    timeout: Double,
    private val drivetrain: IDrivetrain,
    private val distance: Length,
    private val acceptableDistanceError: Length = 2.inch, // inches
    private val acceptableVelocityError: LinearVelocity = 2.inch.velocity // inches / s
) : Action(timeout) {

    override fun start() {
        super.start()
        drivetrain.setPosition(distance)
    }

    override fun next(): Boolean {
        return super.next() ||
        (
            drivetrain.leftDistanceError.absoluteValue < acceptableDistanceError &&
            drivetrain.rightDistanceError.absoluteValue < acceptableDistanceError &&
            drivetrain.leftVelocity.absoluteValue < acceptableVelocityError &&
            drivetrain.rightVelocity.absoluteValue < acceptableVelocityError
        )
    }
}
