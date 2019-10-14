package org.team5419.fault.hardware

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.GenericHID.Hand

@SuppressWarnings("TooManyFunctions")
public class PS4Controller(portNumber: Int) : Joystick(portNumber) {
    public fun getBackButtonPressed(): Boolean {
        return getRawButtonPressed(14)
    }

    public fun getStickButtonPressed(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonPressed(11)
            Hand.kRight -> return getRawButtonPressed(12)
        }
    }

    public fun getStickButtonReleased(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonReleased(11)
            Hand.kRight -> return getRawButtonReleased(12)
        }
    }

    public fun getStickButton(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButton(11)
            Hand.kRight -> return getRawButton(12)
        }
    }

    public fun getTriggerAxis(hand: Hand): Double {
        when (hand) {
            Hand.kLeft -> return getRawAxis(1)
            Hand.kRight -> return getRawAxis(2)
        }
    }

    public fun getAButtonPressed(): Boolean {
        return getBButtonPressed()
    }

    public fun getAButtonReleased(): Boolean {
        return getBButtonReleased()
    }

    public fun getAButton(): Boolean {
        return getBButton()
    }

    public fun getBButtonPressed(): Boolean {
        return getXButtonPressed()
    }

    public fun getBButtonReleased(): Boolean {
        return getXButtonReleased()
    }

    public fun getBButton(): Boolean {
        return getXButton()
    }

    public fun getXButtonPressed(): Boolean {
        return getAButtonPressed()
    }

    public fun getXButtonReleased(): Boolean {
        return getAButtonReleased()
    }

    public fun getXButton(): Boolean {
        return getAButton()
    }
}
