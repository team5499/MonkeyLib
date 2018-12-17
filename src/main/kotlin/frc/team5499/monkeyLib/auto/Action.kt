package frc.team5499.monkeyLib.auto

import edu.wpi.first.wpilibj.Timer

public open class Action(timeoutSeconds: Double) {
    private val mTimer: Timer
    private val mTimeoutSeconds: Double

    init {
        mTimer = Timer()
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
