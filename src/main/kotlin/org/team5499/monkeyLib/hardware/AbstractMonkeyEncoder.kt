package org.team5499.monkeyLib.hardware

import org.team5499.monkeyLib.math.units.SIUnit
import org.team5499.monkeyLib.math.units.native.NativeUnitModel

abstract class AbstractMonkeyEncoder<T : SIUnit<T>>(
    val model: NativeUnitModel<T>
) : MonkeyEncoder<T> {
    override val position: Double get() = model.fromNativeUnitPosition(rawPosition)
    override val velocity: Double get() = model.fromNativeUnitVelocity(rawVelocity)
}
