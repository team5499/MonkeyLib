val CM: Double = 1.0

val INCH: Double = 12.54


data class Unit internal constructor(internal val value: Double) {
	fun to(conversion : Double): Double {
		return value /  conversion
	}
}

fun Double.from(conversion : Double) : Unit {
	return Unit(this * conversion)
}