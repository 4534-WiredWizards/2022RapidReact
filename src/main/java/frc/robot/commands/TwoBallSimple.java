// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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


public class TwoBallSimple extends SequentialCommandGroup{
  /** Creates a new TwoBallSimple. */
  public TwoBallSimple(DriveSubsystem drive,Shooter shooter,Intake intake,FeederWheel feeder, Limelight limelight) {
    // Use addRequirements() here to declare subsystem dependencies.
    double shootingAngle = AutoConstants.positionOneShootingAngle;
    addCommands(
      new QuickTurn(drive,Math.toRadians(shootingAngle)),
      new HoodAdjust(shooter, HoodConstants.high), 
      new ParallelCommandGroup(
        new RunShooter(shooter, limelight).withTimeout(1.5),
        new WaitCommand(1),
        new RunFeeder(feeder, true)
      ),
      new AutoActuateIntake(intake, AutoConstants.leftIntake),
      new ParallelCommandGroup(
        new FollowTrajectory(drive, AutoTrajectories.point_S, false),
        new AutoRunIntake(intake, AutoConstants.leftIntake).withTimeout(1.5)
      ),
      new AutoActuateIntake(intake, AutoConstants.leftIntake),
      new FollowTrajectory(drive, AutoTrajectories.point_3, true),
      new HoodAdjust(shooter, HoodConstants.far),
      new ParallelCommandGroup(
        new RunShooter(shooter, limelight).withTimeout(1.5),
        new WaitCommand(1),
        new RunFeeder(feeder, true)
      )
    );
  }

}
