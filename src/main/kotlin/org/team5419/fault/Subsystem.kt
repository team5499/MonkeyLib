package org.team5419.fault

import org.team5419.fault.util.time.ITimer
import org.team5419.fault.util.time.WPITimer

abstract class Subsystem(timer: ITimer = WPITimer()) {

    protected val timer: ITimer

    init {
        this.timer = timer
    }

    abstract fun update()

    abstract fun stop()

    abstract fun reset()
}
