package org.team5499.monkeyLib.subsystems.drivetrain

import org.team5499.monkeyLib.util.CSVWritable

data class CharacterizationData(
    val voltage: Double, // volts
    val velocity: Double, // rads / s
    val dt: Double // s
) : CSVWritable {

    override fun toCSV() = "$voltage,$velocity,$dt"
}
