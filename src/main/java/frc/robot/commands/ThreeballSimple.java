// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.autonomous.AutoTrajectories;
// import frc.robot.commands.drivetrain.FollowTrajectory;

// // NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// // information, see:
// // https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
// public class ThreeballSimple extends SequentialCommandGroup {
//   /** Creates a new ThreeballSimple. */
//   public ThreeballSimple() {
//     // Add your commands in the addCommands() call, e.g.
//     // addCommands(new FooCommand(), new BarCommand());
      
//     addCommands(
//       drive.resetPose(AutoTrajectories.testTrajectory.getInitialPose()),
//       new ParallelCommandGroup(
//         new drive.drive(0,0,0.5,true),
//         new setShooterMotor(1.0)
//       ),
//       new shootBall(),
//       new ParallelCommandGroup(
//         new FollowTrajectory(drive, AutoTrajectories.point_S),
//         new runIntakeLeft()
//       ),
//       new ParallelCommandGroup(
//         new FollowTrajectory(drive, AutoTrajectories.point_3),
//         new runIntakeRight()
//       ),
//       new FollowTrajectory(drive, AutoTrajectories.point_X),
//       new shootBall(),
//       );
//     );
//   }
// }
