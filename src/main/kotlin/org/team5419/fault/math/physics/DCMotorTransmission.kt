package org.team5419.fault.math.physics

/**
 * Model of a DC motor rotating a shaft.  All parameters refer to the output (e.g. should already consider gearing
 * and efficiency losses).  The motor is assumed to be symmetric forward/reverse.
 *
 * @property speedPerVolt kV, or rad/s per V (no load)
 * @property torquePerVolt N•m per Amp (stall)
 * @property frictionVoltage the voltage needed to overcome static
 * @property stallAmperage the stall amperage at 12 volts
 */
class DCMotorTransmission(
    val speedPerVolt: Double,
    val torquePerVolt: Double,
    val frictionVoltage: Double,
    val stallAmperage: Double
) {
    // TODO add electrical constants?  (e.g. current)

    val torquePerAmp: Double
    val internalResistance: Double

    init {
        torquePerAmp = torquePerVolt * 12.0 / stallAmperage
        internalResistance = 12.0 / stallAmperage
    }
    /**
     * Returns the idle speed of the motor at this voltage
     *
     * @param voltage The voltage across the motor
     * @return The theoretical speed in rad/s
     */
    @Suppress("ReturnCount")
    fun freeSpeedAtVoltage(voltage: Double): Double {
        if (voltage > 0.0) {
            return Math.max(0.0, voltage - frictionVoltage) * speedPerVolt
        } else if (0.0 > voltage) {
            return Math.min(0.0, voltage + frictionVoltage) * speedPerVolt
        } else {
            return 0.0
        }
    }

    /**
     * Get the theoretical torque applied by the motor at a given speed and voltage
     *
     * @param outputSpeed The speed of the motor in rad/s
     * @param voltage The voltage across the motor
     * @return The theoretical torque in N•m
     */
    fun getTorqueForVoltage(outputSpeed: Double, voltage: Double): Double {
        var effectiveVoltage = voltage
        if (outputSpeed > 0.0) {
            // Forward motion, rolling friction.
            effectiveVoltage -= frictionVoltage
        } else if (0.0 > outputSpeed) {
            // Reverse motion, rolling friction.
            effectiveVoltage += frictionVoltage
        } else if (voltage > 0.0) {
            // System is static, forward torque.
            effectiveVoltage = Math.max(0.0, voltage - frictionVoltage)
        } else if (0.0 > voltage) {
            // System is static, reverse torque.
            effectiveVoltage = Math.min(0.0, voltage + frictionVoltage)
        } else {
            // System is idle.
            return 0.0
        }
        return torquePerVolt * (-outputSpeed / speedPerVolt + effectiveVoltage)
    }

    /**
     * Get the required voltage for the requested torque at a speed.
     *
     * @param outputSpeed The output speed of the motor in rad/s
     * @param torque The output torque of the motor in N•m
     * @return The theoretical voltage for the requested torque and speed
     */
    fun getVoltageForTorque(outputSpeed: Double, torque: Double): Double {
        var modifiedFrictionVoltage: Double = 0.0
        if (outputSpeed > 0.0) {
            // Forward motion, rolling friction.
            modifiedFrictionVoltage = frictionVoltage
        } else if (0.0 > outputSpeed) {
            // Reverse motion, rolling friction.
            modifiedFrictionVoltage = -frictionVoltage
        } else if (torque > 0.0) {
            // System is static, forward torque.
            modifiedFrictionVoltage = frictionVoltage
        } else if (0.0 > torque) {
            // System is static, reverse torque.
            modifiedFrictionVoltage = -frictionVoltage
        } else {
            // System is idle.
            return 0.0
        }
        return torque / torquePerVolt + outputSpeed / speedPerVolt + modifiedFrictionVoltage
    }

    /**
     * Get the theoretical speed for voltage and amperage.
     *
     * @param voltage
     * @param amperage
     * @return speed rad/s
     */
    fun getSpeedForVoltageAndAmperage(voltage: Double, amperage: Double): Double {
        var modifiedVoltage = voltage - frictionVoltage
        return (modifiedVoltage - amperage * internalResistance) * speedPerVolt
    }
}
