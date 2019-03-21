package org.team5499.monkeyLib.util.loops

import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.Timer

import java.lang.Runnable

public class Looper(val period: Double = 0.005) {

    private var mRunning: Boolean
    private val mNotifier: Notifier
    private val mLoops: MutableList<Loop> = mutableListOf()
    private val mLock = Any()

    private var mTimestamp = 0.0
    private var mDt = 0.0

    init {
        mNotifier = Notifier(mRunnable)
        mRunning = false
    }

    private val mRunnable: Runnable
        get() = Runnable() {
            synchronized(mLock) {
                if (mRunning) {
                    val now = Timer.getFPGATimestamp()
                    for (loop in mLoops) {
                        loop.onLoop(now)
                    }

                    mDt = now - mTimestamp
                    mTimestamp = now
                }
            }
        }

    @Synchronized public fun register(loop: Loop) {
        synchronized(mLock) {
            mLoops.add(loop)
        }
    }

    @Synchronized public fun start() {
        if (!mRunning) {
            println("Starting loops....")
            synchronized(mLock) {
                mTimestamp = Timer.getFPGATimestamp()
                for (loop in mLoops) {
                    loop.onStart(mTimestamp)
                }
                mRunning = true
            }
            mNotifier.startPeriodic(period)
        }
    }

    @Synchronized public fun stop() {
        if (mRunning) {
            println("Stopping loops")
            mNotifier.stop()
            synchronized(mLock) {
                mRunning = false
                mTimestamp = Timer.getFPGATimestamp()
                for (loop in mLoops) {
                    println("Stopping loop: $loop")
                    loop.onStop(mTimestamp)
                }
            }
        }
    }
}
