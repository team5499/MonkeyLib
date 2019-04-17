package org.team5499.monkeyLib.math.physics

import org.team5499.monkeyLib.util.CSVWritable
import org.team5499.monkeyLib.math.Epsilon

import java.lang.StringBuilder

import kotlin.math.abs

// thanks 254!
public class DifferentialDrive(
    mass: Double,
    moi: Double,
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

    protected val moi: Double

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
        this.moi = moi
        this.momentOfInertia = momentOfInertia
        this.angularDrag = angularDrag
        this.wheelRadius = wheelRadius
        this.effectiveWheelbaseRadius = effectiveWheelbaseRadius
        this.leftTransmission = leftTransmission
        this.rightTransmission = rightTransmission
    }

    public fun solveForwardKinematics(wheelMotion: WheelState): ChassisState {
        val cm = ChassisState()
        cm.linear = wheelRadius * (wheelMotion.right + wheelMotion.left) / 2.0
        cm.angular = wheelRadius * (wheelMotion.right - wheelMotion.left) / (2.0 * effectiveWheelbaseRadius)
        return cm
    }

    public fun solveInverseKinematics(chassisMotion: ChassisState): WheelState {
        val wm = WheelState()
        wm.left = (chassisMotion.linear - effectiveWheelbaseRadius * chassisMotion.angular) / wheelRadius
        wm.right = (chassisMotion.linear + effectiveWheelbaseRadius * chassisMotion.angular) / wheelRadius
        return wm
    }

    public fun solveForwardDynamics(chassisVelo: ChassisState, voltage: WheelState): DriveDynamics {
        val d = DriveDynamics()
        d.wheelVelocity = solveInverseKinematics(chassisVelo)
        d.chassisVelocity = chassisVelo
        d.curvature = d.chassisVelocity.angular / d.chassisVelocity.linear
        if (d.curvature.isNaN()) d.curvature = 0.0
        d.voltage = voltage
        solveForwardDynamics(d)
        return d
    }

    public fun solveForwardDynamics(wheelVelo: WheelState, voltage: WheelState): DriveDynamics {
        val d = DriveDynamics()
        d.wheelVelocity = wheelVelo
        d.chassisVelocity = solveForwardKinematics(wheelVelo)
        d.curvature = d.chassisVelocity.angular / d.chassisVelocity.linear
        if (d.curvature.isNaN()) d.curvature = 0.0
        d.voltage = voltage
        solveForwardDynamics(d)
        return d
    }

    public fun solveForwardDynamics(dynamics: DriveDynamics) {
        val leftStationary = Epsilon.epsilonEquals(dynamics.wheelVelocity.left, 0.0) &&
            abs(dynamics.voltage.left) < leftTransmission.frictionVoltage
        val rightStationary = Epsilon.epsilonEquals(dynamics.wheelVelocity.right, 0.0) &&
            abs(dynamics.voltage.right) < rightTransmission.frictionVoltage
        if (leftStationary && rightStationary) {
            dynamics.wheelTorque.left = 0.0
            dynamics.wheelTorque.right = 0.0
            dynamics.chassisAcceleration.linear = 0.0
            dynamics.chassisAcceleration.angular = 0.0
            dynamics.wheelAcceleration.left = 0.0
            dynamics.wheelAcceleration.right = 0.0
            dynamics.dCurvature = 0.0
            return
        }
        dynamics.wheelTorque.left = leftTransmission.getTorqueForVoltage(dynamics.wheelVelocity.left, dynamics.voltage.left)
        dynamics.wheelTorque.right = rightTransmission.getTorqueForVoltage(dynamics.wheelVelocity.right, dynamics.voltage.right)
        dynamics.chassisAcceleration.linear = (dynamics.wheelTorque.right + dynamics.wheelTorque.right) / (wheelRadius * mass)
        dynamics.chassisAcceleration.angular = effectiveWheelbaseRadius * (dynamics.wheelTorque.right - dynamics.wheelTorque.left) / (wheelRadius * moi) - dynamics.chassisVelocity.angular * angularDrag / moi
        dynamics.dCurvature = (dynamics.chassisAcceleration.angular - dynamics.chassisAcceleration.linear * dynamics.curvature) /
                (dynamics.chassisVelocity.linear * dynamics.chassisVelocity.linear)
        if (dynamics.dCurvature.isNaN()) dynamics.dCurvature = 0.0
        dynamics.wheelAcceleration.left = dynamics.chassisAcceleration.linear - dynamics.chassisAcceleration.angular * effectiveWheelbaseRadius
        dynamics.wheelAcceleration.right = dynamics.chassisAcceleration.linear + dynamics.chassisAcceleration.angular * effectiveWheelbaseRadius
    }

    public fun solveInverseDynamics(chassisVelo: ChassisState, chassisAccel: ChassisState) {
        
    }

    // Can refer to velocity or acceleration depending on context.
    public data class ChassisState(var linear: Double = 0.0, var angular: Double = 0.0)

    // Can refer to velocity, acceleration, torque, voltage, etc., depending on context.
    public data class WheelState(var left: Double = 0.0, var right: Double = 0.0)

    public data class MinMax(var min: Double = 0.0, var max: Double = 0.0)

    public class DriveDynamics : CSVWritable {
        public var curvature = 0.0
        public var dCurvature = 0.0
        public var chassisVelocity = ChassisState()
        public var chassisAcceleration = ChassisState()
        public var wheelVelocity = WheelState()
        public var wheelAcceleration = WheelState()
        public var voltage = WheelState()
        public var wheelTorque = WheelState()

        public override fun toCSV(): String {
            val sb = StringBuilder()
            sb.append(curvature)
            sb.append(dCurvature)
            sb.append(chassisVelocity)
            sb.append(chassisAcceleration)
            sb.append(wheelVelocity)
            sb.append(wheelAcceleration)
            sb.append(wheelTorque)
            sb.append(voltage)
            return sb.toString()
        }
    }
}
