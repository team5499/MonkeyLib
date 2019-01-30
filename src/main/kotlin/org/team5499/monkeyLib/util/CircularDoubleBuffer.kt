package org.team5499.monkeyLib.util

public class CircularDoubleBuffer(maxSize: Int) : CircularBuffer<Double>(maxSize) {

    public val sum by lazy {
        var total = 0.0
        for (num in super.elements) {
            total += num
        }
        total
    }

    public val average by lazy {
        sum / super.elements.size.toDouble()
    }
}
