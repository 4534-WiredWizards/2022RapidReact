// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.InputDevices;
import frc.robot.subsystems.Intake;

public class ActuateIntake extends CommandBase {
  /** Creates a new ActuateIntake. */
  private final Intake m_intake;
  public ActuateIntake(Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("ActuateIntake init");
    if(frc.robot.RobotContainer.m_joystick.getRawAxis(InputDevices.btn_leftTrigger) > 0.1) {
      m_intake.setLeftPiston(!m_intake.getLeftPiston());
    } else if(frc.robot.RobotContainer.m_joystick.getRawAxis(InputDevices.btn_rightTrigger) > 0.1) {
      m_intake.setRightPiston(!m_intake.getRightPiston());
    } else {
      m_intake.setCenterPiston(!m_intake.getCenterPiston());
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("ActuateIntake end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
