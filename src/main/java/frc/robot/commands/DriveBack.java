// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.autonomous.AutoTrajectories;
import frc.robot.commands.drivetrain.FollowTrajectory;
import frc.robot.commands.drivetrain.QuickTurn;
import frc.robot.Constants.HoodConstants;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederWheel;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

import frc.robot.commands.RunShooter;
import frc.robot.commands.RunFeeder;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveBack extends SequentialCommandGroup  {
  /** Creates a new DriveBack. */
  public DriveBack(DriveSubsystem drive, Shooter shooter, Limelight limelight, FeederWheel feeder) {
    // Use addRequirements() here to declare subsystem dependencies.
    addCommands(
      new ParallelCommandGroup(
        new RunShooter(shooter, limelight, true, true).withTimeout(1),
        new HoodAdjust(shooter, HoodConstants.high)
      ),
      new ParallelCommandGroup(
        new RunShooter(shooter, limelight, true, true).withTimeout(3),
        new RunFeeder(feeder, true, true).withTimeout(3)
      ),
      new FollowTrajectory(drive, AutoTrajectories.backUp)
    );
    
  }

  // // Called when the command is initially scheduled.
  // @Override
  // public void initialize() {
  //   System.out.println("driving back");
  // }

  // // Called every time the scheduler runs while the command is scheduled.
  // @Override
  // public void execute() {}

  // // Called once the command ends or is interrupted.
  // @Override
  // public void end(boolean interrupted) {
  //   System.out.println("end driving");

  // }

  // // Returns true when the command should end.
  // @Override
  // public boolean isFinished() {
  //   return false;
  // }
}
