// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.InputDevices;
import frc.robot.subsystems.Intake;

public class ActuateIntake extends CommandBase {
  /** Creates a new ActuateIntake. */
  private final Intake m_intake;
  private final int m_autoIntake;

  public ActuateIntake(Intake intake, int autoIntake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    m_autoIntake = autoIntake;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(frc.robot.RobotContainer.m_joystick.getRawAxis(InputDevices.btn_leftTrigger) > 0.1 || m_autoIntake == AutoConstants.leftIntake) {
      m_intake.setLeftPiston(!m_intake.getLeftPiston());
      m_intake.setCenterPiston(false);
      m_intake.setRightPiston(false);
    } else if(frc.robot.RobotContainer.m_joystick.getRawAxis(InputDevices.btn_rightTrigger) > 0.1 || m_autoIntake == AutoConstants.rightIntake) {
      m_intake.setRightPiston(!m_intake.getRightPiston());
      m_intake.setLeftPiston(false);
      m_intake.setCenterPiston(false);
    } else {
      m_intake.setCenterPiston(!m_intake.getCenterPiston());
      m_intake.setLeftPiston(false);
      m_intake.setRightPiston(false);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
