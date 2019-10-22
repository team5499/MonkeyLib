package org.team5419.fault.path

import org.team5419.fault.math.geometry.Pose2dWithCurvature

/**
* this class represents a path that can be mirrored over the centerline of the field
* @property left is path on the left side of the field
*/
public class MirroredPath(left: Path) {

    public val left: Path
    public val right: Path

    init {
        this.left = left
        val newPoints: MutableList<Pose2dWithCurvature> = left.points.toMutableList()
        for (i in 0..newPoints.size - 1) {
            newPoints.set(i, newPoints.get(i).mirror())
        }
        this.right = Path(newPoints, left.velocities, left.reversed)
    }
}
