// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.Constants.CANDevices;


public class ClimbMotor extends SubsystemBase {
  /** Creates a new LeftArm. */
  private CANSparkMax leftMotor;
  //private Solenoid leftPowerSolenoid;
  // private Solenoid leftDirectionSolenoid;
  private double directionConstant;
  private RelativeEncoder climbEncoder;
  // private double maxRotation = 210;
  // private double minRotation = 0;
  
  
  private ShuffleboardTab Tab=Shuffleboard.getTab("ClimbMotors");
  private NetworkTableEntry leftUpSpeed=Tab.add("LeftArmUpSpeed", 0.5)
  .withWidget(BuiltInWidgets.kNumberSlider)
  .withProperties(Map.of("min", 0, "max", 1))
  .getEntry();

  private NetworkTableEntry leftDownSpeed=Tab.add("LeftArmDownSpeed", 0.5)
  .withWidget(BuiltInWidgets.kNumberSlider)
  .withProperties(Map.of("min", 0, "max", 1))
  .getEntry();


  public ClimbMotor() {
    leftMotor = new CANSparkMax(CANDevices.rightClimberMotorId, MotorType.kBrushless);
    leftMotor.setIdleMode(IdleMode.kBrake);

    climbEncoder = leftMotor.getEncoder();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setLeftClimbMotor(double speed, boolean forward) {
    double leftClimbUpSlider = leftUpSpeed.getDouble(1.0);
    double leftClimbDownSlider = leftDownSpeed.getDouble(0.3);

    //boolean enableMax = false;

    if (forward) {
      directionConstant = 1;
      // if (enableMax &&(climbEncoder.getPosition() >= maxRotation)) {
      //   leftMotor.set(0);
      // }
      // else {
        if (speed > leftClimbUpSlider) {
          leftMotor.set(directionConstant*leftClimbUpSlider);
          System.out.println("forward at speed: " + leftClimbUpSlider);
        }
        else {
          leftMotor.set(directionConstant*speed);
          System.out.println("forward at speed: " + speed);
        }
      //}
    }


    else {
      directionConstant = -1;
      // if (climbEncoder.getPosition() <= minRotation) {
      //   leftMotor.set(0);
      // }

      // else {
        if (speed > leftClimbDownSlider) {
          leftMotor.set(directionConstant*leftClimbDownSlider);
          System.out.println("backward at speed: " + leftClimbDownSlider);
        }
        else {
          leftMotor.set(directionConstant*speed);
          System.out.println("backward at speed: " + speed);
        }
      //}
    }
  }

  public double getClimbEncoder() {
    return climbEncoder.getPosition();
  }

  public void showClimbEncoder() {
    SmartDashboard.putNumber("Climb Encoder", climbEncoder.getPosition());
  }
}
