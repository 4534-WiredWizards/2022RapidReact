// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.HoodConstants;
import frc.robot.subsystems.FeederWheel;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;

public class ShootBall extends SequentialCommandGroup {
  /** Creates a new ShootBall. */
  private final Shooter m_shooter;
  private final FeederWheel m_feeder;
  private final Limelight m_limelight;

  public ShootBall(Shooter shooter,Limelight limelilght, FeederWheel feeder) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_feeder = feeder;
    m_limelight = limelilght;
    addRequirements(m_shooter);
    addRequirements(m_feeder);
    addRequirements(m_limelight);

    addCommands(
      // new ParallelCommandGroup (
      //   new RunFeeder(m_feeder, false, true).withTimeout(0.5),
      //   new RunShooter(m_shooter, m_limelight, false, true).withTimeout(0.5)
      // ),
      new RunShooter(m_shooter, m_limelight, true, true).withTimeout(1),
      new ParallelCommandGroup(
        new RunShooter(m_shooter, m_limelight, true, true).withTimeout(6),
        new RunFeeder(m_feeder, true, true).withTimeout(6)
      )
    );

  }
}
