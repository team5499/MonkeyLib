package org.team5499.monkeyLib.subsystems.drivetrain

import org.team5499.monkeyLib.input.DriveSignal
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.Position

import org.team5499.monkeyLib.util.loops.Loop
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
        TURN,
        PATH_FOLLOWING
    }

    var braking: Boolean
    var gyroOffset: Rotation2d
    var heading: Rotation2d
    var pose: Pose2d
    val model: DifferentialDrive

    var mDriveMode: DriveMode
    val mLoop: Loop
    val mPosition: Position

    fun setPercent(signal: DriveSignal) = setPercent(signal.left, signal.right)
    fun setPercent(left: Double, right: Double)

    fun setVelocity(velo: Double) = setVelocity(velo, velo)
    /**
    * method that sets the velocity of the drivetrain
    * @param left velocity desired for left side of drivetrain
    * @param right velocity desired for right side of drivetrain
    * @param leftFF left feeedforward term, arbitrary (from -1 to 1), voltage / 12.0
    * @param rightFF right feeedforward term, arbitrary (from -1 to 1), voltage / 12.0
    */
    fun setVelocity(left: Double, right: Double, leftFF: Double = 0.0, rightFF: Double = 0.0)

    fun setTurn(degrees: Double, type: TurnType = TurnType.RELATIVE)

    fun setPosition(distance: Double)
}
