package tests.auto

import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.api.mockito.PowerMockito
import org.mockito.Mockito

import org.team5499.monkeyLib.auto.NothingAction

import edu.wpi.first.wpilibj.Timer

@RunWith(PowerMockRunner::class)
@PrepareForTest(Timer::class)
public class AutoTests {

    private val fakeTimer: Timer = PowerMockito.mock(Timer::class.java)

    @Test
    public fun testNothingCommand() {
        PowerMockito.whenNew(Timer::class.java).withAnyArguments().thenReturn(fakeTimer)
        val action = NothingAction(10.0)
    }

}