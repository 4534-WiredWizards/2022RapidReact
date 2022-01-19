// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANDevices;;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private final CANSparkMax leftmotor;
  private final CANSparkMax rightmotor;

  public Intake() {
    leftmotor = new CANSparkMax(CANDevices.leftIntakeMotorId, MotorType.kBrushless);
    rightmotor = new CANSparkMax(CANDevices.rightIntakeMotorId, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setLeftMotor(double speed) {
    leftmotor.set(-speed/2);
  }

  public void setRightMotor(double speed) {
    rightmotor.set(-speed/2);
  }
}
