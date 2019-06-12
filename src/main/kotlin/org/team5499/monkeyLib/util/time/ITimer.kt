package org.team5499.monkeyLib.util.time

import org.team5499.monkeyLib.math.units.Time

/**
* Timer abstraction layer to allow unit testing
*/
interface ITimer {

    /**
    * Function to get time since timer started
    * @return number of ellapsed seconds
    */
    public fun get(): Time

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
