package org.team5419.fault.math.pid

import edu.wpi.first.wpilibj.Timer

public class PIDF {

    public var kP: Double
    public var kI: Double
    public var kD: Double
    public var kF: Double
    public var totalMax: Double
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
        timer = Timer()
        timer.reset()
        accumulator = 0.0
        lastError = 0.0
        setpoint = 0.0
        processVariable = 0.0
        totalMax = 0.0
        integralZoneBuffer = mutableListOf<Double>()
        integralZone = 0
    }

    public constructor(
        kP: Double,
        kI: Double,
        kD: Double,
        kF: Double,
        inverted: Boolean = false,
        totalMax: Double = 0.0
    ) {
        this.kP = kP
        this.kI = kI
        this.kD = kD
        this.kF = kF
        this.totalMax = totalMax
        this.inverted = inverted
    }

    public constructor(): this(0.0, 0.0, 0.0, 0.0, false)

    public fun updateAccumulator() {
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
    }

    public fun calculate(): Double {
        var total = 0.0

        if (kP != 0.0) {
            total += kP * error
        }

        if (kI != 0.0) {
            updateAccumulator()

            total += kI * accumulator
        }

        if (kD != 0.0) {
            total += kD * (error - lastError) / timer.get()

            timer.stop()
            timer.reset()
            timer.start()
        }

        if (kF != 0.0) {
            total += kF * setpoint
        }

        lastError = error

        if (totalMax != 0.0) {
            if (total > totalMax) {
                total = totalMax
            }

            if (total < -totalMax) {
                total = -totalMax
            }
        }

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
