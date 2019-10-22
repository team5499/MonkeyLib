package org.team5419.fault.input

import org.team5419.fault.util.Utils

public class CheesyDriveHelper(config: CheesyDriveConfig) : DriveHelper() {

    public class CheesyDriveConfig {
        public var deadband = 0.0
        public var quickstopDeadband = 0.0
        public var quickstopWeight = 0.0
        public var quickstopScalar = 0.0
        public var highWheelNonlinearity = 0.0
        public var lowWheelNonlinearity = 0.0
        public var highNeginertiaScalar = 0.0
        public var highSensitivity = 0.0
        public var lowNeginertiaTurnScalar = 0.0
        public var lowNeginertiaThreshold = 0.0
        public var lowNeginertiaFarScalar = 0.0
        public var lowNeginertiaCloseScalar = 0.0
        public var lowSensitivity = 0.0
    }

    private var config: CheesyDriveConfig

    private var mOldWheel = 0.0
    private var mQuickStopAccumlator = 0.0
    private var mNegInertiaAccumlator = 0.0
    private var mDebugCounter = 0

    init {
        this.config = config
    }

    public constructor(): this(CheesyDriveConfig())

    @Suppress("LongMethod", "ComplexMethod")
    public override fun calculateOutput(
        throttle: Double,
        wheel: Double,
        isQuickTurn: Boolean,
        isHighGear: Boolean
    ): DriveSignal {
        var newWheel = handleDeadband(wheel, config.deadband)
        var newThrottle = handleDeadband(throttle, config.deadband)

        val negInertia = newWheel - mOldWheel
        mOldWheel = newWheel

        newWheel = calculateCheesyNonlinearity(newWheel, isHighGear)

        var leftPwm: Double
        var rightPwm: Double
        var overPower: Double
        var angularPower: Double
        var linearPower: Double

        val pair = calculateCheesyNegIntertialScalar(newWheel, negInertia, isHighGear)
        val negInertiaScalar = pair.first
        val sensitivity = pair.second

        val negInertiaPower = negInertia * negInertiaScalar
        mNegInertiaAccumlator += negInertiaPower

        newWheel = newWheel + mNegInertiaAccumlator
        if (mNegInertiaAccumlator > 1) {
            mNegInertiaAccumlator -= 1
        } else if (mNegInertiaAccumlator < -1) {
            mNegInertiaAccumlator += 1
        } else {
            mNegInertiaAccumlator = 0.0
        }
        linearPower = newThrottle

        if (isQuickTurn) {
            if (Math.abs(linearPower) < config.quickstopDeadband) {
                val alpha = config.quickstopWeight
                mQuickStopAccumlator = (1 - alpha) * mQuickStopAccumlator
                        + alpha * Utils.limit(wheel, 1.0) * config.quickstopScalar
            }
            overPower = 1.0
            angularPower = newWheel
        } else {
            overPower = 0.0
            angularPower = Math.abs(throttle) * newWheel * sensitivity - mQuickStopAccumlator
            if (mQuickStopAccumlator > 1) {
                mQuickStopAccumlator -= 1
            } else if (mQuickStopAccumlator < -1) {
                mQuickStopAccumlator += 1
            } else {
                mQuickStopAccumlator = 0.0
            }
        }
        leftPwm = linearPower
        rightPwm = linearPower
        leftPwm += angularPower
        rightPwm -= angularPower

        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0)
            leftPwm = 1.0
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0)
            rightPwm = 1.0
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-1.0 - leftPwm)
            leftPwm = -1.0
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-1.0 - rightPwm)
            rightPwm = -1.0
        }

        return DriveSignal(leftPwm, rightPwm)
    }

    private fun calculateCheesyNonlinearity(wheel: Double, isHighGear: Boolean): Double {
        var newWheel = wheel
        if (isHighGear) {
            val wheelNonLinearity = config.highWheelNonlinearity
            val denominator = Math.sin(Math.PI / 2.0 * wheelNonLinearity)
            newWheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * newWheel) / denominator
            newWheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * newWheel) / denominator
        } else {
            val wheelNonLinearity = config.lowWheelNonlinearity
            val denominator = Math.sin(Math.PI / 2.0 * wheelNonLinearity)
            newWheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * newWheel) / denominator
            newWheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * newWheel) / denominator
            newWheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * newWheel) / denominator
        }
        return newWheel
    }

    private fun calculateCheesyNegIntertialScalar(
        wheel: Double,
        negInertia: Double,
        isHighGear: Boolean
    ): Pair<Double, Double> {
        val sensitivity: Double
        val negInertiaScalar: Double
        if (isHighGear) {
            negInertiaScalar = config.highNeginertiaScalar
            sensitivity = config.highSensitivity
        } else {
            if (wheel * negInertia > 0) {
                // If we are moving away from 0.0, aka, trying to get more wheel.
                negInertiaScalar = config.lowNeginertiaTurnScalar
            } else {
                // Otherwise, we are attempting to go back to 0.0.
                if (Math.abs(wheel) > config.lowNeginertiaThreshold) {
                    negInertiaScalar = config.lowNeginertiaFarScalar
                } else {
                    negInertiaScalar = config.lowNeginertiaCloseScalar
                }
            }
            sensitivity = config.lowSensitivity
        }
        return Pair<Double, Double>(negInertiaScalar, sensitivity)
    }
}
