// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.Intake;

public class AutoRunIntake extends CommandBase {
  /** Creates a new AutoRunIntake. */

  private final Intake m_intake;
  private final int m_side;

  public AutoRunIntake(Intake intake, int side) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    m_side = side;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_side == AutoConstants.leftIntake) {
      m_intake.setLeftMotor(0.5, true);
    } else if (m_side == AutoConstants.rightIntake) {
      m_intake.setRightMotor(0.5, true);
    } else {
      m_intake.setCenterMotor(0.5, true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.setLeftMotor(0, true);
    m_intake.setRightMotor(0, true);
    m_intake.setCenterMotor(0, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
