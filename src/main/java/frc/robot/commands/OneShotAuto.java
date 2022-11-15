// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.HoodConstants;
import frc.robot.autonomous.AutoTrajectories;
import frc.robot.commands.drivetrain.FollowTrajectory;
import frc.robot.commands.drivetrain.QuickTurn;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederWheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OneShotAuto extends SequentialCommandGroup {
  /** Creates a new OneShotAuto. */
  public OneShotAuto(DriveSubsystem drive,Shooter shooter,Intake intake,FeederWheel feeder, Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    addCommands(
      new HoodAdjust(shooter, HoodConstants.high),
      new ShootBall(shooter, limelight, feeder),
      new FollowTrajectory(drive, AutoTrajectories.leftBackUp, true)
        );
  }

 
}
