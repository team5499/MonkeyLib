package org.team5419.fault.hardware.ctre

import com.ctre.phoenix.sensors.PigeonIMU
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.geometry.degree
import org.team5419.fault.util.Source

fun PigeonIMU.asSource(): Source<Rotation2d> = { fusedHeading.degree }