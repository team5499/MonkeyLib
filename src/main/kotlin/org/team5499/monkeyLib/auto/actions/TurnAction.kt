package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.subsystems.Drivetrain

import kotlin.math.abs

public class TurnAction(
    timeout: Double,
    val turnType: Drivetrain.TurnType = Drivetrain.TurnType.RELATIVE,
    val degrees: Double,
    val acceptableAngleError: Double = 4.0, // degrees
    val acceptableVelocity: Double = 2.0, // in / s
    val drivetrain: Drivetrain
) : Action(timeout) {

    public override fun start() {
        drivetrain.setTurn(degrees, turnType)
    }

    public override fun update() {}

    public override fun next(): Boolean {
        if (
            abs(drivetrain.turnError) < acceptableAngleError &&
            abs(drivetrain.averageVelocity) < acceptableVelocity
        ) return true
        else return false
    }

    public override fun finish() {}
}
