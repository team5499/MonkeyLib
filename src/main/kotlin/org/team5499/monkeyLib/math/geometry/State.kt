package org.team5499.monkeyLib.math.geometry

import org.team5499.monkeyLib.util.Interpolable
import org.team5499.monkeyLib.util.CSVWritable

interface State<T> : Interpolable<T>, CSVWritable {

    fun distance(other: T): Double

    public override fun toString(): String

    public override fun toCSV(): String

    public override fun equals(other: Any?): Boolean

    public override fun interpolate(other: T, x: Double): T

    public override fun hashCode(): Int
}
