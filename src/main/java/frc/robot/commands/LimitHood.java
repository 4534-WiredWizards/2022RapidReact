// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class LimitHood extends CommandBase {
  /** Creates a new LimitHood. */
  Shooter m_shooter; 
  public LimitHood(Shooter shooter) {
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
    m_shooter.lowerHood();
  }
    

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setHoodSpeed(0);
    m_shooter.setHood(0);
    m_shooter.setCurrentPosition(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!m_shooter.getHoodSensor()) {
      return true;
    }
    return false;
  }
}
