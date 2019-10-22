package org.team5419.fault.auto

import org.team5419.fault.util.time.ITimer
import org.team5419.fault.util.time.WPITimer

public open class Action(timeoutSeconds: Double, timer: ITimer = WPITimer()) {

    private val mTimer: ITimer
    private val mTimeoutSeconds: Double

    init {
        mTimer = timer
        mTimeoutSeconds = timeoutSeconds
    }

    public open fun start() {
        mTimer.stop()
        mTimer.reset()
        mTimer.start()
    }

    public open fun update() {}

    protected fun timedOut(): Boolean {
        val t = mTimer.get()
        return (t >= mTimeoutSeconds)
    }

    public open fun next(): Boolean {
        return timedOut()
    }

    public open fun finish() {}
}
