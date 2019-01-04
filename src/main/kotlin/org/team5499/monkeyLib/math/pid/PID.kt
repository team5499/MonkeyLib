package org.team5499.monkeyLib.math.pid

import edu.wpi.first.wpilibj.Timer

import org.team5499.monkeyLib.util.Utils

public class PID {

    var kP: Double
    var kI: Double
    var kD: Double
    var setpoint: Double
    var processVariable: Double

    var upperOutputCap: Double
    var lowerOutputCap: Double
    var useOutputCaps: Boolean

    var inverted: Boolean

    private var lastError: Double
    private var accumulator: Double
    private val timer: Timer

    val error: Double
        get() = (setpoint - processVariable)

    init {
        this.timer = Timer()
        this.timer.reset()
        this.accumulator = 0.0
        this.upperOutputCap = Double.MAX_VALUE
        this.lowerOutputCap = Double.MIN_VALUE
        this.useOutputCaps = false
        this.lastError = 0.0
        this.setpoint = 0.0
        this.processVariable = 0.0
    }

    public constructor(kP: Double, kI: Double, kD: Double, inverted: Boolean = false) {
        this.kP = kP
        this.kI = kI
        this.kD = kD
        this.inverted = inverted
    }

    public constructor(): this(0.0, 0.0, 0.0, false)

    public fun calculate(): Double {
        accumulator += error * timer.get()
        val pt = kP * error
        val it = kI * accumulator
        val dt = kD * (error - lastError) / timer.get()
        timer.stop()
        timer.reset()
        timer.start()
        lastError = error
        val total = pt + it + dt
        return if (!inverted) Utils.limit(total, lowerOutputCap, upperOutputCap)
            else -Utils.limit(total, lowerOutputCap, upperOutputCap)
    }

    public fun reset() {
        timer.stop()
        timer.reset()
        accumulator = 0.0
        lastError = 0.0
        setpoint = 0.0
    }
}
