package org.team5419.fault.util.loops

public interface Loop {

    public fun onStart(timestamp: Double)

    public fun onLoop(timestamp: Double)

    public fun onStop(timestamp: Double)
}
