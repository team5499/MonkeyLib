package org.team5419.fault.hardware

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.GenericHID.Hand

@SuppressWarnings("TooManyFunctions", "VariableNaming")
public class PS4Controller(portNumber: Int) : Joystick(portNumber) {

    // Ports
    private val SQUARE_BUTTON_PORT: Int = 1
    private val X_BUTTON_PORT: Int = 2
    private val O_BUTTON_PORT: Int = 3
    private val TRIANGLE_BUTTON_PORT: Int = 4
    private val LEFT_BUMPER_PORT: Int = 5
    private val RIGHT_BUMPER_PORT: Int = 6
    private val LEFT_TRIGGER_PORT: Int = 7
    private val RIGHT_TRIGGER_PORT: Int = 8
    private val SHARE_BUTTON_PORT: Int = 9
    private val OPTIONS_BUTTON_PORT: Int = 10
    private val LEFT_STICK_BUTTON_PORT: Int = 11
    private val RIGHT_STICK_BUTTON_PORT: Int = 12
    private val HOME_BUTTON_PORT: Int = 13
    private val TOUCHPAD_BUTTON_PORT: Int = 14

    // Square Button
    public fun getSquareButtonPressed(): Boolean { return getRawButtonPressed(SQUARE_BUTTON_PORT) }
    public fun getSquareButtonReleased(): Boolean { return getRawButtonReleased(SQUARE_BUTTON_PORT) }
    public fun getSquareButton(): Boolean { return getRawButton(SQUARE_BUTTON_PORT) }

    // X Button
    public fun getXButtonPressed(): Boolean { return getRawButtonPressed(X_BUTTON_PORT) }
    public fun getXButtonReleased(): Boolean { return getRawButtonReleased(X_BUTTON_PORT) }
    public fun getXButton(): Boolean { return getRawButton(X_BUTTON_PORT) }

    // O Button
    public fun getOButtonPressed(): Boolean { return getRawButtonPressed(O_BUTTON_PORT) }
    public fun getOButtonReleased(): Boolean { return getRawButtonReleased(O_BUTTON_PORT) }
    public fun getOButton(): Boolean { return getRawButton(O_BUTTON_PORT) }

    // Triangle Button
    public fun getTriangleButtonPressed(): Boolean { return getRawButtonPressed(TRIANGLE_BUTTON_PORT) }
    public fun getTriangleButtonReleased(): Boolean { return getRawButtonReleased(TRIANGLE_BUTTON_PORT) }
    public fun getTriangleButton(): Boolean { return getRawButton(TRIANGLE_BUTTON_PORT) }

    // Left & Right Bumpers
    public fun getBumperPressed(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonPressed(LEFT_BUMPER_PORT)
            Hand.kRight -> return getRawButtonPressed(RIGHT_BUMPER_PORT)
        }
    }

    public fun getBumperReleased(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonReleased(LEFT_BUMPER_PORT)
            Hand.kRight -> return getRawButtonReleased(RIGHT_BUMPER_PORT)
        }
    }

    public fun getBumper(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButton(LEFT_BUMPER_PORT)
            Hand.kRight -> return getRawButton(RIGHT_BUMPER_PORT)
        }
    }

    // Left & Right Triggers
    public fun getTriggerPressed(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonPressed(LEFT_TRIGGER_PORT)
            Hand.kRight -> return getRawButtonPressed(RIGHT_TRIGGER_PORT)
        }
    }

    public fun getTriggerReleased(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonReleased(LEFT_TRIGGER_PORT)
            Hand.kRight -> return getRawButtonReleased(RIGHT_TRIGGER_PORT)
        }
    }

    public fun getTrigger(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButton(LEFT_TRIGGER_PORT)
            Hand.kRight -> return getRawButton(RIGHT_TRIGGER_PORT)
        }
    }

    // Share Button
    public fun getShareButtonPressed(): Boolean { return getRawButtonPressed(SHARE_BUTTON_PORT) }
    public fun getShareButtonReleased(): Boolean { return getRawButtonReleased(SHARE_BUTTON_PORT) }
    public fun getShareButton(): Boolean { return getRawButton(SHARE_BUTTON_PORT) }

    // Options Button
    public fun getOptionsButtonPressed(): Boolean { return getRawButtonPressed(OPTIONS_BUTTON_PORT) }
    public fun getOptionsButtonReleased(): Boolean { return getRawButtonReleased(OPTIONS_BUTTON_PORT) }
    public fun getOptionsButton(): Boolean { return getRawButton(OPTIONS_BUTTON_PORT) }

    // Left & Right Sticks
    public fun getStickButtonPressed(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonPressed(LEFT_STICK_BUTTON_PORT)
            Hand.kRight -> return getRawButtonPressed(RIGHT_STICK_BUTTON_PORT)
        }
    }

    public fun getStickButtonReleased(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonReleased(LEFT_STICK_BUTTON_PORT)
            Hand.kRight -> return getRawButtonReleased(RIGHT_STICK_BUTTON_PORT)
        }
    }

    public fun getStickButton(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButton(LEFT_STICK_BUTTON_PORT)
            Hand.kRight -> return getRawButton(RIGHT_STICK_BUTTON_PORT)
        }
    }

    // Home Button
    public fun getHomeButtonPressed(): Boolean { return getRawButtonPressed(HOME_BUTTON_PORT) }
    public fun getHomeButtonReleased(): Boolean { return getRawButtonReleased(HOME_BUTTON_PORT) }
    public fun getHomeButton(): Boolean { return getRawButton(HOME_BUTTON_PORT) }

    // Touch Pad
    public fun getTouchPadPressed(): Boolean { return getRawButtonPressed(TOUCHPAD_BUTTON_PORT) }
    public fun getTouchPadReleased(): Boolean { return getRawButtonReleased(TOUCHPAD_BUTTON_PORT) }
    public fun getTouchPad(): Boolean { return getRawButton(TOUCHPAD_BUTTON_PORT) }

    // POV
    public fun getPOVUp(error: Int): Boolean { return getPOV() <= 1 + error && getPOV() >= 360 - error }
    public fun getPOVLeft(error: Int): Boolean { return getPOV() <= 270 + error && getPOV() >= 270 - error }
    public fun getPOVDown(error: Int): Boolean { return getPOV() <= 180 + error && getPOV() >= 180 - error }
    public fun getPOVRight(error: Int): Boolean { return getPOV() <= 90 + error && getPOV() >= 90 - error }
}
