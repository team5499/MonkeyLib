package org.team5499.monkeyLib.input

open class DriveHelper {

    protected fun handleDeadband(value: Double, deadband: Double): Double {
        return if (Math.abs(value) > Math.abs(deadband)) value else 0.0
    }
}
