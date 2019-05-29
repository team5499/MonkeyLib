package org.team5499.monkeyLib.util.time

import org.team5499.monkeyLib.math.units.Time
import org.team5499.monkeyLib.math.units.second

class DeltaTime internal constructor(startTime: Double) {
    constructor(startTime: Time = (-1).second) : this(startTime.value)

    @Suppress("VariableNaming")
    internal var _currentTime = startTime
        private set
    @Suppress("VariableNaming")
    internal var _deltaTime = 0.0
        private set

    val deltaTime get() = _deltaTime.second
    val currentTime get() = _currentTime.second

    fun updateTime(newTime: Time) = updateTime(newTime.value).second

    fun updateTime(newTime: Double): Double {
        _deltaTime = if (_currentTime < 0.0) {
            0.0
        } else {
            newTime - _currentTime
        }
        _currentTime = newTime
        return _deltaTime
    }

    fun reset() {
        _currentTime = -1.0
    }
}
