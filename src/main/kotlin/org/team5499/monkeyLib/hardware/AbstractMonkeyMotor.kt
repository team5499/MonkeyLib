package org.team5499.monkeyLib.hardware

import org.team5499.monkeyLib.math.units.SIUnit

abstract class AbstractMonkeyMotor<T : SIUnit<T>> : MonkeyMotor<T> {

    override var useMotionProfileForPosition = false

    override fun follow(motor: MonkeyMotor<*>): Boolean {
        println("Cross brand following not implemented yet!")
        return false
    }
}
