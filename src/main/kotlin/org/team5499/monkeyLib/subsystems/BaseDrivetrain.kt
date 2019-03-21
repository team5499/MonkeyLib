package org.team5499.monkeyLib.subsystems

import org.team5499.monkeyLib.math.Position

public abstract class BaseDrivetrain : Subsystem() {

    public enum class DriveMode {
        PERCENT,
        VELOCITY,
        POSITION,
        TURN
    }

    val position: Position

    val pose: Pose2d

    abstract val leftPositionRaw: Double

    abstract val rightPositionRaw: Double

    abstract val leftPositionInches: Double

    abstract val rightPositionInches: Double

    abstract val angle: Double

    abstract fun setPercent(left: Double, right: Double)

    abstract fun setVelocity(left: Double, right: Double)

    abstract fun setPosition(distance: Double)

    abstract fun setTurn(angle: Double)

    public override fun stop() {
        setPercent(0.0, 0.0)
    }

    // public override fun update() {
    //     position.update(leftPositionInches, rightPositionInches, )
    // }

    public override fun reset() {
        position.reset()
    }
}
