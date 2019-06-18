package org.team5499.monkeyLib.math.localization

import edu.wpi.first.wpilibj.Timer
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.degree
import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.util.CSVWritable
import org.team5499.monkeyLib.util.Source
import kotlin.reflect.KProperty

abstract class PositionTracker(
    val heading: Source<Rotation2d>,
    private val buffer: TimeInterpolatableBuffer<Pose2d>
) : Source<Pose2d>, CSVWritable {

    var robotPosition = Pose2d()
        private set

    private var lastHeading = 0.degree
    private var headingOffset = 0.degree

    override fun invoke() = robotPosition

    @Synchronized fun reset(pose: Pose2d = Pose2d()) = resetInternal(pose)

    protected open fun resetInternal(pose: Pose2d) {
        robotPosition = pose
        val newHeading = heading()
        lastHeading = newHeading
        headingOffset = -newHeading + pose.rotation
        buffer.clear()
    }

    @Synchronized
    fun update() {
        val newHeading = heading()
        val headingDelta = newHeading - lastHeading
        val newRobotPosition = robotPosition + update(headingDelta)
        robotPosition = Pose2d(
                newRobotPosition.translation,
                newHeading
        )
        lastHeading = newHeading

        buffer[Timer.getFPGATimestamp()] = robotPosition
    }

    protected abstract fun update(deltaHeading: Rotation2d): Pose2d

    operator fun get(timestamp: Time) = get(timestamp.second)
    operator fun get(timestamp: Double) = buffer[timestamp] ?: Pose2d()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Pose2d = robotPosition
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Pose2d) = reset(value)

    override fun toCSV() = robotPosition.toCSV()
}
