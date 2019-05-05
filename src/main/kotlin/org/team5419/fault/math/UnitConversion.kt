/// DISTANCS UNITS ///

val CENTIMETERS: Double = 1.0
val DECIMETERS: Double = 10.0
val METERS: Double = 100.0
val KILOMETERS: Double = 1000.0 * METERS

val INCHS: Double = 12.54
val FEET: Double = 12 * INCHS
val YARDS: Double = 3 * FEET
val MILES: Double = 5280 * FEET

// TIME UNITS //

val SECOND: Double = 1.0

/// UNIT CONVERSION ////

data class UnitConverter internal constructor(internal val value: Double) {
	fun to(conversion : Double): Double {
		return value /  conversion
	}
}

fun Double.from(conversion : Double) : UnitConverter {
	return UnitConverter(this * conversion)
}