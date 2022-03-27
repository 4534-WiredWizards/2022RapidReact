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
  boolean goingUp;
  double position;
  double location;
  
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
      location = HoodConstants.lowPosition;
    }
    else if (position == HoodConstants.high) {
      location = HoodConstants.highPosition;
    }
    else if (position == HoodConstants.far) {
      location = HoodConstants.farPosition;
    }
    else if (position == HoodConstants.veryfar) {
      //m_shooter.setHoodPosition(0.5);
      location = HoodConstants.veryfarPosition;
      //location = m_shooter.getSliderPosition();
    }

    m_shooter.setCurrentPosition(location);

    if (m_shooter.getHood() > location*100) {
      goingUp = false;
    } else {
      goingUp = true;
    }
  }
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if (position == HoodConstants.high) {
    //   m_shooter.raiseHood();
    // }
    // else if (position == HoodConstants.low) {
    //   m_shooter.lowerHood();
    // }


    if (goingUp) {
      m_shooter.raiseHood();
    }
    else {
      m_shooter.lowerHood();
    } 
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setHoodSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (goingUp) {
      if(m_shooter.getHood() >= location*100) {
        return true;
      }
    } 
    else{
      if (m_shooter.getHood() <= location*100) {
        return true;
      }
    }
    return false;


    // if (frc.robot.RobotContainer.m_joystick.getPOV() != 0 && frc.robot.RobotContainer.m_joystick.getPOV() != 180) {
    //   return true;
    // }
    // else {
    //   return false;
    // }
  }
}
