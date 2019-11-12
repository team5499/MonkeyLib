package org.team5419.fault.subsystems.drivetrain

import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.localization.PositionTracker
import org.team5419.fault.math.units.Meter
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.derived.AngularVelocity
import org.team5419.fault.math.units.derived.LinearVelocity
import org.team5419.fault.math.units.derived.Radian
import org.team5419.fault.math.units.derived.Volt
import org.team5419.fault.subsystems.Subsystem
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

    abstract val leftDistance: SIUnit<Meter>
    abstract val rightDistance: SIUnit<Meter>
    abstract val leftDistanceError: SIUnit<Meter>
    abstract val rightDistanceError: SIUnit<Meter>

    abstract val angularVelocity: SIUnit<AngularVelocity>
    abstract val turnError: SIUnit<Radian>

    override fun zeroOutputs() {
        setPercent(0.0, 0.0)
    }

    abstract fun setPercent(left: Double, right: Double)
    abstract fun setTurn(angle: Rotation2d, type: TurnType = TurnType.Relative)
    abstract fun setPosition(distance: SIUnit<Meter>)
    abstract fun setVelocity(leftVelocity: LinearVelocity, rightVelocity: LinearVelocity, leftFF: Volt, rightFF: Volt)

    enum class TurnType { Relative, Absolute }
}
