package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.derived.LinearVelocity
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.math.units.inch
import org.team5499.monkeyLib.subsystems.drivetrain.AbstractTankDrive
import kotlin.math.absoluteValue

class DriveStraightAction(
    private val drivetrain: AbstractTankDrive,
    private val distance: Length,
    private val acceptableDistanceError: Length = 2.inch, // inches
    private val acceptableVelocityError: LinearVelocity = 2.inch.velocity // inches / s
) : Action() {

    private val distanceErrorAcceptable = {
            drivetrain.leftDistanceError.absoluteValue < acceptableDistanceError &&
                    drivetrain.rightDistanceError.absoluteValue < acceptableDistanceError
    }

    private val velocityErrorAcceptable = {
        drivetrain.leftMasterMotor.encoder.velocity.absoluteValue < acceptableVelocityError.value &&
                drivetrain.rightMasterMotor.encoder.velocity.absoluteValue < acceptableVelocityError.value
    }

    init {
        finishCondition += distanceErrorAcceptable
        finishCondition += velocityErrorAcceptable
    }

    override fun start() {
        super.start()
        drivetrain.setPosition(distance)
    }

    override fun finish() = drivetrain.zeroOutputs()
}
