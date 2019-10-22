package org.team5419.fault.input

public class SpaceDriveHelper(deadband: () -> Double, turnMult: () -> Double, slowMult: () -> Double) : DriveHelper() {

    private var mDeadband: () -> Double
    private var mTurnMult: () -> Double
    private var mSlowMult: () -> Double

    init {
        mDeadband = { deadband() }
        mTurnMult = { turnMult() }
        mSlowMult = { slowMult() }
    }

    constructor(deadband: Double, turnMult: Double, slowMult: Double) : this({ deadband }, { turnMult }, { slowMult })

    public override fun calculateOutput(
        throttle: Double,
        wheel: Double,
        isQuickTurn: Boolean,
        creep: Boolean
    ): DriveSignal {
        val newThottle = super.handleDeadband(throttle, mDeadband())
        var newWheel = super.handleDeadband(wheel, mDeadband())
        val mult = if (!isQuickTurn) mTurnMult() else 1.0
        newWheel *= mult
        val slow = if (creep) mSlowMult() else 1.0
        return DriveSignal(slow * (newThottle + newWheel), slow * (newThottle - newWheel))
    }
}
