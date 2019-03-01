package org.team5499.monkeyLib

import org.team5499.monkeyLib.util.time.ITimer
import org.team5499.monkeyLib.util.time.WPITimer

public abstract class Controller(timer: ITimer = WPITimer()) {

    protected val timer: ITimer

    init {
        this.timer = timer
    }

    abstract fun start()

    abstract fun update()

    abstract fun reset()
}
