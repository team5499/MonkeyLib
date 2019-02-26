package org.team5499.monkeyLib.util.time

import edu.wpi.first.wpilibj.RobotController
import edu.wpi.first.wpilibj.Timer

/**
* Executes the given block and returns elapsed time in seconds.
*/
inline fun measureTimeFPGA(body: () -> Unit): Double {
    val start = Timer.getFPGATimestamp()
    body()
    return Timer.getFPGATimestamp() - start
}

/**
 * Executes the given block and returns elapsed time in nanoseconds.
 */
inline fun measureTimeFPGAMicros(body: () -> Unit): Long {
    val start = RobotController.getFPGATime()
    body()
    return RobotController.getFPGATime() - start
}
