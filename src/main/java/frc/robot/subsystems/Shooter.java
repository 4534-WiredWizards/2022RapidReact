// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANDevices;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  private final TalonFX leftMotor;
  private final TalonFX rightMotor;

  public static final double directionConstant = -1;

  private ShuffleboardTab Tab=Shuffleboard.getTab("Shooter");
  private NetworkTableEntry shooterSpeed=Tab.add("ShooterSpeed", 0.5)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 1))
        .getEntry();

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
    // the shooter shoots the balls when direction constant is -1
    double max = shooterSpeed.getDouble(0.5);
    System.out.println("Max: " + max);
    if (speed > max) {
      rightMotor.set(TalonFXControlMode.PercentOutput, directionConstant*max*CANDevices.reductionFactor);
    } else {
      rightMotor.set(TalonFXControlMode.PercentOutput, directionConstant*speed*CANDevices.reductionFactor);
    }
  }
}
