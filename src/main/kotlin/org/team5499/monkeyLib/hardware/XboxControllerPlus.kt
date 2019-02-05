package org.team5499.monkeyLib.hardware

import edu.wpi.first.wpilibj.XboxController

import org.team5499.monkeyLib.util.time.ITimer
import org.team5499.monkeyLib.util.time.WPITimer

// adapted from 1323
public class XboxControllerPlus(portNumber: Int, timer: ITimer = WPITimer()) : XboxController(portNumber) {

    public var rumbling = false
        private set

    private val mTimer: ITimer

    init {
        mTimer = timer
    }

    public fun rumble(rumblesPerSecond: Double, numberOfSeconds: Double) {
        if (!rumbling) {
            val thread = RumbleThread(rumblesPerSecond, numberOfSeconds)
            thread.start()
        }
    }

    private inner class RumbleThread(rumblesPerSecond: Double, numberOfSeconds: Double) : Thread() {
        private val mRumblesPerSecond: Double
        private val mInterval: Long
        private val mSeconds: Double

        init {
            mRumblesPerSecond = rumblesPerSecond
            mSeconds = numberOfSeconds
            mInterval = (1 / (rumblesPerSecond * 2.0) * 1000.0).toLong()
        }

        public override fun start() {
            rumbling = true
            mTimer.stop()
            mTimer.reset()
            mTimer.start()
            try {
                while (mTimer.get() < mSeconds) {
                    setRumble(RumbleType.kLeftRumble, 1.0)
                    setRumble(RumbleType.kRightRumble, 1.0)
                    sleep(mInterval)
                    setRumble(RumbleType.kLeftRumble, 0.0)
                    setRumble(RumbleType.kRightRumble, 0.0)
                    sleep(mInterval)
                }
            } catch (e: InterruptedException) {
                rumbling = false
                e.printStackTrace()
            }
            rumbling = false
        }
    }
}
