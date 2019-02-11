package org.team5499.monkeyLib.util

@SuppressWarnings("MagicNumber")
object Utils {

    public fun limit(value: Double, limit: Double): Double {
        return limit(value, -limit, limit)
    }

    public fun limit(value: Double, min: Double, max: Double): Double {
        return Math.min(max, Math.max(min, value))
    }

    public fun interpolate(a: Double, b: Double, x: Double): Double {
        val newX = limit(x, 0.0, 1.0)
        return a + (b - a) * newX
    }

    /**
    * @param encoderTicksPerRotation encoder ticks per 360 degrees of rotation
    * @param circumference circumference of the object being rotated
    * @param inchesPerSecond inches per second to be converted to ticks per 100 ms
    * @param reduction reduction from the encoder shaft to the output shaft
    */
    public fun inchesPerSecondToEncoderTicksPer100Ms(
        encoderTicksPerRotation: Int,
        circumference: Double,
        inchesPerSecond: Double,
        reduction: Double = 1.0
    ): Double {
        return inchesToEncoderTicks(encoderTicksPerRotation, circumference, inchesPerSecond, reduction) / 10.0
    }

    /**
    * @param encoderTicksPerRotation encoder ticks per 360 degrees of rotation
    * @param circumference circumference of the object being rotated
    * @param encoderTicksPer100ms ticks per 100ms to be converted to inches per second
    * @param reduction reduction from the encoder shaft to the output shaft
    */
    public fun encoderTicksPer100MsToInchesPerSecond(
        encoderTicksPerRotation: Int,
        circumference: Double,
        encoderTicksPer100ms: Int,
        reduction: Double = 1.0
    ): Double {
        return encoderTicksToInches(encoderTicksPerRotation, circumference, encoderTicksPer100ms, reduction) * 10.0
    }

    /**
    * @param encoderTicksPerRotation encoder ticks per 360 degrees of rotation
    * @param circumference circumference of the object being rotated
    * @param inches inches of travel to be converted to ticks of travel
    * @param reduction reduction from the encoder shaft to the output shaft
    */
    public fun inchesToEncoderTicks(
        encoderTicksPerRotation: Int,
        circumference: Double,
        inches: Double,
        reduction: Double = 1.0
    ): Int {
        return (((encoderTicksPerRotation / circumference) * inches) / reduction).toInt()
    }

    /**
    * @param encoderTicksPerRotation encoder ticks per 360 degrees of rotation
    * @param circumference circumference of the object being rotated
    * @param ticks ticks of travel to be converted to inches of travel
    * @param reduction reduction from the encoder shaft to the output shaft
    */
    public fun encoderTicksToInches(
        encoderTicksPerRotation: Int,
        circumference: Double,
        ticks: Int,
        reduction: Double = 1.0
    ): Double {
        return (circumference / encoderTicksPerRotation) * ticks * reduction
    }

    public fun talonAngleToDegrees(gyroTicksPerRotation: Int, ticks: Int): Double {
        return (360.0 / gyroTicksPerRotation) * ticks
    }

    public fun degreesToTalonAngle(gyroTicksPerRotation: Int, degrees: Double): Int {
        return ((gyroTicksPerRotation / 360.0) * degrees).toInt()
    }
}
