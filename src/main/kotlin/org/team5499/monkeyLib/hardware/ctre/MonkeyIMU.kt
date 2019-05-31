package org.team5499.monkeyLib.hardware.ctre

import com.ctre.phoenix.sensors.PigeonIMU
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.geometry.degree
import org.team5499.monkeyLib.util.Source

fun PigeonIMU.asSource(): Source<Rotation2d> = { fusedHeading.degree }
