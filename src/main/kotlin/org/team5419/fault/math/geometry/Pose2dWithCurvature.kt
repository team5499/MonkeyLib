package org.team5419.fault.math.geometry

import org.team5419.fault.math.interpolate

class Pose2dWithCurvature(
    translation: Vector2,
    rotation: Rotation2d,
    curvature: Double,
    dCurvature: Double = 0.0
) : State<Pose2dWithCurvature> {

    val curvature: Double
    val dCurvature: Double
    val pose: Pose2d
    val translation: Vector2
        get() = pose.translation
    val rotation: Rotation2d
        get() = pose.rotation

    init {
        this.curvature = curvature
        this.dCurvature = dCurvature
        this.pose = Pose2d(translation, rotation)
    }

    operator fun plus(other: Pose2d) = Pose2dWithCurvature(this.pose + other, this.curvature, this.dCurvature)

    constructor(pose: Pose2d, curvature: Double, dCurvature: Double = 0.0):
        this(pose.translation, pose.rotation, curvature, dCurvature)
    constructor(): this(Vector2(), 0.degree, 0.0, 0.0)
    constructor(other: Pose2dWithCurvature): this(other.translation, other.rotation, other.curvature, other.dCurvature)

    public fun mirror(): Pose2dWithCurvature {
        return Pose2dWithCurvature(pose.mirror, -curvature, -dCurvature)
    }

    override fun interpolate(other: Pose2dWithCurvature, x: Double): Pose2dWithCurvature {
        return Pose2dWithCurvature(pose.interpolate(other.pose, x),
            interpolate(curvature, other.curvature, x),
            interpolate(dCurvature, other.dCurvature, x)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Pose2dWithCurvature) return false
        return pose.equals(other.pose) && curvature == other.curvature && dCurvature == other.dCurvature
    }

    override fun toString(): String {
        return pose.toString() + ", Curvature: $curvature, dCurvature: $dCurvature"
    }

    override fun toCSV() = "${pose.toCSV()},$curvature,$dCurvature"

    override fun distance(other: Pose2dWithCurvature) = pose.distance(other.pose)

    override fun hashCode() = super.hashCode()
}
