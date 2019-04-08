package org.team5499.monkeyLib.subsystems

import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.sensors.PigeonIMU

import org.team5499.monkeyLib.math.geometry.Vector2
import org.team5499.monkeyLib.math.geometry.Pose2d
import org.team5499.monkeyLib.math.geometry.Rotation2d
import org.team5499.monkeyLib.math.Position

import org.team5499.monkeyLib.util.Utils

public class CtreDrivetrain(leftMotorGroup: CtreMotorGroup, rightMotorGroup: CtreMotorGroup, gyro: PigeonIMU, config: CtreDrivetrainConfig): Drivetrain() {

    public class CtreMotorGroup(val master: BaseMotorController, vararg followers: BaseMotorController) {
        
        public val followers: Array<out BaseMotorController>

        init {
            this.followers = followers
        }
    }

    public class CtreDrivetrainConfig {
        var encoderToOutputReduction: Double = 1.0
        var wheelDiameter: Double = 6.0
        val wheelCircumference: Double
            get() = Math.PI * wheelDiameter
        var invertLeft: Boolean = true
        var encoderTicksPerRotation: Int = 4096
    }

    private val mLeftMotors: CtreMotorGroup
    private val mRightMotors: CtreMotorGroup
    private val mGyro: PigeonIMU

    private val mConfig: CtreDrivetrainConfig

    init {
        mLeftMotors = leftMotorGroup
        mRightMotors = rightMotorGroup
        mGyro = gyro
        mConfig = config
    }

    public override var brake: Boolean = false
        set(value) {
            if(value == field) return
            val mode = if (value) NeutralMode.Brake else NeutralMode.Coast
            mLeftMotors.master.setNeutralMode(mode)
            for(motor in mLeftMotors.followers) {
                motor.setNeutralMode(mode)
            }
            mRightMotors.master.setNeutralMode(mode)
            for(motor in mRightMotors.followers) {
                motor.setNeutralMode(mode)
            }
            field = value
        }

    private var mGyroOffset: Rotation2d = Rotation2d.identity

    public override var heading: Rotation2d
        get() {
            return Rotation2d.fromDegrees(mGyro.getFusedHeading()).rotateBy(mGyroOffset)
        }
        set(value) {
            println("SET HEADING: ${heading.degrees}")
            mGyroOffset = value.rotateBy(Rotation2d.fromDegrees(mGyro.getFusedHeading()).inverse())
            println("Gyro offset: ${mGyroOffset.degrees}")
        }

    public override val angularVelocity: Double
        get() {
            val xyz = doubleArrayOf(0.0, 0.0, 0.0)
            mGyro.getRawGyro(xyz)
            return xyz[1]
        }

    public override var leftDistance: Double // inches
        get() {
            val invert = if(mConfig.invertLeft) -1.0 else 1.0
            return  invert * Utils.encoderTicksToInches(
                mConfig.encoderTicksPerRotation,
                mConfig.wheelCircumference, 
                mLeftMotors.master.getSensorCollection().quad,
                mConfig.encoderToOutputReduction)
        } 
    
    public override var rightDistance: Double
        get() {
            
        }

    public override fun setPosition() {

    }


    
}