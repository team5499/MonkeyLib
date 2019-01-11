package org.team5499.monkeyLib.math.pid

import edu.wpi.first.wpilibj.Timer

public class PIDF {

    public var kP: Double
    public var kI: Double
    public var kD: Double
    public var kF: Double
    public var setpoint: Double
    public var processVariable: Double

    // number of iterations the accumulator keeps track of.
    // 0 disables this (keeps track of all loops)
    public var integralZone: Int

    public var inverted: Boolean

    private var lastError: Double
    private var accumulator: Double
    private var integralZoneBuffer: MutableList<Double>
    private val timer: Timer

    val error: Double
        get() = (setpoint - processVariable)

    init {
        this.timer = Timer()
        this.timer.reset()
        this.accumulator = 0.0
        this.lastError = 0.0
        this.setpoint = 0.0
        this.processVariable = 0.0
        this.integralZoneBuffer = mutableListOf<Double>()
        this.integralZone = 0
    }

    public constructor(kP: Double, kI: Double, kD: Double, kF: Double, inverted: Boolean = false) {
        this.kP = kP
        this.kI = kI
        this.kD = kD
        this.kF = kF
        this.inverted = inverted
    }

    public constructor(): this(0.0, 0.0, 0.0, 0.0, false)

    public fun calculate(): Double {
        if (integralZone == 0) {
            accumulator += error * timer.get()
        } else {
            accumulator = 0.0
            integralZoneBuffer.add(error * timer.get())
            while (integralZoneBuffer.size > integralZone) {
                integralZoneBuffer.removeAt(0)
            }
            for (value in integralZoneBuffer) {
                accumulator += value
            }
        }

        val pt = kP * error
        val it = kI * accumulator
        val dt = kD * (error - lastError) / timer.get()
        val ft = kF * setpoint
        timer.stop()
        timer.reset()
        timer.start()
        lastError = error
        val total = pt + it + dt + ft
        return total
    }

    public fun reset() {
        timer.stop()
        timer.reset()
        accumulator = 0.0
        lastError = 0.0
        setpoint = 0.0
    }
}
