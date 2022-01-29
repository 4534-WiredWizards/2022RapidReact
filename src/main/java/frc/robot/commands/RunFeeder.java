// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.InputDevices;
import frc.robot.subsystems.FeederWheel;

public class RunFeeder extends CommandBase {
  /** Creates a new RunShooter. */
  private final FeederWheel m_feeder;

  public RunFeeder(FeederWheel feeder) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_feeder = feeder;
    addRequirements(m_feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double feederSpeed = 0.0;

    if(frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_rightBumper)) {//button a
      feederSpeed=0.4;
      m_feeder.setFeederMotor(feederSpeed);
    }
    else if (frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_leftBumper)) {
      feederSpeed=-0.4;
      m_feeder.setFeederMotor(feederSpeed);
    } else {
      feederSpeed = 0;
      m_feeder.setFeederMotor(feederSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
