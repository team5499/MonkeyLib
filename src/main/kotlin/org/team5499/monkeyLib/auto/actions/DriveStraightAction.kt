package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.subsystems.Drivetrain

import kotlin.math.abs

public class DriveStraightAction(
    timeout: Double,
    val distance: Double,
    val acceptableVelocity: Double = 2.0, // in / s
    val acceptableDistanceError: Double = 1.0, // in
    val drivetrain: Drivetrain
) : Action(timeout) {
    private var mIsDriving: Boolean = false

    // Called when the action starts
    public override fun start() {
        super.start()
        drivetrain.setPosition(distance)
        mIsDriving = true
    }

    public override fun next(): Boolean {
        mIsDriving = (acceptableDistanceError > abs(drivetrain.positionError)) &&
            ((acceptableVelocity > abs(drivetrain.averageVelocity)))

        // Return true if super.next() is true or
        // the distance error is less than the acceptable distance error/velocity defined in PID.
        return (super.next() || !mIsDriving)
    }

    public override fun finish() {
        // abort the drive if the action is aborted
        if (mIsDriving) {
            drivetrain.setPercent(0.0, 0.0)
        }
    }
}
