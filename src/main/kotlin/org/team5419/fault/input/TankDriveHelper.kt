package org.team5419.fault.input

public class TankDriveHelper(deadband: Double, slowMultiplier: Double) : DriveHelper() {

    private val mDeadband: Double
    private val mSlowMult: Double

    init {
        mDeadband = deadband
        mSlowMult = slowMultiplier
    }

    public override fun calculateOutput(left: Double, right: Double, isSlow: Boolean, notUsed: Boolean): DriveSignal {
        var newLeft = super.handleDeadband(left, mDeadband)
        var newRight = super.handleDeadband(right, mDeadband)
        if (isSlow) {
            newLeft *= mSlowMult
            newRight *= mSlowMult
        }
        return DriveSignal(newLeft, newRight)
    }
}
