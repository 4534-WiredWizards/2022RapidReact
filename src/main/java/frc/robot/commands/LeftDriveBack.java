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

public class LeftDriveBack extends SequentialCommandGroup {
  /** Creates a new LeftDriveBack. */
  public LeftDriveBack(DriveSubsystem drive,Shooter shooter,Intake intake,FeederWheel feeder, Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    addCommands(
      new AutoActuateIntake(intake, AutoConstants.leftIntake).withTimeout(1.5),
       new ParallelRaceGroup(
        //new HoodAdjust(shooter, HoodConstants.far),
        new AutoRunIntake(intake, AutoConstants.leftIntake),
        new FollowTrajectory(drive, AutoTrajectories.leftBackUp, true)
        //new RunFeeder(feeder, true, true)
        ),
      new AutoRunIntake(intake, AutoConstants.leftIntake).withTimeout(0.5),
      new AutoActuateIntake(intake, AutoConstants.leftIntake).withTimeout(1),
      new ParallelCommandGroup(
         new HoodAdjust(shooter, HoodConstants.far)
    
      ),
      new ShootBall(shooter, limelight, feeder)
        );

  }

}

  //we are  cool (sunhye and sam)