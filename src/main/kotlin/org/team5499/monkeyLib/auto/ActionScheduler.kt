package org.team5499.monkeyLib.auto

// private data class ActionState(var started: Boolean = false, var finished: Boolean = false)
//
// object ActionScheduler {
//
//    private var mEnabled = false
//
//    private val mScheduledCommands = linkedMapOf<Action, ActionState>()
//
//    fun enable() { mEnabled = true }
//
//    fun disable() { mEnabled = false }
//
//    fun add(action: Action) {
//        mScheduledCommands.put(action, ActionState())
//    }
//
//    fun add(vararg actions: Action) {
//        for(action in actions.toList()) {
//            add(action)
//        }
//    }
//
//    fun clear() {
//        mScheduledCommands.clear()
//    }
//
//    fun run() {
//        if(mEnabled) {
//            return
//        }
//
//        val iterator = mScheduledCommands.iterator()
//        while(iterator.hasNext()) {
//            val (action, state) = iterator.next()
//
//            if(!state.started) { // start commands that haven't been started yet
//                action.start()
//                state.started = true
//            }
//
//            if(!state.finished && action.next()) { // check for commands that are finished
//                state.finished = true
//            }
//
//            // update commands if needed
//            if(state.started && !state.finished) {
//                action.update()
//            }
//
//            // if finished, call finish method and remove from list
//            if(state.finished) {
//                action.finish()
//                iterator.remove()
//                continue
//            }
//        }
//    }
//
//
// }
