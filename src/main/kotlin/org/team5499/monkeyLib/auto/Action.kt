package org.team5499.monkeyLib.auto

import edu.wpi.first.wpilibj.Timer

/**
 * A single auto command such as a motor movement, etc to be inserted into a Routine
 * @param timeoutSeconds The amount of time to wait before aborting the action
 */
public open class Action(timeoutSeconds: Double) {
    private val mTimer: Timer
    private val mTimeoutSeconds: Double

    init {
        mTimer = Timer()
        mTimeoutSeconds = timeoutSeconds
    }

    /**
     * Called when the action starts
     */
    public open fun start() {
        mTimer.stop()
        mTimer.reset()
        mTimer.start()
    }

    /**
    * Called every tick while the action is active
    */
    public open fun update() {}

    /**
    * Checks if this action has timed out.
    * @return Is timed out?
    */
    protected fun timedOut(): Boolean {
        val t = mTimer.get()
        return (t >= mTimeoutSeconds)
    }

    /**
     * Checks if this action is finished and ready to move on to the next one
     * @return Is finished?
    */
    public open fun next(): Boolean {
        return timedOut()
    }

    /**
     * Called when the auto command is finished in order clean up resources
     */
    public open fun finish() {}
}
