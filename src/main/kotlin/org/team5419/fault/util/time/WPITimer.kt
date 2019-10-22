package org.team5419.fault.util.time

import edu.wpi.first.wpilibj.Timer

public class WPITimer(timer: Timer = Timer()) : ITimer {

    private val mTimer: Timer

    init {
        mTimer = timer
    }

    public override fun get() = mTimer.get()

    public override fun start() = mTimer.start()

    public override fun stop() = mTimer.stop()

    public override fun reset() = mTimer.reset()
}
