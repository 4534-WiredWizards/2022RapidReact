// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.HoodConstants;
import frc.robot.subsystems.Shooter;

public class HoodAdjust extends CommandBase {

  double hoodOutput = 0.0;
  Shooter m_shooter;
  boolean m_isUp = true;
  double position;
  
  /** Creates a new HoodAdjust. */
  public HoodAdjust(Shooter shooter, double pos) {
    m_shooter = shooter;
    position = pos;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (position == HoodConstants.low){
      m_shooter.setLowPosition();
    }
    else if (position == HoodConstants.high) {
      m_shooter.setHighPosition();
    }
    else if (position == HoodConstants.far) {
      m_shooter.setFarPosition();
    }
    else if (position == HoodConstants.veryfar) {
      //m_shooter.setHoodPosition(0.5);
      m_shooter.setVeryFarPosition();
    }
  }
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /*
    if (position == HoodConstants.high) {
      m_shooter.raiseHood();
    }
    else if (position == HoodConstants.low) {
      m_shooter.lowerHood();
    }
    */
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("HoodAdjust end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
