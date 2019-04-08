package org.team5499.monkeyLib.auto.actions

import org.team5499.monkeyLib.util.time.ITimer
import org.team5499.monkeyLib.util.time.WPITimer

public class NothingAction(timeout: Double, timer: ITimer = WPITimer()) : Action(timeout, timer)
