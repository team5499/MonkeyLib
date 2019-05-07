package org.team5499.monkeyLib.subsystems

import org.team5499.monkeyLib.util.loops.ILooper

class SubsystemsManager(vararg subsystems: Subsystem) {

    private val mSubsystems: Array<out Subsystem>

    init {
        mSubsystems = subsystems.copyOf()
    }

    fun stop() = mSubsystems.forEach { it.stop() }

    fun zeroSensors() = mSubsystems.forEach { it.zeroSensors() }

    fun updateDashboard() = mSubsystems.forEach { it.updateDashboard() }

    fun registerLoops(looper: ILooper) = mSubsystems.forEach { it.registerLoops(looper) }
}
