// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

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

public class CenterDriveBack extends SequentialCommandGroup {
  /** Creates a new CenterDriveBack. */
  public CenterDriveBack(DriveSubsystem drive, Shooter shooter, Intake intake, FeederWheel feeder, Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    System.out.println("Please work ----------------------------------");
    addCommands(
      new AutoActuateIntake(intake, AutoConstants.leftIntake).withTimeout(1.5),
       new ParallelRaceGroup(
        new HoodAdjust(shooter, HoodConstants.far),
        new AutoRunIntake(intake, AutoConstants.leftIntake),
        new FollowTrajectory(drive, AutoTrajectories.backUp, true),
        new RunFeeder(feeder, true, true)
        ),
      new ParallelCommandGroup(
         new HoodAdjust(shooter, HoodConstants.far),
         new QuickTurn(drive, Math.toRadians(-90))
      ),
      new ParallelRaceGroup(
        new ShootBall(shooter, limelight, feeder),
        new AutoRunIntake(intake, AutoConstants.leftIntake)
       )
       
     );
  }
}
