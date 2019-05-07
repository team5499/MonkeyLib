package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain

import kotlin.math.abs
// ALLOW for absolute turn in future (using rotation2d math maybe)
class DriveTurnAction(
    timeout: Double,
    private val drivetrain: IDrivetrain,
    private val degrees: Double,
    private val turnType: IDrivetrain.TurnType = IDrivetrain.TurnType.RELATIVE,
    private val acceptableTurnError: Double = 2.0, // degrees
    private val acceptableVelocityError: Double = 2.0 // inches / s
) : Action(timeout) {

    override fun start() {
        super.start()
        drivetrain.setTurn(degrees, turnType)
    }

    override fun next(): Boolean {
        return super.next() || (
            abs(drivetrain.turnError) < acceptableTurnError &&
            abs(drivetrain.averageVelocity) < acceptableVelocityError // could check angular velo as well
        )
    }
}
