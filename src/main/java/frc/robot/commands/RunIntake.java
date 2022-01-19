// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

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

    if(frc.robot.RobotContainer.m_joystick.getRawButton(2)) {
      intakespeed=-0.6;
      m_intake.setLeftMotor(intakespeed); //-0.6
    }
    else {
      if(frc.robot.RobotContainer.m_joystick.getRawButton(3)) {
        intakespeed=0.4;
        m_intake.setLeftMotor(intakespeed);
      }
      else {
        intakespeed=0;
        m_intake.setLeftMotor(intakespeed);
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
