package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain

import kotlin.math.abs

class DriveStraightAction(
    timeout: Double,
    private val drivetrain: IDrivetrain,
    private val distance: Double,
    private val acceptableDistanceError: Double = 2.0, // inches
    private val acceptableVelocityError: Double = 2.0 // inches / s
) : Action(timeout) {

    override fun start() {
        super.start()
        drivetrain.setPosition(distance)
    }

    override fun next(): Boolean {
        return super.next() ||
        (
            abs(drivetrain.leftDistanceError) < acceptableDistanceError &&
            abs(drivetrain.rightDistanceError) < acceptableDistanceError &&
            abs(drivetrain.leftVelocity) < acceptableVelocityError &&
            abs(drivetrain.rightVelocity) < acceptableVelocityError
        )
    }
}
