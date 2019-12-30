package tests.math.splines

import org.junit.Test
import org.knowm.xchart.XYChartBuilder
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Vector2d
import org.team5419.fault.math.physics.DCMotorTransmission
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.units.derived.acceleration
import org.team5419.fault.math.units.derived.degrees
import org.team5419.fault.math.units.derived.radians
import org.team5419.fault.math.units.derived.volts
import org.team5419.fault.math.units.inches
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.units.feet
import org.team5419.fault.math.units.milliseconds
import org.team5419.fault.math.units.inFeet
import org.team5419.fault.math.units.inSeconds
import org.team5419.fault.math.units.milli
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.trajectory.constraints.AngularAccelerationConstraint
import org.team5419.fault.trajectory.constraints.CentripetalAccelerationConstraint
import org.team5419.fault.trajectory.constraints.DifferentialDriveDynamicsConstraint
import java.awt.Color
import java.awt.Font
import java.text.DecimalFormat
import kotlin.math.absoluteValue
import kotlin.math.pow

class TrajectoryGeneratorTest {

    companion object {
        private const val kRobotLinearInertia = 60.0 // kg
        private const val kRobotAngularInertia = 10.0 // kg m^2
        private const val kRobotAngularDrag = 12.0  // N*m / (rad/sec)
        private const val kDriveVIntercept = 1.055  // V
        private const val kDriveKv = 0.135 // V per rad/s
        private const val kDriveKa = 0.012 // V per rad/s^2

        private val kDriveWheelRadiusInches = 3.0.inches
        private val kWheelBaseDiameter = 29.5.inches

        private val transmission = DCMotorTransmission(
                speedPerVolt = 1 / kDriveKv,
                torquePerVolt = kDriveWheelRadiusInches.value.pow(2) * kRobotLinearInertia / (2.0 * kDriveKa),
                frictionVoltage = kDriveVIntercept
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

        private val kMaxCentripetalAcceleration = 4.0.feet.acceleration
        private val kMaxAcceleration = 4.0.feet.acceleration
        private val kMaxAngularAcceleration = 2.0.radians.acceleration
        private val kMaxVelocity = 10.0.feet.velocity

        private const val kTolerance = 0.1

        private val kSideStart = Pose2d(1.54.feet, 23.234167.feet, 180.0.degrees)
        private val kNearScaleEmpty = Pose2d(23.7.feet, 20.2.feet, 160.0.degrees)

        val trajectory = DefaultTrajectoryGenerator.generateTrajectory(
                listOf(
                        kSideStart,
                        kSideStart + Pose2d((-13.0).feet, 0.0.feet, 0.0.degrees),
                        kSideStart + Pose2d((-19.5).feet, 5.0.feet, (-90.0).degrees),
                        kSideStart + Pose2d((-19.5).feet, 14.0.feet, (-90.0).degrees),
                        kNearScaleEmpty.mirror
                ),
                listOf(
                        CentripetalAccelerationConstraint(kMaxCentripetalAcceleration),
                        DifferentialDriveDynamicsConstraint(drive, 9.0.volts),
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

        var time = 0.0.seconds
        val dt = 20.0.milli.seconds

        while (!iterator.isDone) {
            val pt = iterator.advance(dt)
            time += dt

            val ac = pt.state.velocity.value.pow(2) * pt.state.state.curvature

            assert(ac <= kMaxCentripetalAcceleration.value + kTolerance)
            assert(pt.state.velocity.value.absoluteValue < kMaxVelocity.value + kTolerance)
            assert(pt.state.acceleration.value.absoluteValue < kMaxAcceleration.value + kTolerance)

            assert(
                    pt.state.acceleration.value * pt.state.state.curvature +
                            pt.state.velocity.value.pow(2) * pt.state.state.dkds
                            < kMaxAngularAcceleration.value + kTolerance
            )
        }
    }

    @Suppress("LongMethod")
    @Test
    fun testTrajectoryVisualization() {
        val iterator = trajectory.iterator()
        var time = 0.seconds
        var dt = 20.milliseconds
        val refList = mutableListOf<Vector2d>()
        while (!iterator.isDone) {
            val pt = iterator.advance(dt)
            time += dt
            val ac = pt.state.velocity.value.pow(2) * pt.state.state.curvature

            refList.add(pt.state.state.pose.translation)

            assert(ac <= kMaxCentripetalAcceleration.value + kTolerance)
            assert(pt.state.velocity.value.absoluteValue < kMaxVelocity.value + kTolerance)
            assert(pt.state.acceleration.value.absoluteValue < kMaxAcceleration.value + kTolerance)

            assert(
                    pt.state.acceleration.value * pt.state.state.curvature +
                            pt.state.velocity.value.pow(2) * pt.state.state.dkds
                            < kMaxAngularAcceleration.value + kTolerance
            )
        }

        val fm = DecimalFormat("#.###").format(TrajectoryGeneratorTest.trajectory.lastInterpolant.inSeconds())
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
                refList.map { it.x.inFeet() }.toDoubleArray(),
                refList.map { it.y.inFeet() }.toDoubleArray()
        )
        // Uncomment these to see generation
//        SwingWrapper(chart).displayChart()
//        Thread.sleep(1000000)
    }
}
