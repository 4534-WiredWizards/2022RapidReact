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
  private final boolean m_runForward;
  private double leftIntakeSpeed=1.0;
  private double rightIntakeSpeed=1.0;
  private double centerIntakeSpeed=1.0;

  public ActuateIntake(Intake intake, boolean isForward) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    m_runForward = isForward;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_y) || frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_a)) {
      if(frc.robot.RobotContainer.m_joystick.getRawAxis(InputDevices.btn_leftTrigger) > 0.1) {
        m_intake.setLeftPiston(true);
        m_intake.setCenterPiston(false);
        m_intake.setRightPiston(false);
      } else if(frc.robot.RobotContainer.m_joystick.getRawAxis(InputDevices.btn_rightTrigger) > 0.1) {
        m_intake.setRightPiston(true);
        m_intake.setLeftPiston(false);
        m_intake.setCenterPiston(false);
      } else {
        m_intake.setCenterPiston(true);
        m_intake.setLeftPiston(false);
        m_intake.setRightPiston(false);
      }
    } else {
      m_intake.setCenterPiston(false);
        m_intake.setLeftPiston(false);
        m_intake.setRightPiston(false);
    }

    if (m_runForward) {
      if (m_intake.getLeftPiston()) {
        m_intake.setLeftMotor(leftIntakeSpeed, true); //-1
      } else if (m_intake.getRightPiston()) {
        m_intake.setRightMotor(rightIntakeSpeed, true); //1 
      } else {
        m_intake.setCenterMotor(centerIntakeSpeed, true); //-1
      }
    } 
    
    else {
      // runfoward is false so run intake in reverse
      if (m_intake.getLeftPiston()) {
        m_intake.setLeftMotor(leftIntakeSpeed, false);
      } else if (m_intake.getRightPiston()) {
        m_intake.setRightMotor(rightIntakeSpeed, false);
      } else {
        m_intake.setCenterMotor(centerIntakeSpeed, false);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.setLeftMotor(0, true);
    m_intake.setRightMotor(0, true);
    m_intake.setCenterMotor(0, true);
    
    m_intake.setLeftPiston(false);
    m_intake.setRightPiston(false);
    m_intake.setCenterPiston(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_y);
  }
}
