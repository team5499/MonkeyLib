package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.util.time.ITimer
import org.team5499.monkeyLib.util.time.WPITimer

public open class Action(timeoutSeconds: Double, timer: ITimer = WPITimer()) {

    constructor(time: Time): this(time.second)

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
