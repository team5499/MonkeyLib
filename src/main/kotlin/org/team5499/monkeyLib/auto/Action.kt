package org.team5499.monkeyLib.auto

import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.util.BooleanSource
import org.team5499.monkeyLib.util.Source
import org.team5499.monkeyLib.util.or
import org.team5499.monkeyLib.util.time.ITimer
import org.team5499.monkeyLib.util.time.WPITimer

typealias NothingAction = Action

open class Action(
    private val timer: ITimer = WPITimer()
) {

    protected var timeout = Time(0.0)

    protected var finishCondition = FinishCondition(Source(false))

    open fun start() {
        timer.stop()
        timer.reset()
        timer.start()
    }

    open fun update() {}

    protected fun timedOut(): Boolean {
        val t = timer.get()
        return (t >= timeout)
    }

    open fun next(): Boolean {
        return finishCondition()
    }

    open fun finish() {}

    protected class FinishCondition(
        private var source: BooleanSource
    ) : BooleanSource {
        override fun invoke() = source()

        operator fun plusAssign(other: BooleanSource) {
            source = source or other
        }

        fun set(other: BooleanSource) {
            source = other
        }
    }

    fun withExit(condition: BooleanSource) = also { finishCondition += condition }

    fun overrideExit(condition: BooleanSource) = also { finishCondition.set(condition) }

    fun withTimeout(time: Time) = also { this.timeout = time }
}

open class ConditionalAction(
    private val condition: BooleanSource,
    private val ifTrue: Action? = null,
    private val ifFalse: Action? = null
) : Action() {

    private var selectedAction: Action? = null
    private val selectedActionDone = { if (selectedAction != null) selectedAction!!.next() else true }

    init {
        finishCondition += { timedOut() }
        finishCondition += selectedActionDone
    }

    override fun start() {
        super.start()
        selectedAction = if (condition()) ifTrue else ifFalse
        if (selectedAction != null) selectedAction!!.start()
    }

    override fun update() {
        super.update()
        if (selectedAction != null) selectedAction!!.update()
    }

    override fun finish() {
        if (selectedAction != null) selectedAction!!.finish()
        selectedAction = null
    }
}
