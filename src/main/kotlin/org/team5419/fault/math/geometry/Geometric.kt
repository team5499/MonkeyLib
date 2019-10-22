package org.team5419.fault.math.geometry

import org.team5419.fault.util.Interpolable
import org.team5419.fault.util.CSVWritable

interface Geometric<T> : Interpolable<T>, CSVWritable {

    public override fun toString(): String

    public override fun equals(other: Any?): Boolean

    public override fun toCSV(): String

    public override fun interpolate(other: T, x: Double): T

    public override fun hashCode(): Int
}
