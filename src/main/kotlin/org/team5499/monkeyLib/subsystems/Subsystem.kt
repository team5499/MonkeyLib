package org.team5499.monkeyLib.subsystems

import org.team5499.monkeyLib.util.time.ITimer
import org.team5499.monkeyLib.util.time.WPITimer

abstract class Subsystem(timer: ITimer = WPITimer()) {

    protected val timer: ITimer

    init {
        this.timer = timer
    }

    abstract fun update()

    abstract fun stop()

    abstract fun reset()
}
