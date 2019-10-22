package org.team5419.fault.auto

import org.team5419.fault.util.time.ITimer
import org.team5419.fault.util.time.WPITimer

class ParallelAction(vararg actions: Action, timer: ITimer = WPITimer()) : Action(0.0, timer) {

    private val mActions: Array<out Action>

    init {
        mActions = actions.copyOf()
    }

    override fun start() {
        super.start()
        for (a: Action in mActions) {
            a.start()
        }
    }

    override fun update() {
        super.update()
        for (a: Action in mActions) {
            a.update()
        }
    }

    @Suppress("ReturnCount")
    override fun next(): Boolean {
        for (a: Action in mActions) {
            if (!a.next()) {
                return false
            }
        }
        return true
    }

    override fun finish() {
        super.finish()
        for (a: Action in mActions) {
            a.finish()
        }
    }
}
