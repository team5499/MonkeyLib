package org.team5499.monkeyLib.path

import org.team5499.monkeyLib.math.geometry.Pose2dWithCurvature

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
