// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.Intake;

public class AutoActuateIntake extends CommandBase {
  /** Creates a new AutoActuateIntake. */

  private final Intake m_intake;
  private final int m_side;
  public AutoActuateIntake(Intake intake, int side) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    m_side = side;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (m_side == AutoConstants.leftIntake) {
      m_intake.setLeftPiston(!m_intake.getLeftPiston());
    } else if (m_side == AutoConstants.rightIntake) {
      m_intake.setRightPiston(!m_intake.getRightPiston());
    } else if (m_side == AutoConstants.centerIntake) {
      m_intake.setCenterPiston(!m_intake.getCenterPiston());
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
