package org.team5419.fault.math

import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.util.CSVWritable

class Position : CSVWritable {

    private var x = 0.0
    private var y = 0.0
    private var lastLeft = 0.0
    private var lastRight = 0.0
    private var lastAngle = 0.0

    var positionVector: Vector2
        get() = Vector2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    fun update(leftDistance: Double, rightDistance: Double, angle: Double) {
        val newAngle = Math.toRadians(angle)
        var angleDelta = newAngle - lastAngle
        if (angleDelta == 0.0) angleDelta = Epsilon.EPSILON
        val leftDelta = leftDistance - lastLeft
        val rightDelta = rightDistance - lastRight
        val distance = (leftDelta + rightDelta) / 2.0
        val radiusOfCurvature = distance / angleDelta
        val dy = radiusOfCurvature * Math.sin(angleDelta)
        val dx = radiusOfCurvature * (Math.cos(angleDelta) - 1)
        y -= dx * Math.cos(lastAngle) - dy * Math.sin(lastAngle)
        x += dx * Math.sin(lastAngle) + dy * Math.cos(lastAngle)
        lastLeft = leftDistance
        lastRight = rightDistance
        lastAngle = newAngle
    }

    fun reset() {
        x = 0.0
        y = 0.0
        lastLeft = 0.0
        lastRight = 0.0
        lastAngle = 0.0
    }

    override fun toString(): String {
        return "Robot Position -- X: $x, Y: $y"
    }

    override fun toCSV(): String = "$x,$y"
}
