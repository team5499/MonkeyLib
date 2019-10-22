package org.team5419.fault.path

import org.team5419.fault.util.CSVWritable

import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Pose2dWithCurvature

class Path(
    points: MutableList<Pose2dWithCurvature>,
    velocities: MutableList<Double>,
    reversed: Boolean = false
) : CSVWritable {

    internal val points: MutableList<Pose2dWithCurvature>
    internal val velocities: MutableList<Double>

    val pathLength: Int
        get() = points.size

    val reversed: Boolean
        get() = field

    val startPose: Pose2dWithCurvature
        get() = Pose2dWithCurvature(points.get(0))

    val endPose: Pose2dWithCurvature
        get() = Pose2dWithCurvature(points.get(pathLength - 1))

    val startVelocity: Double
        get() = velocities.get(0)

    val endVelocity: Double
        get() = velocities.get(velocities.size - 1)

    init {
        if (points.size != velocities.size) {
            println("coords length: ${points.size}, velo length: ${velocities.size}")
            throw IllegalArgumentException("Velocity and Coordinate arrays need to be same length.")
        }
        if (points.size < 2) throw IllegalArgumentException("Needs to be more than 2 points for a path")
        this.reversed = reversed
        this.points = points.toMutableList()
        this.velocities = velocities.toMutableList()
    }

    constructor(other: Path): this(other.points.toMutableList(), other.velocities, other.reversed)

    fun getPose(index: Int): Pose2dWithCurvature {
        if (index >= points.size || index < 0) {
            throw IndexOutOfBoundsException("Point $index not in path")
        }
        return points.get(index)
    }

    fun getVelocity(index: Int): Double {
        if (index >= points.size || index < 0) {
            throw IndexOutOfBoundsException("Point $index not in velocities")
        }
        return velocities.get(index)
    }

    fun findClosestPointIndex(point: Pose2d, lastIndex: Int): Int {
        val lastPose: Vector2 = points.get(lastIndex).translation
        var minDistance: Double = Vector2.distanceBetween(point.translation, lastPose)
        var index: Int = lastIndex
        for (i in lastIndex..points.size - 1) {
            val tempDistance: Double = Vector2.distanceBetween(point.translation, points.get(i).translation)
            if (tempDistance < minDistance) {
                index = i
                minDistance = tempDistance
            }
        }
        return index
    }

    override fun toString(): String {
        val buffer: StringBuilder = StringBuilder()
        for (i in 0..points.size - 1) {
            buffer.append(points.get(i).toString())
            buffer.append(System.lineSeparator())
        }
        return buffer.toString()
    }

    override fun toCSV(): String {
        val buffer: StringBuilder = StringBuilder()
        for (i in 0..points.size - 1) {
            buffer.append(points.get(i).pose.toCSV())
            buffer.append(", ")
            buffer.append(velocities.get(i))
            buffer.append(System.lineSeparator())
        }
        return buffer.toString()
    }
}
