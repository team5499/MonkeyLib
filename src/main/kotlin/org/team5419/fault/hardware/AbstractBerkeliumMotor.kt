package org.team5419.fault.hardware

import org.team5419.fault.math.units.SIUnit

abstract class AbstractBerkeliumMotor<T : SIUnit<T>> : BerkeliumMotor<T> {

    override var useMotionProfileForPosition = false

    override fun follow(motor: BerkeliumMotor<*>): Boolean {
        println("Cross brand following not implemented yet!")
        return false
    }
}
