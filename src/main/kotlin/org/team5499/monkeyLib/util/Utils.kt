package org.team5499.monkeyLib.util

@SuppressWarnings("MagicNumber")
object Utils {

    fun limit(value: Double, limit: Double): Double {
        return limit(value, -limit, limit)
    }

    fun limit(value: Double, min: Double, max: Double): Double {
        return Math.min(max, Math.max(min, value))
    }

    fun interpolate(a: Double, b: Double, x: Double): Double {
        val newX = limit(x, 0.0, 1.0)
        return a + (b - a) * newX
    }

    fun inchesPerSecondToEncoderTicksPer100Ms(
        encoderTicksPerRotation: Int,
        wheelCircumference: Double,
        ips: Double
    ): Double {
        return inchesToEncoderTicks(encoderTicksPerRotation, wheelCircumference, ips) / 10.0
    }

    fun encoderTicksPer100MsToInchesPerSecond(
        encoderTicksPerRotation: Int,
        wheelCircumference: Double,
        eps: Int
    ): Double {
        return encoderTicksToInches(encoderTicksPerRotation, wheelCircumference, eps) * 10.0
    }

    fun inchesToEncoderTicks(encoderTicksPerRotation: Int, wheelCircumference: Double, inches: Double): Int {
        return ((encoderTicksPerRotation / wheelCircumference) * inches).toInt()
    }

    fun encoderTicksToInches(encoderTicksPerRotation: Int, wheelCircumference: Double, ticks: Int): Double {
        return (wheelCircumference / encoderTicksPerRotation) * ticks
    }

    fun talonAngleToDegrees(gyroTicksPerRotation: Int, ticks: Int): Double {
        return (360.0 / gyroTicksPerRotation) * ticks
    }

    fun degreesToTalonAngle(gyroTicksPerRotation: Int, degrees: Double): Int {
        return ((gyroTicksPerRotation / 360.0) * degrees).toInt()
    }
}
