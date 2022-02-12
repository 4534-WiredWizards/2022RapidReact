// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.InputDevices;
import frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Map;

public class RunShooter extends CommandBase {
  /** Creates a new RunShooter. */
  private final Shooter m_shooter;
  private double shooterSpeed = 1.0;

  public RunShooter(Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    addRequirements(m_shooter);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("RunShooter init");
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
    System.out.println("RunShooter end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if x is no longer being pressed, return true
    if (!frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_x)) {
      return true;
    }
    return false;
  }
}
