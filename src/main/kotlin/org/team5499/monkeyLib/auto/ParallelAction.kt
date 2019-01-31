package org.team5499.monkeyLib.auto

/**
 * An action that runs multiple Actions at once
 * @param actions The Actions to run
 */

class ParallelAction(vararg actions: Action) : Action(0.0) {

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
