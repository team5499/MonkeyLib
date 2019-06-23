package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.degree
import org.team5499.monkeyLib.math.units.derived.AngularVelocity
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.subsystems.drivetrain.AbstractTankDrive

// ALLOW for absolute turn in future (using rotation2d math maybe)
class DriveTurnAction(
    private val drivetrain: AbstractTankDrive,
    private val rotation: Rotation2d,
    private val turnType: AbstractTankDrive.TurnType = AbstractTankDrive.TurnType.Relative,
    private val acceptableTurnError: Rotation2d = 3.degree, // degrees
    private val acceptableVelocityError: AngularVelocity = 5.degree.velocity // inches / s
) : Action() {

    private val turnErrorAcceptable = { drivetrain.turnError < acceptableTurnError }
    private val angularVelocityAcceptable = { drivetrain.angularVelocity < acceptableVelocityError }

    override fun start() {
        super.start()
        drivetrain.setTurn(rotation, turnType)

        finishCondition += { turnErrorAcceptable() }
        finishCondition += { angularVelocityAcceptable() }
    }

    override fun finish() {
        super.finish()
        drivetrain.zeroOutputs()
    }
}
