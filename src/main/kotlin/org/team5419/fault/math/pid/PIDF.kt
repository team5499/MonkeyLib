package org.team5419.fault.math.pid

import edu.wpi.first.wpilibj.Timer

public class PIDF {
    /**
     * The PIDF proportional constant.
     */
    public var kP: Double

    /**
     * The PIDF integral constant.
     */
    public var kI: Double

    /**
     * The PIDF derivative constant.
     */
    public var kD: Double

    /**
     * The PIDF final constant.
     */
    public var kF: Double

    /**
     * The max absolute value of the output.
     */
    public var totalMax: Double

    /**
     * The set point the pid loop is targeting
     */
    public var setpoint: Double

    /**
     * The value of the system. Used to get error when calculate is called.
     */
    public var processVariable: Double

    /**
     * Number of iterations the accumulator keeps track of.
     * 0 disables this (keeps track of all loops)
     */
    public var integralZone: Int

    private var lastError: Double
    private var accumulator: Double
    private var integralZoneBuffer: MutableList<Double>
    private val timer: Timer

    /**
     * Calculates the error based off of the current processVariable and setpoint
     */
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

    public constructor(kP: Double, kI: Double, kD: Double, kF: Double, totalMax: Double = 0.0) {
        this.kP = kP
        this.kI = kI
        this.kD = kD
        this.kF = kF
        this.totalMax = totalMax
    }

    public constructor(): this(0.0, 0.0, 0.0, 0.0)

    private fun updateAccumulator() {
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

    /**
     * Calculates the current PIDF output based of the error.
     * It also updates the accumulator and sets the last error.
     *
     * Make sure to update the processVariable before calling it.
     *
     * @return the PIDF output
     */
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

            if (total < /** > */ -totalMax) {
                total = -totalMax
            }
        }

        return total
    }

    /**
     * Resets the pid loop timer, acumulator and setpoint.
     * It dosent change the constants.
     */
    public fun reset() {
        timer.stop()
        timer.reset()
        accumulator = 0.0
        lastError = 0.0
        setpoint = 0.0
    }
}
