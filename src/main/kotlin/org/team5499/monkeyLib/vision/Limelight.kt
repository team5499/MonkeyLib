package org.team5499.monkeyLib.vision

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.networktables.NetworkTable

import kotlin.math.tan

/*
* make this more efficient in the future. Loopers pls
*/
public class Limelight(networkTableName: String = "limelight") {

    public enum class LEDMode(val value: Int) { PIPELINE(0), OFF(1), ON(2), BLINK(3) }

    public enum class CameraMode(val value: Int) { VISION(0), DRIVER(1) }

    public enum class StreamMode(val value: Int) { STANDARD(0), PIP_MAIN(1), PIP_SECOND(2) }

    public data class Output3D(
        val x: Double = 0.0,
        val y: Double = 0.0,
        val z: Double = 0.0,
        val pitch: Double = 0.0,
        val yaw: Double = 0.0,
        val roll: Double = 0.0
    )

    public val networkTableName: String

    private val mTable: NetworkTable

    // setable vars

    var cameraMode: CameraMode = CameraMode.VISION
        set(value) {
            mTable.getEntry("camMode").setNumber(value.value)
            field = value
        }

    var ledMode: LEDMode = LEDMode.OFF
        set(value) {
            mTable.getEntry("ledMode").setNumber(value.value)
            field = value
        }

    var streamMode: StreamMode = StreamMode.STANDARD
        set(value) {
            mTable.getEntry("stream").setNumber(value.value)
        }

    var pipeline: Int = 0
        get() = mTable.getEntry("pipeline").getDouble(0.0).toInt()
        set(value) {
            if (field == value) return
            mTable.getEntry("pipeline").setNumber(value)
            field = value
        }

    // return vars
    val targetXOffset: Double
        get() {
            return mTable.getEntry("tx").getDouble(0.0)
        }
    val targetYOffset: Double
        get() {
            return mTable.getEntry("ty").getDouble(0.0)
        }
    val targetSkew: Double
        get() {
            return mTable.getEntry("ts").getDouble(0.0)
        }
    val targetArea: Double
        get() {
            return mTable.getEntry("ta").getDouble(0.0)
        }
    val hasValidTarget: Boolean
        get() {
            return if (mTable.getEntry("tv").getDouble(0.0) == 1.0) true else false
        }
    val pipelineLatency: Double // ms
        get() {
            return mTable.getEntry("tl").getDouble(-1.0)
        }

    val output3d: Output3D
        get() {
            val output = mTable.getEntry("camtran").getDoubleArray(Array<Double>(6, { 0.0 }))
            return Output3D(
                output[0], output[1], output[2],
                output[3], output[4], output[5]
            )
        }

    // http://docs.limelightvision.io/en/latest/cs_estimating_distance.html
    fun distanceToTarget(limelightHeight: Double, targetHeight: Double, limelightAngle: Double): Double {
        val dh = targetHeight - limelightHeight
        val angle = limelightAngle + targetYOffset
        return dh / tan(angle)
    }

    init {
        this.networkTableName = networkTableName
        mTable = NetworkTableInstance.getDefault().getTable(networkTableName)
    }
}
