package org.team5419.fault.util.time

import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.Second
/**
* Timer abstraction layer to allow unit testing
*/
interface ITimer {

    /**
    * Function to get time since timer started
    * @return number of ellapsed seconds
    */
    public fun get(): SIUnit<Second>

    /**
    * Starts the timer
    */
    public fun start(): Unit

    /**
    * stops the timer
    */
    public fun stop(): Unit

    /**
    * resets the timer to 0.
    */
    public fun reset(): Unit
}
