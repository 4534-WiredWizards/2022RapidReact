// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANDevices;
import frc.robot.Constants.HoodConstants;
import frc.robot.Constants.SpeedConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  private final TalonFX leftMotor;
  private final TalonFX rightMotor;
  private double currentSpeed = SpeedConstants.shooterSpeed;
  private final double HOODADJUSTRATE=0.01;
  private double currentPos;
  private double currentPosition = 0;
  DigitalInput hoodSensor = new DigitalInput(8);

  private CANSparkMax Hood;
  private RelativeEncoder hoodEncoder;

  private double hoodDirectionConstant;
  private double directionConstant;

  private ShuffleboardTab Tab=Shuffleboard.getTab("Shooter");
  // private NetworkTableEntry shooterSpeed=Tab.add("ShooterSpeed", SpeedConstants.shooterSpeed)
  //       .withWidget(BuiltInWidgets.kNumberSlider)
  //       .withProperties(Map.of("min", 0, "max", 1))
  //       .getEntry();

  private NetworkTableEntry hoodPosition=Tab.add("HoodPosition", 0.5)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 1))
        .getEntry();
  

  public Shooter() {
    // initalizes left and right motor of flywheel
    // flywheel motor ids are in constants
    leftMotor = new TalonFX(CANDevices.leftFlywheelMotorId);
    rightMotor = new TalonFX(CANDevices.rightFlywheelMotorId);

    Hood = new CANSparkMax(CANDevices.hoodMotorId, MotorType.kBrushless);
    hoodEncoder = Hood.getEncoder();
    
    

    // makes left motor dependent on right motor but reversed
    leftMotor.follow(rightMotor);
    leftMotor.setInverted(true);
    // Hood.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    //hoodEncoder.setPositionConversionFactor(120);
  }
  // public void setHood(double value){
  //   // Hood.set(MathUtil.clamp(value, 0, 1.0)); //formerly 0.61

  //   // System.out.println("sethood "+value);
  //   //hoodEncoder.setPosition(MathUtil.clamp(value, 0, 1.0)*120);
  //   if (getHood() < value*100) {
  //     hoodDirectionConstant = -1;
  //     while (getHood() < value*100) {
  //       Hood.set(hoodDirectionConstant*0.05);
  //       currentPosition = getHood()/100;
  //     }
  //   } else if (getHood() > value*100) {
  //     hoodDirectionConstant = 1;
  //     while (getHood() > value*100) {
  //       Hood.set(hoodDirectionConstant*0.05);
  //       currentPosition = getHood()/100;
  //     }
  //   }

  //   Hood.set(0);
  // }

  public void setHoodSpeed(double speed) {
    Hood.set(speed);
  }

  public double getHood(){
    return -1 * hoodEncoder.getPosition();
  }

  public void setHood(double hoodPos) {
    hoodEncoder.setPosition(hoodPos);
  }

  public void raiseHood(){
  // if (currentPosition < 1-HOODADJUSTRATE) {
  //   currentPosition += HOODADJUSTRATE;
  //   setHood(currentPosition);
  //   Hood.set(0.1);
  //   }
    setHoodSpeed(-HoodConstants.normalHoodSpeed);
  }
  
  public void lowerHood(){
    // if (currentPosition > HOODADJUSTRATE) {
    // currentPosition -= HOODADJUSTRATE;
    // setHood(currentPosition);
    // Hood.set(-0.1);
    // }
      setHoodSpeed(HoodConstants.normalHoodSpeed);
    }

  public void raiseHoodSlower() {
    setHoodSpeed(-HoodConstants.slowHoodSpeed);
  }

  public void lowerHoodSlower() {
    setHoodSpeed(HoodConstants.slowHoodSpeed);
  }


  // public void setLowPosition() {
  //   setHood(HoodConstants.lowPosition);
  //   currentPosition = HoodConstants.lowPosition;
  //   System.out.println("Set Low Position");
  // }

  // public void setHighPosition() {
  //   setHood(HoodConstants.highPosition);
  //   currentPosition = HoodConstants.highPosition;
  //   System.out.println("Set High Position");
  // }

  // public void setFarPosition() {
  //   setHood(HoodConstants.farPosition);
  //   currentPosition = HoodConstants.farPosition;
  // }

  // public void setVeryFarPosition() {
  //   setHood(HoodConstants.veryfarPosition);
  //   currentPosition = HoodConstants.veryfarPosition;
  // }

  public double getSliderPosition() {
    return hoodPosition.getDouble(0.5);
  }

  public void updateShooter() {
    SmartDashboard.putNumber("ShooterSpeed", currentSpeed);
    SmartDashboard.putNumber("HoodPosition", getCurrentPosition());
    SmartDashboard.putNumber("HoodRotations", getHood());
    SmartDashboard.putNumber("Shooter RPM",(0-rightMotor.getSensorCollection().getIntegratedSensorVelocity())/21777);
    SmartDashboard.putBoolean("HoodSensor", hoodSensor.get());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setShooterMotor(double speed, boolean isForward) {
    // sets the speed of the shooter so both mtors(=1.0 to 1.0)
    // reduction factor is in constants
    // the shooter shoots the balls when direction constant is -1
    if (isForward) {
      directionConstant = -1;
    }
    else {
      directionConstant = 1;
    }

    double max = 0.4;
    
    if (getCurrentPosition() == HoodConstants.lowPosition) {
      rightMotor.set(TalonFXControlMode.PercentOutput, speed*directionConstant*HoodConstants.lowShooterSpeed);
      currentSpeed = HoodConstants.lowShooterSpeed;
    }
    else if (getCurrentPosition() == HoodConstants.highPosition) {
      rightMotor.set(TalonFXControlMode.PercentOutput, speed*directionConstant*HoodConstants.highShooterSpeed);
      currentSpeed = HoodConstants.highShooterSpeed;
    }
    else if (getCurrentPosition() == HoodConstants.farPosition) {
      rightMotor.set(TalonFXControlMode.PercentOutput, speed*directionConstant*HoodConstants.farShooterSpeed);
      currentSpeed = HoodConstants.farShooterSpeed;
    }
    else if (getCurrentPosition() == HoodConstants.veryfarPosition) {
      rightMotor.set(TalonFXControlMode.PercentOutput, speed*directionConstant*HoodConstants.veryfarShooterSpeed);
      currentSpeed = HoodConstants.veryfarShooterSpeed;
    }
    else {
      if (speed > max) {
        rightMotor.set(TalonFXControlMode.PercentOutput, directionConstant*max*CANDevices.reductionFactor);
        currentSpeed = max;
      } else {
        rightMotor.set(TalonFXControlMode.PercentOutput, directionConstant*speed*CANDevices.reductionFactor);
        currentSpeed = speed;
      }
    }
  }

  public double getShooterMotor() {return currentSpeed;}

  public double getCurrentPosition() {return currentPosition;}

  public void setCurrentPosition(double position) {
    currentPosition = position;
  }

  public boolean getHoodSensor() {
    return hoodSensor.get();
  }
}
