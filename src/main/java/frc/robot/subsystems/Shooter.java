// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANDevices;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  private final TalonFX leftMotor;
  private final TalonFX rightMotor;

  public Shooter() {
    // initalizes left and right motor of flywheel
    // flywheel motor ids are in constants
    leftMotor = new TalonFX(CANDevices.leftFlywheelMotorId);
    rightMotor = new TalonFX(CANDevices.rightFlywheelMotorId);

    // makes left motor dependent on right motor but reversed
    leftMotor.follow(rightMotor);
    leftMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setShooterMotor(double speed) {
    // sets the speed of the shooter so both mtors(=1.0 to 1.0)
    // reduction factor is in constants
    rightMotor.set(TalonFXControlMode.PercentOutput, speed*CANDevices.reductionFactor);
  }
}
