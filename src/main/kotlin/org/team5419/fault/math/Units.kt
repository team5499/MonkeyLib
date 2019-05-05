package org.team5419.fault.math

public object Units {
	/// DISTANCS UNITS ///

	public const val CENTIMETERS: Double = 1.0
	public const val DECIMETERS: Double = 10.0
	public const val METERS: Double = 100.0
	public const val KILOMETERS: Double = 1000.0 * METERS

	public const val INCHS: Double = 12.54
	public const val FEET: Double = 12 * INCHS
	public const val YARDS: Double = 3 * FEET
	public const val MILES: Double = 5280 * FEET

	// TIME UNITS //

	public const val SECONDS: Double = 1.0
}

/// UNIT CONVERSION ////

data class UnitConverter internal constructor(internal val value: Double) {
	fun to(conversion : Double): Double {
		return value /  conversion
	}
}

fun Double.from(conversion : Double) : UnitConverter {
	return UnitConverter(this * conversion)
}