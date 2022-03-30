// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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
      new ParallelCommandGroup(
        new QuickTurn(drive, Math.toRadians(-25)),
        new RunShooter(shooter, limelight, true).withTimeout(1),
        new HoodAdjust(shooter, HoodConstants.high)
      ),
      new ParallelCommandGroup(
        new RunShooter(shooter, limelight, true).withTimeout(1),
        new RunFeeder(feeder, true, true).withTimeout(1)
      ),
      new QuickTurn(drive, Math.toRadians(-90)),
      new AutoActuateIntake(intake, AutoConstants.rightIntake),
      new ParallelCommandGroup(
        new AutoRunIntake(intake, AutoConstants.rightIntake).withTimeout(2),
        new FollowTrajectory(drive, AutoTrajectories.backUp, true)
        ),
      new AutoActuateIntake(intake, AutoConstants.rightIntake),
      new ParallelCommandGroup(
        new QuickTurn(drive, Math.toRadians(90)),
        new RunShooter(shooter, limelight, true).withTimeout(1),
        new HoodAdjust(shooter, HoodConstants.far)
        ),
      new ParallelCommandGroup(
        new RunShooter(shooter, limelight, true).withTimeout(1),
        new RunFeeder(feeder, true, true).withTimeout(1)
      )
        );

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
