// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.InputDevices;
import frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase {
  /** Creates a new RunIntake. */
  private final Intake m_intake;

  public RunIntake(Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double intakespeed = 0.0;
    if (frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_b)) {
      m_intake.setCenterPiston(!m_intake.getCenterPiston());
    }

    if (frc.robot.RobotContainer.m_joystick.getRawAxis(2) > 0.1 && frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_b)) {
      m_intake.setLeftPiston(!m_intake.getLeftPiston());
    }

    if (frc.robot.RobotContainer.m_joystick.getRawAxis(3) > 0.1 && frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_b)) {
      m_intake.setRightPiston(!m_intake.getLeftPiston());
    } 


    if(frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_a)) {
        intakespeed=-0.4;
        m_intake.setCenterMotor(intakespeed);
    } else {
      if(frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_y)) {
        intakespeed=0.4;
        m_intake.setCenterMotor(intakespeed);
      } else {
        intakespeed=0;
        m_intake.setCenterMotor(intakespeed);
      }
    }

    if(frc.robot.RobotContainer.m_joystick.getRawAxis(InputDevices.btn_leftTrigger) > 0.1 && frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_a)) {
      intakespeed=-0.4;
      m_intake.setLeftMotor(intakespeed);
  } else {
    if(frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_y)) {
      intakespeed=0.4;
      m_intake.setLeftMotor(intakespeed);
    } else {
      intakespeed=0;
      m_intake.setLeftMotor(intakespeed);
    }
  }

  if(frc.robot.RobotContainer.m_joystick.getRawAxis(InputDevices.btn_rightTrigger) > 0.1 && frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_a)) {
    intakespeed=-0.4;
    m_intake.setRightMotor(intakespeed);
} else {
  if(frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_y)) {
    intakespeed=0.4;
    m_intake.setRightMotor(intakespeed);
  } else {
    intakespeed=0;
    m_intake.setRightMotor(intakespeed);
  }
}





  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
