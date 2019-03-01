package org.team5499.monkeyLib.hardware

public object LazyTalonSRXFactory {

    public class TalonConfig

    public fun createTalon(port: Int, config: TalonConfig): LazyTalonSRX {
        return LazyTalonSRX(port)
    }
}
