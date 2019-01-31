package org.team5499.monkeyLib.util

public class CircularDoubleBuffer(maxSize: Int) : CircularBuffer<Double>(maxSize) {

    public val sum: Double
        get() {
            var total = 0.0
            for (num in super.elements) {
                total += num
            }
            return total
        }

    public val average: Double
        get() {
            return sum / super.elements.size.toDouble()
        }
}
