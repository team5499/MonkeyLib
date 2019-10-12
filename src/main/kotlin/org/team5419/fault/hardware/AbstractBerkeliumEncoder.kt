package org.team5419.fault.hardware

import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.native.NativeUnitModel

abstract class AbstractBerkeliumEncoder<T : SIUnit<T>>(
    val model: NativeUnitModel<T>
) : BerkeliumEncoder<T> {
    override val position: Double get() = model.fromNativeUnitPosition(rawPosition)
    override val velocity: Double get() = model.fromNativeUnitVelocity(rawVelocity)
}
