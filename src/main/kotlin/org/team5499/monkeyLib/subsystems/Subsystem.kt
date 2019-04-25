package org.team5499.monkeyLib.subsystems

import org.team5499.monkeyLib.util.loops.ILooper

abstract class Subsystem {

    abstract fun stop()

    abstract fun zeroSensors()

    open fun updateDashboard() {}

    open fun registerLoops(looper: ILooper) {}
}
