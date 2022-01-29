// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANDevices;

public class FeederWheel extends SubsystemBase {
  /** Creates a new FeederWheel. */
  private CANSparkMax feedMotor;
  public FeederWheel() {
    feedMotor = new CANSparkMax(CANDevices.feederWheelMotorId, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setFeederMotor(double speed) {
      feedMotor.set(speed*CANDevices.reductionFactor);
  }
}
