package org.team5499.monkeyLib.hardware

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.RumbleType
import edu.wpi.first.wpilibj.Timer

public class XboxControllerPlus(portNumber: Int) : XboxController(portNumber) {

    companion object {
        private const val kRumbleThreadPeriod = 100L // tenth of a second
    }

    public enum class RumbleSide {
        NONE,
        LEFT,
        RIGHT,
        BOTH
    }

    private val mPortNumber: Int
    private val mRumbleThread: Thread
    private val mTimer: Timer

    private var mStrength: Double
    private var mDuration: Double
    private var mSide: RumbleSide

    init {
        mPortNumber = portNumber

        mStrength = 0.0
        mDuration = 0.0
        mSide = RumbleSide.NONE

        mTimer = Timer()
        mRumbleThread = Thread(Runnable {
                synchronized(this) {
                    if (mTimer.get() >= mDuration) {
                        mTimer.stop()
                        mDuration = 0.0
                        mStrength = 0.0
                        mSide = RumbleSide.NONE
                    }
                    when (mSide) {
                        RumbleSide.BOTH -> {
                            super.setRumble(RumbleType.kLeftRumble, mStrength)
                            super.setRumble(RumbleType.kRightRumble, mStrength)
                        }
                        RumbleSide.LEFT -> {
                            super.setRumble(RumbleType.kLeftRumble, mStrength)
                        }
                        RumbleSide.RIGHT -> {
                            super.setRumble(RumbleType.kRightRumble, mStrength)
                        }
                        RumbleSide.NONE -> {
                            super.setRumble(RumbleType.kLeftRumble, 0.0)
                            super.setRumble(RumbleType.kRightRumble, 0.0)
                        }
                    }
                    Thread.sleep(kRumbleThreadPeriod) // sleep for a tenth of a second to save cpu time
                }
            },
            "rumble-thread-$mPortNumber" // name of thread
        )
        mRumbleThread.start()
    }

    /**
    *  @param duration the duration, in seconds, that the controller will rumble
    *  @param stregth stregth that the controller will rumble (from 0.0 to 1.0)
    *  @param side side of the controller desired to rumble
    */
    @Synchronized
    public fun rumbleForSeconds(duration: Double, strength: Double, side: RumbleSide) {
        mDuration = duration
        mStrength = strength
        mSide = side
        mTimer.stop()
        mTimer.reset()
        mTimer.start()
    }
}
