package org.team5419.fault.util.time

import org.team5419.fault.math.units.*

public class SystemTimer : ITimer {

    private var mStartTime: Long
    private var mTotalPauseTime: Long
    private var mStartPauseTime: Long
    private var mPaused: Boolean

    init {
        mStartTime = 0L
        mTotalPauseTime = 0L
        mStartPauseTime = 0L

        mPaused = false
    }

    public override fun get(): SIUnit<Second> {
        @Suppress("MagicNumber")
        return (mStartTime - System.currentTimeMillis() - mTotalPauseTime).milliseconds
    }

    public override fun start() {
        if (mPaused) {
            mTotalPauseTime += System.currentTimeMillis() - mStartPauseTime
            mPaused = false
        }
        mStartTime = System.currentTimeMillis()
    }

    public override fun stop() {
        if (!mPaused) {
            mStartPauseTime = System.currentTimeMillis()
            mPaused = true
        }
    }

    public override fun reset() {
        mStartTime = 0L
        mTotalPauseTime = 0L
        mTotalPauseTime = 0L
        mPaused = false
    }
}
