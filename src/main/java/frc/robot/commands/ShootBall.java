// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.FeederWheel;
import frc.robot.subsystems.Shooter;

public class ShootBall extends CommandBase {
  /** Creates a new ShootBall. */
  private double shooterSpeed = 1.0;
  private double feederSpeed = 1.0;
  private final Shooter m_shooter;
  private final FeederWheel m_feeder;
  public ShootBall(Shooter shooter,FeederWheel feeder) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_feeder = feeder;
    addRequirements(m_shooter);
    addRequirements(m_feeder);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.setShooterMotor(shooterSpeed);
    m_feeder.setFeederMotor(feederSpeed, true, true);

    if (m_shooter.getShooterMotor() < 0.1) {

    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setShooterMotor(0);
    System.out.println("ShootBall end");
    m_feeder.setFeederMotor(0, true, true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    //FIX ME : add prox switch to stop
    return false;
  }
}
