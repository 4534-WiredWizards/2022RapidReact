// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.autonomous.AutoTrajectories;
import frc.robot.commands.drivetrain.FollowTrajectory;
import frc.robot.commands.drivetrain.QuickTurn;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederWheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ThreeballSimple extends SequentialCommandGroup {

  /** Creates a new ThreeballSimple. */
  public ThreeballSimple(DriveSubsystem drive,Shooter shooter,Intake intake,FeederWheel feeder) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
      double shootingAngle = AutoConstants.positionOneShootingAngle;
    addCommands(

      //FIX ME : this line does not work in a sequence 
      //drive.resetPose(AutoTrajectories.testTrajectory.getInitialPose()),
      new ParallelCommandGroup(
        //new drive.drive(0,0,0.5,true),
        new QuickTurn(drive,Math.toRadians(shootingAngle))
        //new RunShooter(shooter,true).withTimeout(0.5)
      ),
      new ShootBall(shooter,feeder).withTimeout(1)
    //  new ParallelCommandGroup(
    //     new ActuateLeftIntake(),  //command still needs to be added
    //     new FollowTrajectory(drive, AutoTrajectories.point_S),
    //     new RunLeftIntake()
    //   ),
    //   new ParallelCommandGroup(
    //     new ActuateRightIntake(), //command still needs to be added
    //     new FollowTrajectory(drive, AutoTrajectories.point_3),
    //     new RunRightIntake()
    //   ),
    //   new FollowTrajectory(drive, AutoTrajectories.point_X),
    //   new ShootBall(shooter, feeder).withTimeout(1),
    //   );
    );
  }
}
