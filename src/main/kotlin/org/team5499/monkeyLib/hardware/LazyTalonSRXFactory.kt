package org.team5499.monkeyLib.hardware

import com.ctre.phoenix.ParamEnum
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.LimitSwitchSource
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.ControlFrame
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod

/**
 * Creates CANTalon objects and configures all the parameters we care about to factory defaults. Closed-loop and sensor
 * parameters are not set, as these are expected to be set by the application.
 */
public object LazyTalonSRXFactory {

    private const val kTimeoutMs = 100

    public class Configuration {
        public var NEUTRAL_MODE = NeutralMode.Coast
        // This is factory default.
        public var NEUTRAL_DEADBAND = 0.04

        public var ENABLE_CURRENT_LIMIT = false
        public var ENABLE_SOFT_LIMIT = false
        public var ENABLE_LIMIT_SWITCH = false
        public var FORWARD_SOFT_LIMIT = 0
        public var REVERSE_SOFT_LIMIT = 0

        public var INVERTED = false
        public var SENSOR_PHASE = false

        public var CONTROL_FRAME_PERIOD_MS = 5
        public var MOTION_CONTROL_FRAME_PERIOD_MS = 100
        public var GENERAL_STATUS_FRAME_RATE_MS = 5
        public var FEEDBACK_STATUS_FRAME_RATE_MS = 100
        public var QUAD_ENCODER_STATUS_FRAME_RATE_MS = 100
        public var ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 100
        public var PULSE_WIDTH_STATUS_FRAME_RATE_MS = 100

        public val VELOCITY_MEASUREMENT_PERIOD = VelocityMeasPeriod.Period_100Ms
        public var VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW = 64

        public var OPEN_LOOP_RAMP_RATE = 0.0
        public var CLOSED_LOOP_RAMP_RATE = 0.0
    }

    private val kDefaultConfiguration = Configuration()
    private val kSlaveConfiguration = Configuration()

    init {
        // This control frame value seems to need to be something reasonable to avoid the Talon's
        // LEDs behaving erratically.  Potentially try to increase as much as possible.
        kSlaveConfiguration.CONTROL_FRAME_PERIOD_MS = 100
        kSlaveConfiguration.MOTION_CONTROL_FRAME_PERIOD_MS = 1000
        kSlaveConfiguration.GENERAL_STATUS_FRAME_RATE_MS = 1000
        kSlaveConfiguration.FEEDBACK_STATUS_FRAME_RATE_MS = 1000
        kSlaveConfiguration.QUAD_ENCODER_STATUS_FRAME_RATE_MS = 1000
        kSlaveConfiguration.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 1000
        kSlaveConfiguration.PULSE_WIDTH_STATUS_FRAME_RATE_MS = 1000
    }

    // Create a CANTalon with the default (out of the box) configuration.
    public fun createDefaultTalon(id: Int): LazyTalonSRX {
        return createTalon(id, kDefaultConfiguration)
    }

    public fun createPermanentSlaveTalon(id: Int, masterId: Int): LazyTalonSRX {
        val talon = createTalon(id, kSlaveConfiguration)
        talon.set(ControlMode.Follower, masterId.toDouble())
        return talon
    }

        @Suppress("LongMethod")
    public fun createTalon(id: Int, config: Configuration): LazyTalonSRX {
        val talon = LazyTalonSRX(id)
        talon.set(ControlMode.PercentOutput, 0.0)

        talon.changeMotionControlFramePeriod(config.MOTION_CONTROL_FRAME_PERIOD_MS)
        talon.clearMotionProfileHasUnderrun(kTimeoutMs)
        talon.clearMotionProfileTrajectories()

        talon.clearStickyFaults(kTimeoutMs)

        talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen, kTimeoutMs)
        talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen, kTimeoutMs)
        talon.overrideLimitSwitchesEnable(config.ENABLE_LIMIT_SWITCH)

        // Turn off re-zeroing by default.
        talon.configSetParameter(
                ParamEnum.eClearPositionOnLimitF, 0.0, 0, 0, kTimeoutMs)
        talon.configSetParameter(
                ParamEnum.eClearPositionOnLimitR, 0.0, 0, 0, kTimeoutMs)

        talon.configNominalOutputForward(0.0, kTimeoutMs)
        talon.configNominalOutputReverse(0.0, kTimeoutMs)
        talon.configNeutralDeadband(config.NEUTRAL_DEADBAND, kTimeoutMs)

        talon.configPeakOutputForward(1.0, kTimeoutMs)
        talon.configPeakOutputReverse(-1.0, kTimeoutMs)

        talon.setNeutralMode(config.NEUTRAL_MODE)

        talon.configForwardSoftLimitThreshold(config.FORWARD_SOFT_LIMIT, kTimeoutMs)
        talon.configForwardSoftLimitEnable(config.ENABLE_SOFT_LIMIT, kTimeoutMs)

        talon.configReverseSoftLimitThreshold(config.REVERSE_SOFT_LIMIT, kTimeoutMs)
        talon.configReverseSoftLimitEnable(config.ENABLE_SOFT_LIMIT, kTimeoutMs)
        talon.overrideSoftLimitsEnable(config.ENABLE_SOFT_LIMIT)

        talon.setInverted(config.INVERTED)
        talon.setSensorPhase(config.SENSOR_PHASE)

        talon.selectProfileSlot(0, 0)

        talon.configVelocityMeasurementPeriod(config.VELOCITY_MEASUREMENT_PERIOD, kTimeoutMs)
        talon.configVelocityMeasurementWindow(config.VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW,
                kTimeoutMs)

        talon.configOpenloopRamp(config.OPEN_LOOP_RAMP_RATE, kTimeoutMs)
        talon.configClosedloopRamp(config.CLOSED_LOOP_RAMP_RATE, kTimeoutMs)

        talon.configVoltageCompSaturation(0.0, kTimeoutMs)
        talon.configVoltageMeasurementFilter(32, kTimeoutMs)
        talon.enableVoltageCompensation(false)

        talon.enableCurrentLimit(config.ENABLE_CURRENT_LIMIT)

        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General,
                config.GENERAL_STATUS_FRAME_RATE_MS, kTimeoutMs)
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0,
                config.FEEDBACK_STATUS_FRAME_RATE_MS, kTimeoutMs)

        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature,
                config.QUAD_ENCODER_STATUS_FRAME_RATE_MS, kTimeoutMs)
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat,
                config.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS, kTimeoutMs)
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth,
                config.PULSE_WIDTH_STATUS_FRAME_RATE_MS, kTimeoutMs)

        talon.setControlFramePeriod(ControlFrame.Control_3_General, config.CONTROL_FRAME_PERIOD_MS)

        return talon
    }
}
