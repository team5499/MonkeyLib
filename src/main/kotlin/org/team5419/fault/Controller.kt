package org.team5419.fault

import org.team5419.fault.util.time.ITimer
import org.team5419.fault.util.time.WPITimer

public abstract class Controller(timer: ITimer = WPITimer()) {

    protected val timer: ITimer

    init {
        this.timer = timer
    }

    abstract fun start()

    abstract fun update()

    abstract fun reset()
}
