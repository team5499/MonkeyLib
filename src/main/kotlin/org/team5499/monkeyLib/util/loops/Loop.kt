package org.team5499.monkeyLib.util.loops

public interface Loop {

    public fun onStart(timestamp: Double)

    public fun onLoop(timestamp: Double)

    public fun onStop(timestamp: Double)
}
