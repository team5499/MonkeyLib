package org.team5499.monkeyLib.subsystems

import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.Position

import org.team5499.monkeyLib.input.DriveSignal

@Suppress("TooManyFunctions")
public abstract class Drivetrain : Subsystem() {

    protected enum class DriveMode {
        PERCENT,
        VELOCITY,
        POSITION,
        TURN
    }

    public enum class TurnType {
        ABSOLUTE,
        RELATIVE
    }

    protected var mDriveMode: DriveMode = DriveMode.PERCENT
        set(value) {
            if (value == field) return
            when (value) {
                DriveMode.PERCENT -> configureForPercent()
                DriveMode.VELOCITY -> configureForVelocity()
                DriveMode.POSITION -> configureForPosition()
                DriveMode.TURN -> configureForTurn()
            }
            field = value
        }

    protected val mPosition = Position()

    public fun setPosition(position: Vector2) {
        mPosition.positionVector = position
    }

    public abstract var brake: Boolean

    public abstract var heading: Rotation2d

    public abstract val angularVelocity: Double

    public val pose: Pose2d
        get() {
            return Pose2d(mPosition.positionVector, heading)
        }

    public abstract var leftDistance: Double
    public abstract var rightDistance: Double
    public val averageDistance: Double
        get() = (leftDistance + rightDistance) / 2.0

    public abstract val positionError: Double

    public abstract val leftVelocity: Double
    public abstract val rightVelocity: Double
    public val averageVelocity: Double
        get() = (leftVelocity + rightDistance) / 2.0

    public abstract val leftVelocityError: Double
    public abstract val rightVelocityError: Double
    public val averageVelocityError: Double
        get() = (leftVelocityError + rightVelocityError) / 2.0

    public abstract val anglePositionError: Double

    public abstract val turnError: Double
    public abstract val turnFixedError: Double

    public abstract fun setPercent(left: Double, right: Double, brake: Boolean = false)
    public fun setPercent(signal: DriveSignal) {
        setPercent(signal.left, signal.right, signal.brakeMode)
    }

    public abstract fun setVelocity(left: Double, right: Double, brake: Boolean = false)
    public fun setVelocity(signal: DriveSignal) {
        setVelocity(signal.left, signal.right, signal.brakeMode)
    }

    public abstract fun setPosition(distance: Double)

    public abstract fun setTurn(degrees: Double, type: TurnType = TurnType.RELATIVE)

    public abstract fun configureForPercent()

    public abstract fun configureForVelocity()

    public abstract fun configureForPosition()

    public abstract fun configureForTurn()

    public override fun stop() {
        setPercent(0.0, 0.0)
    }

    public override fun reset() {
        stop()
        mPosition.reset()
        leftDistance = 0.0
        rightDistance = 0.0
        brake = false
    }

    public override fun update() {
        mPosition.update(leftDistance, rightDistance, heading.degrees)
    }
}
