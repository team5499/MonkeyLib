package org.team5499.monkeyLib.subsystems.drivetrain

import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.localization.PositionTracker
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.derived.AngularVelocity
import org.team5499.monkeyLib.math.units.derived.LinearVelocity
import org.team5499.monkeyLib.math.units.derived.Volt
import org.team5499.monkeyLib.subsystems.Subsystem
import kotlin.properties.Delegates

abstract class AbstractTankDrive : IDifferentialFollowerDrive, Subsystem() {

    protected abstract val localization: PositionTracker

    override var robotPosition: Pose2d
        get() = localization.robotPosition
        set(value) = localization.reset(value)

    var isBraking by Delegates.observable(false) { _, _, wantBrake ->
        leftMasterMotor.brakeMode = wantBrake
        rightMasterMotor.brakeMode = wantBrake
    }

    abstract val leftDistance: Length
    abstract val rightDistance: Length
    abstract val leftDistanceError: Length
    abstract val rightDistanceError: Length

    abstract val angularVelocity: AngularVelocity
    abstract val turnError: Rotation2d

    override fun zeroOutputs() {
        setPercent(0.0, 0.0)
    }

    abstract fun setPercent(left: Double, right: Double)
    abstract fun setTurn(angle: Rotation2d, type: TurnType = TurnType.Relative)
    abstract fun setPosition(distance: Length)
    abstract fun setVelocity(leftVelocity: LinearVelocity, rightVelocity: LinearVelocity, leftFF: Volt, rightFF: Volt)

    enum class TurnType { Relative, Absolute }
}
