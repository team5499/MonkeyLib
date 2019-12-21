package org.team5419.fault.util

import java.util.LinkedList

class MovingAverageFilter(numberOfValues: Int) {
    private val values: LinkedList<Double>
    private val numberOfValues: Int
    public val average: Double
        get() = values.toDoubleArray().sum() / values.size

    private var staticAddValue = { value ->
        values.add(value)
        values.remove()
    }

    private var addValue = { value ->
        values.add(value)
        if (values.size == numberOfValues) {
            addValue = staticAddValue
        }
    }

    init {
        this.values = LinkedList()
        this.numberOfValues = numberOfValues
    }

    operator fun plusAssing(value: Double) = addValue(value)
}
