package org.team5499.monkeyLib

// TODO(Finish this somehow)
// @Suppress("TooManyFunctions")
// abstract class MonkeyRobot(period: Double) {
//
//    constructor(period: Time): this(period.value)
//
//    protected val wrappedValue = WpiTimedRobot(period)
//
//    protected inner class WpiTimedRobot(period: Double = 0.05) : TimedRobot(period) {
//
//        private val kLanguageKotlin = 6
//
//        init {
//            HAL.report(FRCNetComm.tResourceType.kResourceType_Language, kLanguageKotlin)
//        }
//
//        override fun robotInit() {
//            this@MonkeyRobot.robotInit()
//            SubsystemManager.init()
//        }
//
//        override fun autonomousInit() {
//            this@MonkeyRobot.autonomousInit()
//            SubsystemManager.autoReset()
//        }
//
//        override fun teleopInit() {
//            this@MonkeyRobot.teleopInit()
//            SubsystemManager.teleopReset()
//        }
//
//        override fun disabledInit() {
//            this@MonkeyRobot.disabledInit()
//            SubsystemManager.zeroOutputs()
//        }
//
//        override fun testInit() {
//            this@MonkeyRobot.testInit()
//        }
//
//        override fun robotPeriodic() {
//            this@MonkeyRobot.robotPeriodic()
//            SubsystemManager.periodic()
//        }
//    }
//
//    protected open fun robotInit() {}
//    protected open fun autonomousInit() {}
//    protected open fun teleopInit() {}
//    protected open fun disabledInit() {}
//    protected open fun testInit() {}
//
//    protected open fun robotPeriodic() {}
//    protected open fun autonomousPeriodic() {}
//    protected open fun teleopPeriodic() {}
//    protected open fun disabledPeriodic() {}
//    protected open fun testIPeriodic() {}
//
//    open operator fun Subsystem.unaryPlus() {
//        SubsystemManager.addSubsystem(this)
//    }
//
//    fun start() {
//        RobotBase.startRobot { wrappedValue }
//    }
// }

/* TODO(maybe implement this to get rid of overrun messages and have more control) */
