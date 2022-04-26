// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
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
import frc.robot.Constants;
import frc.robot.Constants.CANDevices;
import frc.robot.Constants.HoodConstants;
import frc.robot.Constants.SpeedConstants;
import frc.robot.Constants.VelocityClosedLoop;

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
  private NetworkTableEntry shooterSpeed=Tab.add("ShooterSpeed", SpeedConstants.shooterSpeed)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 1))
        .getEntry();

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

    rightMotor.configFactoryDefault();
    rightMotor.configNeutralDeadband(0.0001);
    rightMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kTimeoutMs);
    rightMotor.configNominalOutputForward(0, Constants.VelocityClosedLoop.kTimeoutMs);
		rightMotor.configNominalOutputReverse(0, Constants.VelocityClosedLoop.kTimeoutMs);
		rightMotor.configPeakOutputForward(1, Constants.VelocityClosedLoop.kTimeoutMs);
		rightMotor.configPeakOutputReverse(-1, Constants.VelocityClosedLoop.kTimeoutMs);
    rightMotor.config_kF(Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kGains_Velocit.kF, Constants.VelocityClosedLoop.kTimeoutMs);
		rightMotor.config_kP(Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kGains_Velocit.kP, Constants.VelocityClosedLoop.kTimeoutMs);
		rightMotor.config_kI(Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kGains_Velocit.kI, Constants.VelocityClosedLoop.kTimeoutMs);
		rightMotor.config_kD(Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kGains_Velocit.kD, Constants.VelocityClosedLoop.kTimeoutMs);

    leftMotor.configFactoryDefault();
    leftMotor.configNeutralDeadband(0.0001);
    leftMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kTimeoutMs);
    leftMotor.configNominalOutputForward(0, Constants.VelocityClosedLoop.kTimeoutMs);
		leftMotor.configNominalOutputReverse(0, Constants.VelocityClosedLoop.kTimeoutMs);
		leftMotor.configPeakOutputForward(1, Constants.VelocityClosedLoop.kTimeoutMs);
		leftMotor.configPeakOutputReverse(-1, Constants.VelocityClosedLoop.kTimeoutMs);
    leftMotor.config_kF(Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kGains_Velocit.kF, Constants.VelocityClosedLoop.kTimeoutMs);
		leftMotor.config_kP(Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kGains_Velocit.kP, Constants.VelocityClosedLoop.kTimeoutMs);
		leftMotor.config_kI(Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kGains_Velocit.kI, Constants.VelocityClosedLoop.kTimeoutMs);
		leftMotor.config_kD(Constants.VelocityClosedLoop.kPIDLoopIdx, Constants.VelocityClosedLoop.kGains_Velocit.kD, Constants.VelocityClosedLoop.kTimeoutMs);
    
    rightMotor.setNeutralMode(NeutralMode.Coast);
    leftMotor.setNeutralMode(NeutralMode.Coast);

    // rightMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 65, 1.0));
    // rightMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 55, 60, 1.0));
    // leftMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 65, 1.0));
    // leftMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 55, 60, 1.0));

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

  public void stopShooter() {
    rightMotor.set(TalonFXControlMode.PercentOutput, 0);
  }

  public void setShooterMotor(double speed, boolean isForward) {
    // sets the speed of the shooter so both mtors(=1.0 to 1.0)
    // reduction factor is in constants
    // the shooter shoots the balls when direction constant is -1

    double max = shooterSpeed.getDouble(SpeedConstants.shooterSpeed);

    if (isForward) {
      directionConstant = -1;
    }
    else {
      directionConstant = 1;
    }
    
    if (getCurrentPosition() == HoodConstants.lowPosition) {
      rightMotor.set(TalonFXControlMode.Velocity, speed*directionConstant*HoodConstants.lowShooterSpeed*VelocityClosedLoop.maxRPM);
      currentSpeed = HoodConstants.lowShooterSpeed;
    }
    else if (getCurrentPosition() == HoodConstants.highPosition) {
      rightMotor.set(TalonFXControlMode.Velocity, speed*directionConstant*HoodConstants.highShooterSpeed*VelocityClosedLoop.maxRPM);
      currentSpeed = HoodConstants.highShooterSpeed;
    }
    else if (getCurrentPosition() == HoodConstants.farPosition) {
      rightMotor.set(TalonFXControlMode.Velocity, speed*directionConstant*HoodConstants.farShooterSpeed*VelocityClosedLoop.maxRPM);
      currentSpeed = HoodConstants.farShooterSpeed;
    }
    else if (getCurrentPosition() == HoodConstants.veryfarPosition) {
      rightMotor.set(TalonFXControlMode.Velocity, speed*directionConstant*HoodConstants.veryfarShooterSpeed*VelocityClosedLoop.maxRPM);
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
