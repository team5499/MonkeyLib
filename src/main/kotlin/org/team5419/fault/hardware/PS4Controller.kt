package org.team5419.fault.hardware

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

@SuppressWarnings("TooManyFunctions")
public class PS4Controller(portNumber: Int) : XboxController(portNumber) {
    public override fun getBackButtonPressed(): Boolean {
        return getRawButtonPressed(14)
    }

    public override fun getStickButtonPressed(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonPressed(11)
            Hand.kRight -> return getRawButtonPressed(12)
        }
    }

    public override fun getStickButtonReleased(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButtonReleased(11)
            Hand.kRight -> return getRawButtonReleased(12)
        }
    }

    public override fun getStickButton(hand: Hand): Boolean {
        when (hand) {
            Hand.kLeft -> return getRawButton(11)
            Hand.kRight -> return getRawButton(12)
        }
    }

    public override fun getTriggerAxis(hand: Hand): Double {
        when (hand) {
            Hand.kLeft -> return getRawAxis(1)
            Hand.kRight -> return getRawAxis(2)
        }
    }

    public override fun getAButtonPressed(): Boolean {
        return getBButtonPressed()
    }

    public override fun getAButtonReleased(): Boolean {
        return getBButtonReleased()
    }

    public override fun getAButton(): Boolean {
        return getBButton()
    }

    public override fun getBButtonPressed(): Boolean {
        return getXButtonPressed()
    }

    public override fun getBButtonReleased(): Boolean {
        return getXButtonReleased()
    }

    public override fun getBButton(): Boolean {
        return getXButton()
    }

    public override fun getXButtonPressed(): Boolean {
        return getAButtonPressed()
    }

    public override fun getXButtonReleased(): Boolean {
        return getAButtonReleased()
    }

    public override fun getXButton(): Boolean {
        return getAButton()
    }
}
