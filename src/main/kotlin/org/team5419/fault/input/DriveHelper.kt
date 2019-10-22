package org.team5419.fault.input

abstract class DriveHelper {

    public abstract fun calculateOutput(arg0: Double, arg1: Double, arg2: Boolean, arg3: Boolean = false): DriveSignal

    protected fun handleDeadband(value: Double, deadband: Double): Double {
        return if (Math.abs(value) > Math.abs(deadband)) value else 0.0
    }
}
