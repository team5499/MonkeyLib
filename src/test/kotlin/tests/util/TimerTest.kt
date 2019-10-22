package tests.utils

import org.junit.Test
import org.junit.Assert.assertEquals

import org.team5419.fault.util.time.ITimer
import org.team5419.fault.util.time.SystemTimer

public class TimerTest {

    private val kEpsilon = 1E6

    @Test
    fun timerSystemTest() {
        val timer: ITimer = SystemTimer()
        timer.start()
        assertEquals(timer.get(), 0.0, kEpsilon)
        Thread.sleep(2000L)
        assertEquals(timer.get(), 2.0, kEpsilon)
        timer.stop()
        Thread.sleep(1000L)
        timer.start()
        Thread.sleep(1000L)
        assertEquals(timer.get(), 3.0, kEpsilon)
        timer.stop()
        timer.reset()
        timer.start()
        assertEquals(timer.get(), 0.0, kEpsilon)
    }
}
