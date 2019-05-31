package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.degree
import org.team5499.monkeyLib.math.units.derived.AngularVelocity
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.subsystems.drivetrain.IDrivetrain
// ALLOW for absolute turn in future (using rotation2d math maybe)
class DriveTurnAction(
    timeout: Double,
    private val drivetrain: IDrivetrain,
    private val rotation: Rotation2d,
    private val turnType: IDrivetrain.TurnType = IDrivetrain.TurnType.RELATIVE,
    private val acceptableTurnError: Rotation2d = 3.degree, // degrees
    private val acceptableVelocityError: AngularVelocity = 5.degree.velocity // inches / s
) : Action(timeout) {

    override fun start() {
        super.start()
        drivetrain.setTurn(rotation, turnType)
    }

    override fun next(): Boolean {
        return super.next() || (
            drivetrain.turnError < acceptableTurnError &&
            drivetrain.angularVelocity < acceptableVelocityError // could check angular velo as well
        )
    }
}
