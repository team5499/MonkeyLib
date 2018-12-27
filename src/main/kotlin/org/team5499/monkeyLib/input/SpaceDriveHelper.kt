package org.team5499.monkeyLib.input

public class SpaceDriveHelper(deadband: Double, turnMult: Double) : DriveHelper() {

    private val mDeadband: Double
    private val mTurnMult: Double

    init {
        mDeadband = deadband
        mTurnMult = turnMult
    }

    fun calculateOutput(throttle: Double, wheel: Double, isQuickTurn: Boolean): DriveSignal {
        val newThottle = super.handleDeadband(throttle, mDeadband)
        var newWheel = super.handleDeadband(wheel, mDeadband)
        val mult = if (!isQuickTurn) mTurnMult else 1.0
        newWheel *= mult
        return DriveSignal(newThottle + newWheel, newThottle - newWheel)
    }
}
