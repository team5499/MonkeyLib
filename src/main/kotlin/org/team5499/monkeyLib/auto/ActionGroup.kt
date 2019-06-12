package org.team5499.monkeyLib.auto

abstract class ActionGroup internal constructor() : Action() {

    internal abstract val actions: MutableList<out Action>
}

open class ParallelAction(actions: MutableList<Action>) : ActionGroup() {

    override val actions: MutableList<Action>

    constructor(vararg actions: Action) : this(actions.toMutableList())

    init {
        this.actions = actions
        actions.forEach { finishCondition += { it.next() } }
        finishCondition += { timedOut() }
    }

    override fun start() {
        super.start()
        actions.forEach { it.start() }
    }

    override fun update() {
        actions.forEach { it.update() }
    }

    override fun finish() {
        actions.forEach { it.finish() }
    }
}

open class SerialAction(actions: MutableList<Action>) : ActionGroup() {

    constructor(vararg actions: Action) : this(actions.toMutableList())

    private var index = 0
    private val isLastAction = { index == (actions.size - 1) }
    private val isLastActionDone = { actions.last().next() }

    override val actions: MutableList<Action>

    init {
        finishCondition += { timedOut() }
        finishCondition += { isLastAction() }
        finishCondition += { isLastActionDone() }
        this.actions = actions.toMutableList()
        assert(this.actions.size < 1) { "No actions added to command group" }
        index = 0
    }

    override fun start() {
        super.start()
        index = 0
        actions[index].start()
    }

    override fun update() {
        super.update()
        if (isLastAction() && isLastActionDone()) return
        else if (actions[index].next() && !isLastAction()) {
            actions[index].finish()
            index++
            actions[index].start()
        }
        actions[index].update()
    }

    override fun finish() {
        super.finish()
        actions.last().finish()
    }
}
