// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.InputDevices;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase {
  /** Creates a new RunShooter. */
  private final Shooter m_shooter;
  private final Limelight m_limelight;
  private boolean m_isAuto;
  private double shooterSpeed = 1.0;

  public RunShooter(Shooter shooter, Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_limelight = limelight;
    addRequirements(m_shooter);
    addRequirements(m_limelight);

  }
  public RunShooter(Shooter shooter, Limelight limelight, boolean isAuto) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_limelight = limelight;
    m_isAuto = isAuto;
    addRequirements(m_shooter);

  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_limelight.setPipeline(2);
    m_limelight.setLEDMode(3);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // grabs speed from shuffleboard with 0.5 as default

    m_shooter.setShooterMotor(shooterSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // if finished or interrupted, set speed to 0
    m_shooter.setShooterMotor(0);
    m_limelight.setPipeline(0);
    m_limelight.setLEDMode(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if x is no longer being pressed, return true
    if ((!frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_x)) && !m_isAuto){
      return true;
    }
    return false;
  }
}
