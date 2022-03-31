// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.HoodConstants;
import frc.robot.autonomous.AutoTrajectories;
import frc.robot.commands.drivetrain.FollowTrajectory;
import frc.robot.commands.drivetrain.QuickTurn;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederWheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ThreeBallSimple extends SequentialCommandGroup {

  /** Creates a new ThreeballSimple. */
  public ThreeBallSimple(DriveSubsystem drive,Shooter shooter,Intake intake,FeederWheel feeder, Limelight limelight) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
      
    addCommands(
      new AutoActuateIntake(intake, AutoConstants.rightIntake).withTimeout(1.5),
      new ParallelRaceGroup(
        new AutoRunIntake(intake, AutoConstants.rightIntake),
        new FollowTrajectory(drive, AutoTrajectories.backUp, true),
        new RunFeeder(feeder, true, true)
        ),
        new AutoActuateIntake(intake, AutoConstants.rightIntake).withTimeout(1.5),
        new HoodAdjust(shooter, HoodConstants.far),
        new ShootBall(shooter, limelight, feeder),
        new AutoActuateIntake(intake, AutoConstants.leftIntake).withTimeout(1.5),
        new ParallelRaceGroup(
        new AutoRunIntake(intake, AutoConstants.leftIntake),
        new FollowTrajectory(drive, AutoTrajectories.position2, true),
        new RunFeeder(feeder, true, true)
        ),
        new AutoActuateIntake(intake, AutoConstants.leftIntake).withTimeout(1.5),
        new ShootBall(shooter, limelight, feeder)
        );



  }
}
