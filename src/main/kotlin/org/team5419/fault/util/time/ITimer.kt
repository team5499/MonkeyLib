package org.team5419.fault.util.time

/**
* Timer abstraction layer to allow unit testing
*/
public interface ITimer {

    /**
    * Function to get time since timer started
    * @return number of ellapsed seconds
    */
    public fun get(): Double

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
