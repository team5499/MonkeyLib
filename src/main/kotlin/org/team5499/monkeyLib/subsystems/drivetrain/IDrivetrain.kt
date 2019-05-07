package org.team5499.monkeyLib.subsystems.drivetrain

import org.team5499.monkeyLib.input.DriveSignal
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Vector2

import org.team5499.monkeyLib.math.physics.DifferentialDrive

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
    val angularVelocity: Double
    val pose: Pose2d
    val position: Vector2
    val model: DifferentialDrive

    val leftDistance: Double
    val rightDistance: Double

    val leftVelocity: Double
    val rightVelocity: Double
    val averageVelocity get() = (leftVelocity + rightDistance) / 2.0

    val leftDistanceError: Double
    val rightDistanceError: Double
    val averageDistanceError get() = (leftDistanceError + rightDistanceError) / 2.0

    val leftVelocityError: Double
    val rightVelocityError: Double
    val averageVelocityError get() = (leftVelocityError + rightVelocityError) / 2.0

    val turnError: Double

    fun setPercent(signal: DriveSignal) = setPercent(signal.left, signal.right)
    fun setPercent(percent: Double) = setPercent(percent, percent)
    fun setPercent(left: Double, right: Double)

    fun setVelocity(velo: Double) = setVelocity(velo, velo)
    /**
    * method that sets the velocity of the drivetrain
    * @param left velocity desired for left side of drivetrain
    * @param right velocity desired for right side of drivetrain
    * @param leftVoltage left voltage
    * @param rightVoltage right voltage
    */
    fun setVelocity(left: Double, right: Double, leftVoltage: Double = 0.0, rightVoltage: Double = 0.0)

    fun setTurn(degrees: Double, type: TurnType = TurnType.RELATIVE)

    fun setPosition(distance: Double) = setPosition(distance, distance)
    fun setPosition(leftDistance: Double, rightDistance: Double)
}
