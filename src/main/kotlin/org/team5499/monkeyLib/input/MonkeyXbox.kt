@file:Suppress("MatchingDeclarationName")
package org.team5499.monkeyLib.input

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController

typealias MonkeyXboxController = MonkeyHID<XboxController>
typealias MonkeyXboxBuilder = MonkeyHIDBuilder<XboxController>

fun xboxController(port: Int, block: MonkeyXboxBuilder.() -> Unit): MonkeyXboxController =
        XboxController(port).mapControls(block)

fun MonkeyXboxBuilder.button(
    button: XboxButton,
    block: HIDButtonBuilder.() -> Unit = {}
) = button(button.id, block)

fun MonkeyXboxBuilder.triggerAxisButton(
    hand: GenericHID.Hand,
    threshold: Double = HIDButton.kDefaultThreshold,
    block: HIDButtonBuilder.() -> Unit = {}
) = axisButton(yTriggerAxisToRawAxis(hand), threshold, block)

fun MonkeyXboxController.getY(hand: GenericHID.Hand) = getRawAxis(yAxisToRawAxis(hand))

fun MonkeyXboxController.getX(hand: GenericHID.Hand) = getRawAxis(xAxisToRawAxis(hand))

fun MonkeyXboxController.getRawButton(button: XboxButton) = getRawButton(button.id)

private fun yAxisToRawAxis(hand: GenericHID.Hand) = if (hand == GenericHID.Hand.kLeft) 1 else 5
private fun xAxisToRawAxis(hand: GenericHID.Hand) = if (hand == GenericHID.Hand.kLeft) 0 else 4
private fun yTriggerAxisToRawAxis(hand: GenericHID.Hand) = if (hand == GenericHID.Hand.kLeft) 2 else 3

enum class XboxButton(val id: Int) {
        A(1),
        B(2),
        X(3),
        Y(4),
        LeftBumper(5),
        RightBumper(6),
        Back(7),
        Start(8),
        LeftStick(9),
        RightStick(10)
}
