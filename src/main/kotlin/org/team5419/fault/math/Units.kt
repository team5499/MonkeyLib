package org.team5419.fault.math.units

/// UNIT CONVERSION ///

public infix fun Double.from(conversion : Double) : UnitConverter {
	return UnitConverter(this * conversion)
}

data class UnitConverter internal constructor(internal val value: Double) {
	public infix fun to(conversion : Double): Double {
		return value / conversion
	}
}

/// DISTANCS UNITS ///

public const val CENTIMETERS: Double = 1.0
public const val DECIMETERS: Double = 10.0
public const val METERS: Double = 100.0
public const val KILOMETERS: Double = 1000.0 * METERS

public const val INCHS: Double = 2.54
public const val FEET: Double = 12 * INCHS
public const val YARDS: Double = 3 * FEET
public const val MILES: Double = 5280 * FEET

// TIME UNITS //

public const val SECONDS: Double = 1.0
public const val MINUTES: Double = 60.0
public const val HOURS: Double = 60.0 * MINUTES