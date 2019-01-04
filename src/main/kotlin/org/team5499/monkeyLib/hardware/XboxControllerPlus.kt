package org.team5499.monkeyLib.hardware

import kotlinx.coroutines.delay

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.RumbleType

public class XboxControllerPlus(portNumber: Int) : XboxController(portNumber) {

    public enum class RumbleSide {
        LEFT,
        RIGHT,
        BOTH
    }

    public val mPortNumber: Int

    init {
        mPortNumber = portNumber
    }

    /**
    *  @param duration the duration, in seconds, that the controller will rumble
    *  @param stregth stregth that the controller will rumble (from 0.0 to 1.0)
    *  @param side side of the controller desired to rumble
    */
    public suspend fun rumbleForSeconds(duration: Double, strength: Double, side: RumbleSide) {
        when (side) {
            RumbleSide.LEFT -> {
                super.setRumble(RumbleType.kLeftRumble, strength)
            }
            RumbleSide.RIGHT -> {
                super.setRumble(RumbleType.kRightRumble, strength)
            }
            RumbleSide.BOTH -> {
                super.setRumble(RumbleType.kLeftRumble, strength)
                super.setRumble(RumbleType.kRightRumble, strength)
            }
        }
        delay((duration * 1000).toLong())
        when (side) {
            RumbleSide.LEFT -> {
                super.setRumble(RumbleType.kLeftRumble, 0.0)
            }
            RumbleSide.RIGHT -> {
                super.setRumble(RumbleType.kRightRumble, 0.0)
            }
            RumbleSide.BOTH -> {
                super.setRumble(RumbleType.kLeftRumble, 0.0)
                super.setRumble(RumbleType.kRightRumble, 0.0)
            }
        }
    }
}
