package org.team5419.fault.auto

import org.team5419.fault.util.time.ITimer
import org.team5419.fault.util.time.WPITimer

public class NothingAction(timeout: Double, timer: ITimer = WPITimer()) : Action(timeout, timer)
