package org.team5499.monkeyLib.subsystems.drivetrain

import org.team5499.monkeyLib.input.DriveSignal
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Vector2

import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.math.units.Length
import org.team5499.monkeyLib.math.units.derived.AngularVelocity
import org.team5499.monkeyLib.math.units.derived.LinearVelocity
import org.team5499.monkeyLib.math.units.derived.Volt
import org.team5499.monkeyLib.math.units.derived.volt

public interface IDrivetrain {

    enum class TurnType {
        RELATIVE,
        ABSOLUTE
    }

    enum class DriveMode {
        PERCENT,
        VELOCITY,
        POSITION,
        TURN
    }

    // these are vals because kotlin has weird semantics around override getters
    // and setters from interfaces
    val braking: Boolean
    val heading: Rotation2d
    val angularVelocity: AngularVelocity
    val pose: Pose2d
    val position: Vector2
    val model: DifferentialDrive

    val leftOutputVoltage: Volt
    val rightOutputVoltage: Volt
    val averageOutputVoltage get() = (leftOutputVoltage + rightOutputVoltage) / 2.0

    val leftDistance: Length
    val rightDistance: Length

    val leftVelocity: LinearVelocity
    val rightVelocity: LinearVelocity
    val averageVelocity get() = (leftVelocity + rightVelocity) / 2.0

    val leftDistanceError: Length
    val rightDistanceError: Length
    val averageDistanceError get() = (leftDistanceError + rightDistanceError) / 2.0

    val leftVelocityError: LinearVelocity
    val rightVelocityError: LinearVelocity
    val averageVelocityError get() = (leftVelocityError + rightVelocityError) / 2.0

    val turnError: Rotation2d

    fun setPercent(signal: DriveSignal) = setPercent(signal.left, signal.right)
    fun setPercent(percent: Double) = setPercent(percent, percent)
    fun setPercent(left: Double, right: Double)

    fun setVelocity(velo: LinearVelocity) = setVelocity(velo, velo)
    /**
    * method that sets the velocity of the drivetrain
    * @param left velocity desired for left side of drivetrain
    * @param right velocity desired for right side of drivetrain
    * @param leftVoltage left voltage
    * @param rightVoltage right voltage
    */
    fun setVelocity(
        left: LinearVelocity,
        right: LinearVelocity,
        leftVoltage: Volt = 0.volt,
        rightVoltage: Volt = 0.volt
    )

    fun setTurn(degrees: Rotation2d, type: TurnType = TurnType.RELATIVE)

    fun setPosition(distance: Length) = setPosition(distance, distance)
    fun setPosition(leftDistance: Length, rightDistance: Length)
}
