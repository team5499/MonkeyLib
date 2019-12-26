package org.team5419.fault.util

import java.util.LinkedList

class MovingAverageFilter(numberOfValues: Int) {
    private val values: LinkedList<Double>
    private val numberOfValues: Int
    public val average: Double
        get() = values.toDoubleArray().sum() / values.size

    init {
        this.values = LinkedList()
        this.numberOfValues = numberOfValues
    }

    private var staticAddValue: (Double) -> Unit = { value ->
        this.values.add(value)
        this.values.remove()
    }

    private var initAddValue: (Double) -> Unit = { value ->
        this.values.add(value)
        if (this.values.size == numberOfValues) {
            addValue = staticAddValue
        }
    }

    private var addValue: (Double) -> Unit = initAddValue

    operator fun plusAssign(value: Double) = addValue(value)
}
