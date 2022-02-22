// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class HoodAdjust extends CommandBase {

  double hoodOutput = 0.0;
  Shooter m_shooter;
  boolean m_isUp = true;
  
  /** Creates a new HoodAdjust. */
  public HoodAdjust(Shooter shooter, boolean isUp) {
    m_shooter = shooter;
    m_isUp = isUp;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //m_shooter.raiseHood();
    if (m_isUp == true){
      m_shooter.raiseHood();
    }
    else {
      m_shooter.lowerHood();
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("HoodAdjust end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
