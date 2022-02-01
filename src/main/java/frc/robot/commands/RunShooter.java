// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase {
  /** Creates a new RunShooter. */
  private final Shooter m_shooter;

  public RunShooter(Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double shooterSpeed = 0.0;

    if(frc.robot.RobotContainer.m_joystick.getRawButton(1)) {//button a
      shooterSpeed=0.4;
      m_shooter.setShooterMotor(shooterSpeed);
    }
    else {
      shooterSpeed=0.0;
      m_shooter.setShooterMotor(shooterSpeed);
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