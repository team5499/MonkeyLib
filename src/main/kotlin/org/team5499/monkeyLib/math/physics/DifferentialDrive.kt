package org.team5499.monkeyLib.math.physics

import org.team5499.monkeyLib.util.CSVWritable

import java.lang.StringBuilder

import java.text.DecimalFormat

// thanks 254!
public class DifferentialDrive(
    mass: Double,
    momentOfInertia: Double,
    angularDrag: Double,
    wheelRadius: Double,
    effectiveWheelbaseRadius: Double,
    leftTransmission: DCMotorTransmission,
    rightTransmission: DCMotorTransmission
) {

    // All units must be SI!

    // Equivalent mass when accelerating purely linearly, in kg.
    // This is "equivalent" in that it also absorbs the effects of drivetrain inertia.
    // Measure by doing drivetrain acceleration characterization in a straight line.
    protected val mass: Double

    // Equivalent moment of inertia when accelerating purely angularly, in kg*m^2.
    // This is "equivalent" in that it also absorbs the effects of drivetrain inertia.
    // Measure by doing drivetrain acceleration characterization while turning in place.
    protected val momentOfInertia: Double

    // Drag torque (proportional to angular velocity) that resists turning, in N*m/rad/s
    // Empirical testing of our drivebase showed that there was an unexplained loss in torque ~proportional to angular
    // velocity, likely due to scrub of wheels.
    // NOTE: this may not be a purely linear term, and we have done limited testing, but this factor helps our model to
    // better match reality.  For future seasons, we should investigate what's going on here...
    protected val angularDrag: Double

    // Self-explanatory.  Measure by rolling the robot a known distance and counting encoder ticks.
    protected val wheelRadius: Double // m

    // "Effective" kinematic wheelbase radius.  Might be larger than theoretical to compensate for skid steer.  Measure
    // by turning the robot in place several times and figuring out what the equivalent wheelbase radius is.
    protected val effectiveWheelbaseRadius: Double // m

    // Transmissions for both sides of the drive.
    protected val leftTransmission: DCMotorTransmission
    protected val rightTransmission: DCMotorTransmission

    init {
        this.mass = mass
        this.momentOfInertia = momentOfInertia
        this.angularDrag = angularDrag
        this.wheelRadius = wheelRadius
        this.effectiveWheelbaseRadius = effectiveWheelbaseRadius
        this.leftTransmission = leftTransmission
        this.rightTransmission = rightTransmission
    }

    // Can refer to velocity or acceleration depending on context.
    public class ChassisState(var linear: Double, var angular: Double) {

        public constructor(): this(0.0, 0.0)

        public override fun toString(): String {
            val fmt = DecimalFormat("#0.000")
            return "${fmt.format(linear)}, ${fmt.format(angular)}"
        }
    }

    // Can refer to velocity, acceleration, torque, voltage, etc., depending on context.
    public class WheelState(var left: Double, var right: Double) {

        public constructor(): this(0.0, 0.0)

        public override fun toString(): String {
            val fmt = DecimalFormat("#0.000")
            return "${fmt.format(left)}, ${fmt.format(right)}"
        }
    }

    public class DriveDynamics : CSVWritable {
        public var curvature = 0.0
        public var dCurvature = 0.0
        public var chassisVelocity = ChassisState()
        public var chassisAcceleration = ChassisState()
        public var wheelVelocity = WheelState()
        public var wheelAcceleration = WheelState()
        public val wheelVoltage = WheelState()
        public val wheelTorque = WheelState()

        public override fun toCSV(): String {
            val sb = StringBuilder()
            sb.append(curvature)
            sb.append(dCurvature)
            sb.append(chassisVelocity)
            sb.append(chassisAcceleration)
            sb.append(wheelVelocity)
            sb.append(wheelAcceleration)
            sb.append(wheelTorque)
            sb.append(wheelVoltage)
            return sb.toString()
        }
    }
}
