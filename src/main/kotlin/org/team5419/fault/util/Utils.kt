package org.team5419.fault.util

@SuppressWarnings("MagicNumber")
object Utils {

    /**
    * method that limits input to a certain range.
    * @param value value to be limited
    * @param limit upper and lower bound of the value
    * @return bounded value
    */
    public fun limit(value: Double, limit: Double): Double {
        return limit(value, -limit, limit)
    }

    /**
    * method that limits input to a certain range.
    * @param value value to be limited
    * @param min lower bound of the value
    * @param max upper bound of the value
    * @return bounded value
    */
    public fun limit(value: Double, min: Double, max: Double): Double {
        return Math.min(max, Math.max(min, value))
    }

    /**
    * method to interpolate between 2 doubles
    * @param a first value
    * @param b second value
    * @param x value between the 2 values
    * @return interpolated value
    */
    public fun interpolate(a: Double, b: Double, x: Double): Double {
        val newX = limit(x, 0.0, 1.0)
        return a + (b - a) * newX
    }

    /**
    * @param encoderTicksPerRotation encoder ticks per 360 degrees of rotation of the encoder shaft
    * @param circumference circumference of the object being rotated
    * @param inchesPerSecond velocity to be converted
    * @param reduction reduction from the encoder shaft to the output shaft
    * @return velocity in ticks per 100ms
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
    * @param encoderTicksPerRotation encoder ticks per 360 degrees of rotation of the encoder shaft
    * @param circumference circumference of the object being rotated
    * @param encoderTicksPer100ms velocity to be converted
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
    * @param encoderTicksPerRotation encoder ticks per 360 degrees of rotation of the encoder shaft
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
        return (encoderTicksPerRotation * inches * reduction / circumference).toInt()
    }

    /**
    * @param encoderTicksPerRotation encoder ticks per 360 degrees of rotation of the encoder shaft
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
        return (circumference * ticks) / (encoderTicksPerRotation * reduction)
    }

    public fun talonAngleToDegrees(gyroTicksPerRotation: Int, ticks: Int): Double {
        return (360.0 / gyroTicksPerRotation) * ticks
    }

    public fun degreesToTalonAngle(gyroTicksPerRotation: Int, degrees: Double): Int {
        return ((gyroTicksPerRotation / 360.0) * degrees).toInt()
    }
}
