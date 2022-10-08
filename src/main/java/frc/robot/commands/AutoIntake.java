// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Intake;



public class AutoIntake extends CommandBase {
  /** Creates a new AutoIntake. 
 * @param intake */
Intake m_intake;
DriveSubsystem m_drive; 
  public AutoIntake(Intake intake, DriveSubsystem drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    m_drive = drive; 
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double angle = m_drive.relativeDriveDirection();
    if (angle>270||angle<45) {
      m_intake.setCenterPiston(false);
      m_intake.setLeftPiston(false);
      m_intake.setRightPiston(true);
    } 
    else if (angle>135) {
       m_intake.setLeftPiston(true);
       m_intake.setCenterPiston(false);
       m_intake.setRightPiston(false);
    }
    else if (angle>45) {
        m_intake.setCenterPiston(true);
        m_intake.setRightPiston(false);
        m_intake.setLeftPiston(false);
    }
    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.setCenterPiston(false);
    m_intake.setRightPiston(false);
    m_intake.setLeftPiston(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}