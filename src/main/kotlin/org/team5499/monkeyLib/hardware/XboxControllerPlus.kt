package org.team5499.monkeyLib.hardware

import edu.wpi.first.wpilibj.XboxController

import org.team5499.monkeyLib.util.time.ITimer
import org.team5499.monkeyLib.util.time.WPITimer

public class XboxControllerPlus(portNumber: Int, timer: ITimer = WPITimer()) : XboxController(portNumber) {

    private val mRumbling = false
    private val mTimer: ITimer

    init {
        mTimer = timer
    }

    public fun rumble() {
    }

    private class RumbleThread(rumblesPerSecond: Double, numberOfSeconds: Double) : Thread() {
        private val mRumblesPerSecond: Double
        private val mInterval: Long
        private val mSeconds: Double

        init {
            this.mRumblesPerSecond = rumblesPerSecond
            mSeconds = numberOfSeconds
            mInterval = (1 / (rumblesPerSecond * 2.0) * 1000.0).toLong()
        }

        public override fun start() {
            mRumbling = true
        }
    }

    public override fun run() {
    }
}
