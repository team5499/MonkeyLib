package org.team5499.monkeyLib.input

public class SpaceDriveHelper(deadband: Double, turnMult: Double, slowMult: Double) : DriveHelper() {

    private val mDeadband: Double
    private val mTurnMult: Double
    private val mSlowMult: Double

    init {
        mDeadband = deadband
        mTurnMult = turnMult
        mSlowMult = slowMult
    }

    public override fun calculateOutput(
        throttle: Double,
        wheel: Double,
        isQuickTurn: Boolean,
        creep: Boolean
    ): DriveSignal {
        val newThottle = super.handleDeadband(throttle, mDeadband)
        var newWheel = super.handleDeadband(wheel, mDeadband)
        val mult = if (!isQuickTurn) mTurnMult else 1.0
        newWheel *= mult
        val slow = if (creep) mSlowMult else 1.0
        return DriveSignal(slow * (newThottle + newWheel), slow * (newThottle - newWheel))
    }
}
