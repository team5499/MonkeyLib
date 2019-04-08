package org.team5499.monkeyLib.subsystems

import org.team5499.monkeyLib.util.time.ITimer
import org.team5499.monkeyLib.util.time.WPITimer
import org.team5499.monkeyLib.util.loops.Looper
import org.team5499.monkeyLib.util.loops.ILooper


abstract class Subsystem(timer: ITimer = WPITimer()) {

    protected val timer: ITimer

    init {
        this.timer = timer
    }

    abstract fun stop()

    abstract fun reset()

    public fun registerLoops(looper: ILooper? = null) {}
}
