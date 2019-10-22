package org.team5419.fault.math.units

// Unit Conversion Code

public infix fun Double.from(conversion: Double): UnitConverter {
    return UnitConverter(this * conversion)
}

data class UnitConverter internal constructor(internal val value: Double) {
    public infix fun to(conversion: Double): Double {
        return value / conversion
    }
}

// Distance Units

public const val CENTIMETERS: Double = 0.01
public const val DECIMETERS: Double = 0.1
public const val METERS: Double = 1.0
public const val KILOMETERS: Double = 1000.0

public const val INCHS: Double = 0.0254
public const val FEET: Double = 12 * INCHS
public const val YARDS: Double = 3 * FEET
public const val MILES: Double = 5280 * FEET

// Time Units

public const val MILLISECONDS: Double = 0.001
public const val SECONDS: Double = 1.0
public const val MINUTES: Double = 60.0
public const val HOURS: Double = 60.0 * MINUTES

// Current Units

public const val MICROAMPERS: Double = 1 / 1e-6
public const val MILLIAMPS: Double = 0.001
public const val AMPS: Double = 1.0

// Electrical Potental Units

public const val MICROVOLTS: Double = 1 / 1e-6
public const val MILLIVOLTS: Double = 0.001
public const val VOLTS: Double = 1.0
