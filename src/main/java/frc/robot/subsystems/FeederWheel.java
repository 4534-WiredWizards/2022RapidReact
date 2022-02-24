// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANDevices;

public class FeederWheel extends SubsystemBase {
  /** Creates a new FeederWheel. */

  private final DigitalInput proxSensor = new DigitalInput(0);
  
  private CANSparkMax feedMotor;
  private ShuffleboardTab Tab=Shuffleboard.getTab("FeederWheel");
  private NetworkTableEntry feederSpeed=Tab.add("FeederSpeed", 0.5)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 1))
        .getEntry();

  private double directionConstant;

  public FeederWheel() {
    // initializes motor for feeder wheel
    // motorid is in constants
    feedMotor = new CANSparkMax(CANDevices.feederWheelMotorId, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setFeederMotor(double speed, boolean forward, boolean ignoreProx) {
    // sets speed of feeder motor
    // reduction factor is in constants
    // when forward is true, the wheels pull the ball into the shooter
    // when forward is false, the wheels, move the balls away from the shooter

    double slideSpeed = feederSpeed.getDouble(0.5);
    //if direction is -1, feeder wheels run forward
    //if direction is 1, feeder wheels run backward
    if (!ignoreProx) {
      if (forward && proxSensor.get()) {
        directionConstant = 1;
        if (speed > slideSpeed) {
          feedMotor.set(directionConstant*slideSpeed*CANDevices.reductionFactor);
        }
        else {
          feedMotor.set(directionConstant*speed*CANDevices.reductionFactor);
        }
      } else if (!forward) {
        directionConstant = -1;
        if (speed > slideSpeed) {
          feedMotor.set(directionConstant*slideSpeed*CANDevices.reductionFactor);
        }
        else {
          feedMotor.set(directionConstant*speed*CANDevices.reductionFactor);
        }
      } else {
        feedMotor.set(0);
      }
    } 
    
    else {
      if (forward) {
        directionConstant = 1;
        if (speed > slideSpeed) {
          feedMotor.set(directionConstant*slideSpeed*CANDevices.reductionFactor);
        }
        else {
          feedMotor.set(directionConstant*speed*CANDevices.reductionFactor);
        }
      } else if (!forward) {
        directionConstant = -1;
        if (speed > slideSpeed) {
          feedMotor.set(directionConstant*slideSpeed*CANDevices.reductionFactor);
        }
        else {
          feedMotor.set(directionConstant*speed*CANDevices.reductionFactor);
        }
      } else {
        feedMotor.set(0);
      }
    }
  }
}


