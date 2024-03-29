package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autonomous.AutoTrajectories;
import frc.robot.commands.ChooseAuto;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.TwoBallSimple;
import frc.robot.commands.ThreeBallSimple;
import frc.robot.commands.DriveBack;
import frc.robot.commands.CenterDriveBack;
import frc.robot.commands.LeftDriveBack;
import frc.robot.commands.RightDriveBack;


public class Robot extends TimedRobot {
    
    private RobotContainer robotContainer;

    private Command autonomousCommand;

    //private ChooseAuto autoChooser = new ChooseAuto();
  String trajectoryJSON = "paths/Sam.wpilib.json";
  Trajectory trajectory = new Trajectory();
  private Command TwoBallSimple;
  private Command ThreeBallSimple;
  private Command DriveBack;
  private Command CenterDriveBack;
  private Command LeftDriveBack;
  private Command RightDriveBack;
  public SendableChooser<Command> autoChooser;
  
    @Override
    public void robotInit() {

        robotContainer = new RobotContainer();
        TwoBallSimple = new TwoBallSimple(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_intake, robotContainer.t_feeder, robotContainer.t_limelight);
        ThreeBallSimple = new ThreeBallSimple(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_intake, robotContainer.t_feeder, robotContainer.t_limelight);
        DriveBack = new DriveBack(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_limelight, robotContainer.t_feeder);
        CenterDriveBack = new CenterDriveBack(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_intake,robotContainer.t_feeder, robotContainer.t_limelight);
        LeftDriveBack = new LeftDriveBack(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_intake, robotContainer.t_feeder, robotContainer.t_limelight);
        RightDriveBack = new RightDriveBack(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_intake, robotContainer.t_feeder, robotContainer.t_limelight);

        autoChooser = new SendableChooser<Command>();
        NetworkTableInstance.getDefault().setUpdateRate(.01);
        //Added to speed up auto running
        new AutoTrajectories();
        UsbCamera fisheye = CameraServer.startAutomaticCapture();
        fisheye.setResolution(320, 240);
        fisheye.setPixelFormat(PixelFormat.kMJPEG);
        robotContainer.t_pneumatics.setCompressor(true);
        autoChooser.setDefaultOption("ThreeBallSimple", ThreeBallSimple);
        autoChooser.addOption("TwoBallSimple", TwoBallSimple);
        autoChooser.addOption("DriveBack", DriveBack);
        autoChooser.addOption("CenterDriveBack", CenterDriveBack);
        autoChooser.addOption("LeftDriveBack", LeftDriveBack);
        autoChooser.addOption("RightDriveBack", RightDriveBack);
        SmartDashboard.putData("Auto Routines", autoChooser);
        robotContainer.getTrajectories();

        
   }


    

    @Override
    public void robotPeriodic() {   

        CommandScheduler.getInstance().run();
        robotContainer.drive.updateSmartDashboard();
        robotContainer.t_pneumatics.updatePressurSensor();
        robotContainer.t_shooter.updateShooter();
        robotContainer.t_feeder.updateSmartDashboard();
        robotContainer.t_limelight.updateLimelightInfo();
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {
        
        autonomousCommand = robotContainer.getTrajectories(); //robotContainer.getAutonomousCommand(); //autoChooser.getSelected();

        if (autonomousCommand != null) autonomousCommand.schedule();


    }

    @Override
    public void teleopInit() {

        if (autonomousCommand != null) {autonomousCommand.cancel();}

    }

    // @Override
    // public void testInit() {
    //     robotContainer.getTestCommand();
    // }

    @Override
    public void testPeriodic() {
        
    }

}
