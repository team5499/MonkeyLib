package org.team5499.monkeyLib.hardware 

import com.ctre.phoenix.motorcontrol.ControlMode 
import com.ctre.phoenix.motorcontrol.can.TalonSRX 
import org.team5499.monkeyLib.Subsystem 
import org.team5499.monkeyLib.util.Utils 
import edu.wpi.first.wpilibj.Timer 

import java.util.ArrayList 
import java.util.function.DoubleSupplier 

public object TalonSRXChecker {
    public class CheckerConfig {
        public var mCurrentFloor = 5 
        public var mRPMFloor = 2000

        public var mCurrentEpsilon = 5.0 
        public var mRPMEpsilon = 500.0
        public var mRPMSupplier: DoubleSupplier? = null 

        public var mRunTimeSec = 4.0 
        public var mWaitTimeSec = 2.0 
        public var mRunOutputPercentage = 0.5 
    }

    public class TalonSRXConfig(var name:String, var talon: LazyTalonSRX)

    private class StoredTalonSRXConfiguration (var mode : ControlMode, var setValue : Double)

    public fun CheckTalons(subsystem : Subsystem,
                                      talonsToCheck : MutableList<TalonSRXConfig>,
                                    checkerConfig : CheckerConfig) : Boolean {
        var failure = false 
        println("////////////////////////////////////////////////") 
        println("Checking subsystem " + subsystem::class
                + " for " + talonsToCheck.size + " talons.") 

        val currents = mutableListOf<Double>()
        val rpms = mutableListOf<Double>() 
        val storedConfigurations = mutableListOf<StoredTalonSRXConfiguration>() 

        // Record previous configuration for all talons.
        for (config in talonsToCheck) {
            var talon = LazyTalonSRX::class.java.cast(config.talon) 
            var configuration = StoredTalonSRXConfiguration( talon.controlMode, talon.lastSet )

            storedConfigurations.add(configuration) 

            // Now set to disabled.
            talon.set(ControlMode.PercentOutput, 0.0) 
        }

        for (config : TalonSRXConfig in talonsToCheck) {
            println("Checking: " + config.name) 

            config.talon.set(ControlMode.PercentOutput, checkerConfig.mRunOutputPercentage) 
            Timer.delay(checkerConfig.mRunTimeSec) 

            // Now poll the interesting information.
            var current = config.talon.getOutputCurrent() 
            currents.add(current) 
            print("Current: " + current) 

            var rpm = Double.NaN 
            if (checkerConfig.mRPMSupplier != null) {
                rpm = checkerConfig.mRPMSupplier!!.getAsDouble() 
                rpms.add(rpm) 
                print(" RPM: " + rpm) 
            }
            print('\n') 

            config.talon.set(ControlMode.PercentOutput, 0.0) 

            // And perform checks.
            if (current < checkerConfig.mCurrentFloor) {
                println(config.name + " has failed current floor check vs " +
                        checkerConfig.mCurrentFloor + "!!") 
                failure = true 
            }
            if (checkerConfig.mRPMSupplier != null) {
                if (rpm < checkerConfig.mRPMFloor) {
                    println(config.name + " has failed rpm floor check vs " +
                            checkerConfig.mRPMFloor + "!!") 
                    failure = true 
                }
            }

            Timer.delay(checkerConfig.mWaitTimeSec) 
        }

        // Now run aggregate checks.

        if (currents.size > 0) {
            var average = 0.0
            for (current in currents) {
                average = current + average
            } 
            average = average / currents.size.toDouble()
            if (!Utils.allCloseTo(currents, average, checkerConfig.mCurrentEpsilon)) {
                println("Currents varied!!!!!!!!!!!") 
                failure = true 
            }
        }

        if (rpms.size > 0) {
            var average = 0.0
            for (rpm in rpms) {
                average = rpm + average
            } 
            average = average / rpms.size.toDouble()
            if (!Utils.allCloseTo(rpms, average, checkerConfig.mRPMEpsilon)) {
                println("RPMs varied!!!!!!!!") 
                failure = true 
            }
        }

        // Restore Talon configurations
        for(i in 0..(talonsToCheck.size - 1)) {
            talonsToCheck.get(i).talon.set(storedConfigurations.get(i).mode,
                    storedConfigurations.get(i).setValue) 
        }

        return !failure 
    }
}
