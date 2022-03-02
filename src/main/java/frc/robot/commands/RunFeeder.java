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
  private final boolean m_runForward;
  private double feederSpeed = 1.0;

  public RunFeeder(FeederWheel feeder, boolean runFoward) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_feeder = feeder;
    m_runForward = runFoward;
    addRequirements(m_feeder);

    // adds feeder wheel speed as a slider to shuffleboard
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // grabs feeder wheel speed from shuffleboard with 0.4 as default

    // if runfoward is true, run feeder forward at feeder speed
    // else run feeder backwards at feeder speed
    if (frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_x)) {
      if(m_runForward) {
        m_feeder.setFeederMotor(feederSpeed, true, true);
      } else {
        m_feeder.setFeederMotor(feederSpeed, false, true);
      }
    } else {
      if(m_runForward) {
        m_feeder.setFeederMotor(feederSpeed, true, false);
      } else {
        m_feeder.setFeederMotor(feederSpeed, false, false);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // if finished or interrupted, set feeder wheel speed to 0
    m_feeder.setFeederMotor(0, true, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if left and right bumper are not held, return true
    if (!frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_leftBumper) && !frc.robot.RobotContainer.m_joystick.getRawButton(InputDevices.btn_rightBumper)) {
      return true;
    }
    return false;
  }
}
