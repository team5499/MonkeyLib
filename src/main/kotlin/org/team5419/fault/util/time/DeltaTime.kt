package org.team5419.fault.util.time

import org.team5419.fault.math.units.*

class DeltaTime internal constructor(startTime: SIUnit<Second> = SIUnit(0.0)) {

    @Suppress("VariableNaming")
    internal var _currentTime = startTime
        private set
    @Suppress("VariableNaming")
    internal var _deltaTime = SIUnitBuilder(0.0).seconds
        private set

    val deltaTime get() = _deltaTime
    val currentTime get() = _currentTime

    fun updateTime(newTime: SIUnit<Second>): SIUnit<Second> {
        _deltaTime = if (_currentTime < 0.0) {
            SIUnit<Second>(0.0)
        } else {
            newTime - _currentTime
        }
        _currentTime = newTime
        return _deltaTime
    }

    fun reset() {
        _currentTime = SIUnit<Second>(-1.0)
    }
}
