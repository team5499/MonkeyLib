package tests.math.splines

import org.junit.Test
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.degree
import org.team5499.monkeyLib.math.geometry.radian
import org.team5499.monkeyLib.math.physics.DCMotorTransmission
import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.math.units.derived.acceleration
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.math.units.derived.volt
import org.team5499.monkeyLib.math.units.feet
import org.team5499.monkeyLib.math.units.inch
import org.team5499.monkeyLib.math.units.millisecond
import org.team5499.monkeyLib.math.units.second
import org.team5499.monkeyLib.trajectory.DefaultTrajectoryGenerator
import org.team5499.monkeyLib.trajectory.constraints.AngularAccelerationConstraint
import org.team5499.monkeyLib.trajectory.constraints.CentripetalAccelerationConstraint
import org.team5499.monkeyLib.trajectory.constraints.DifferentialDriveDynamicsConstraint
import kotlin.math.absoluteValue
import kotlin.math.pow

class TrajectoryGeneratorTest {

    companion object {
        private const val kRobotLinearInertia = 60.0
        private const val kRobotAngularInertia = 10.0
        private const val kRobotAngularDrag = 12.0
        private const val kDriveFrictionVoltage = 1.055
        private const val kDriveKv = 0.135
        private const val kDriveKa = 0.012

        private val kDriveWheelRadiusInches = 3.0.inch
        private val kWheelBaseDiameter = 29.5.inch

        private val transmission = DCMotorTransmission(
                speedPerVolt = 1 / kDriveKv,
                torquePerVolt = kDriveWheelRadiusInches.value.pow(2) * kRobotLinearInertia / (2.0 * kDriveKa),
                frictionVoltage = kDriveFrictionVoltage
        )

        val drive = DifferentialDrive(
                mass = kRobotLinearInertia,
                moi = kRobotAngularInertia,
                angularDrag = kRobotAngularDrag,
                wheelRadius = kDriveWheelRadiusInches.value,
                effectiveWheelBaseRadius = kWheelBaseDiameter.value / 2.0,
                leftTransmission = transmission,
                rightTransmission = transmission
        )

        private val kMaxCentripetalAcceleration = 4.feet.acceleration
        private val kMaxAcceleration = 4.feet.acceleration
        private val kMaxAngularAcceleration = 2.radian.acceleration
        private val kMaxVelocity = 10.0.feet.velocity

        private const val kTolerance = 0.1

        private val kSideStart = Pose2d(1.54.feet, 23.234167.feet, 180.degree)
        private val kNearSideScaleEmpty = Pose2d(23.7.feet, 20.2.feet, 160.degree)

        val trajectory = DefaultTrajectoryGenerator.generateTrajectory(
                listOf(
                        kSideStart,
                        kSideStart + Pose2d((-13).feet, 0.feet, 0.degree),
                        kSideStart + Pose2d((-19.5).feet, 5.feet, (-90).degree),
                        kSideStart + Pose2d((-19.5).feet, 14.feet, (-90).degree),
                        kNearSideScaleEmpty.mirror
                ),
                listOf(
                        CentripetalAccelerationConstraint(kMaxCentripetalAcceleration),
                        DifferentialDriveDynamicsConstraint(drive, 9.0.volt),
                        AngularAccelerationConstraint(kMaxAngularAcceleration)
                ),
                0.0.feet.velocity,
                0.0.feet.velocity,
                kMaxVelocity,
                kMaxAcceleration,
                true
        )
    }

    @Test
    fun testTrajectoryGenerationAndConstraints() {
        val iterator = trajectory.iterator()
        var time = 0.second
        var dt = 20.millisecond
        while (!iterator.isDone) {
            val pt = iterator.advance(dt)
            time += dt
            val ac = pt.state.velocity.value.pow(2) * pt.state.state.curvature

            assert(ac <= kMaxCentripetalAcceleration.value + kTolerance)
            assert(pt.state.velocity.value.absoluteValue < kMaxVelocity.value + kTolerance)
            assert(pt.state.acceleration.value.absoluteValue < kMaxAcceleration.value + kTolerance)

            assert(
                    pt.state._acceleration * pt.state.state.curvature +
                            pt.state._velocity.pow(2) * pt.state.state.dCurvature
                            < kMaxAngularAcceleration.value + kTolerance
            )
        }
    }
}
