// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import javax.print.CancelablePrintJob;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import frc.robot.Constants.CANDevices;
import frc.robot.Constants.PneumaticChannels;;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private final CANSparkMax leftmotor;
  private final CANSparkMax rightmotor;
  private final CANSparkMax centermotor;

  private final Solenoid leftPiston;
  private final Solenoid rightPiston;
  private final Solenoid centerPiston;

  private final DigitalInput leftProxSensor = new DigitalInput(0);
  private final DigitalInput centerProxSensor = new DigitalInput(1);
  private final DigitalInput rightProxSensor = new DigitalInput(2);

  public Intake() {
    // initializes all 3 motors and pistons
    // motor ids and channel ids are in constants
    leftmotor = new CANSparkMax(CANDevices.leftIntakeMotorId, MotorType.kBrushless);
    rightmotor = new CANSparkMax(CANDevices.rightIntakeMotorId, MotorType.kBrushless);
    centermotor = new CANSparkMax(CANDevices.centerIntakeMotorId, MotorType.kBrushless);

    leftPiston = new Solenoid(PneumaticChannels.PCMId, PneumaticsModuleType.REVPH, PneumaticChannels.intakeSolenoidChannels[0]);
    rightPiston = new Solenoid(PneumaticChannels.PCMId, PneumaticsModuleType.REVPH, PneumaticChannels.intakeSolenoidChannels[1]);
    centerPiston = new Solenoid(PneumaticChannels.PCMId, PneumaticsModuleType.REVPH, PneumaticChannels.intakeSolenoidChannels[2]);

    addChild("LIPiston", leftPiston);
    addChild("RIPiston", rightPiston);
    addChild("CIPiston", centerPiston);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setLeftMotor(double speed) {
    // sets speed of left motor (-1.0 to 1.0) and disables motor if past prox sensor
    // reduction factor is in constants
    if (leftProxSensor.get()) {
      leftmotor.set(0);
    }
    else {
      leftmotor.set(speed*CANDevices.reductionFactor);
    }
  }

  public void setRightMotor(double speed) {
    // sets speed of right motor (-1.0 to 1.0) and disables motor if past prox sensor
    // reduction factor is in constants
    if (rightProxSensor.get()) {
      rightmotor.set(0);
    }
    else {
      rightmotor.set(speed*CANDevices.reductionFactor);
    }
  }

  public void setCenterMotor(double speed) {
    // sets speed of center motor (-1.0 to 1.0) and disables motor if past prox sensor
    // reduction factor is in constants
    if (centerProxSensor.get()) {
      centermotor.set(0);
    }
    else {
      centermotor.set(speed*CANDevices.reductionFactor);
    }
  }

  public void setLeftPiston(boolean state){
    // sets the left piston based on state
    leftPiston.set(state);
  }

  public boolean getLeftPiston(){
    // gets left piston state
    return leftPiston.get();
  } 

  public void setRightPiston(boolean state){
    // sets the right piston based on state
    rightPiston.set(state);
  }

  public boolean getRightPiston(){
    // gets the right piston state
    return rightPiston.get();
  }

  public void setCenterPiston(boolean state){
    // sets the center piston based on state
    centerPiston.set(state);
  }
  public boolean getCenterPiston(){
    // gets the center piston state
    return centerPiston.get();
  }
}
