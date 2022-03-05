// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbMotor;
import frc.robot.Constants.fancyJoystick;


public class ControlArmMotor extends CommandBase {
  /** Creates a new ControlLeftArm. */
  private final ClimbMotor m_climbMotor;
  private double leftClimbSpeed = 1.0;
  private  boolean isForward;

  public ControlArmMotor(ClimbMotor climbMotor) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climbMotor = climbMotor;
    addRequirements(m_climbMotor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (frc.robot.RobotContainer.m_fancyJoystick.getRawButton(fancyJoystick.r2)) {
       isForward = true;
        m_climbMotor.setLeftClimbMotor(leftClimbSpeed, isForward);
    }

    if (frc.robot.RobotContainer.m_fancyJoystick.getRawButton(fancyJoystick.square)) {
        isForward = false;
        m_climbMotor.setLeftClimbMotor(leftClimbSpeed, isForward);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climbMotor.setLeftClimbMotor(0, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!frc.robot.RobotContainer.m_fancyJoystick.getRawButton(fancyJoystick.r2) && !frc.robot.RobotContainer.m_fancyJoystick.getRawButton(fancyJoystick.square)) {
      return true;
    }
    return false;
}


}
