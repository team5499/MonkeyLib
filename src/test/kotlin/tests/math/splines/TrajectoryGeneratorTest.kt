package tests.math.splines

import org.junit.Test
import org.knowm.xchart.XYChartBuilder
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.degree
import org.team5499.monkeyLib.math.geometry.radian
import org.team5499.monkeyLib.math.physics.DCMotorTransmission
import org.team5499.monkeyLib.math.physics.DifferentialDrive
import org.team5499.monkeyLib.math.units.feet
import org.team5499.monkeyLib.math.units.inch
import org.team5499.monkeyLib.math.units.second
import org.team5499.monkeyLib.math.units.millisecond
import org.team5499.monkeyLib.math.units.SILengthConstants
import org.team5499.monkeyLib.math.units.derived.acceleration
import org.team5499.monkeyLib.math.units.derived.velocity
import org.team5499.monkeyLib.math.units.derived.volt
import org.team5499.monkeyLib.trajectory.DefaultTrajectoryGenerator
import org.team5499.monkeyLib.trajectory.constraints.AngularAccelerationConstraint
import org.team5499.monkeyLib.trajectory.constraints.CentripetalAccelerationConstraint
import org.team5499.monkeyLib.trajectory.constraints.DifferentialDriveDynamicsConstraint
import java.awt.Color
import java.awt.Font
import java.text.DecimalFormat
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

    @Suppress("LongMethod")
    @Test
    fun testTrajectoryGenerationAndConstraints() {
        val iterator = trajectory.iterator()
        var time = 0.second
        var dt = 20.millisecond
        val refList = mutableListOf<Vector2>()
        while (!iterator.isDone) {
            val pt = iterator.advance(dt)
            time += dt
            val ac = pt.state.velocity.value.pow(2) * pt.state.state.curvature

            refList.add(pt.state.state.pose.translation)

            assert(ac <= kMaxCentripetalAcceleration.value + kTolerance)
            assert(pt.state.velocity.value.absoluteValue < kMaxVelocity.value + kTolerance)
            assert(pt.state.acceleration.value.absoluteValue < kMaxAcceleration.value + kTolerance)

            assert(
                    pt.state._acceleration * pt.state.state.curvature +
                            pt.state._velocity.pow(2) * pt.state.state.dCurvature
                            < kMaxAngularAcceleration.value + kTolerance
            )
        }

        val fm = DecimalFormat("#.###").format(TrajectoryGeneratorTest.trajectory.lastInterpolant.second)
        val chart = XYChartBuilder().width(1800).height(1520).title("$fm seconds.")
                .xAxisTitle("X").yAxisTitle("Y").build()
        chart.styler.markerSize = 8
        chart.styler.seriesColors = arrayOf(Color.ORANGE, Color(151, 60, 67))

        chart.styler.chartTitleFont = Font("Kanit", 1, 40)
        chart.styler.chartTitlePadding = 15

        chart.styler.xAxisMin = 1.0
        chart.styler.xAxisMax = 26.0
        chart.styler.yAxisMin = 1.0
        chart.styler.yAxisMax = 26.0

        chart.styler.chartFontColor = Color.WHITE
        chart.styler.axisTickLabelsColor = Color.WHITE

        chart.styler.legendBackgroundColor = Color.GRAY

        chart.styler.isPlotGridLinesVisible = true
        chart.styler.isLegendVisible = true

        chart.styler.plotGridLinesColor = Color.GRAY
        chart.styler.chartBackgroundColor = Color.DARK_GRAY
        chart.styler.plotBackgroundColor = Color.DARK_GRAY

        chart.addSeries(
                "Trajectory",
                refList.map { it.x / SILengthConstants.kFeetToMeter }.toDoubleArray(),
                refList.map { it.y / SILengthConstants.kFeetToMeter }.toDoubleArray()
        )
        // Uncomment these to see generation
//        SwingWrapper(chart).displayChart()
//        Thread.sleep(1000000)
    }
}
