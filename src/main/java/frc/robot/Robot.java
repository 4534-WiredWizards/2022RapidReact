package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autonomous.AutoTrajectories;
import frc.robot.commands.ChooseAuto;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.TwoBallSimple;
import frc.robot.commands.ThreeBallSimple;
import frc.robot.commands.DriveBack;

public class Robot extends TimedRobot {
    
    private RobotContainer robotContainer;

    private Command autonomousCommand;

    //private ChooseAuto autoChooser = new ChooseAuto();

  private Command TwoBallSimple;
  private Command ThreeBallSimple;
  private Command DriveBack;
  public SendableChooser<Command> autoChooser;
  
    @Override
    public void robotInit() {

        robotContainer = new RobotContainer();
        TwoBallSimple = new TwoBallSimple(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_intake, robotContainer.t_feeder, robotContainer.t_limelight);
        ThreeBallSimple = new ThreeBallSimple(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_intake, robotContainer.t_feeder, robotContainer.t_limelight);
        DriveBack = new DriveBack(robotContainer.drive, robotContainer.t_shooter, robotContainer.t_limelight, robotContainer.t_feeder);
        autoChooser = new SendableChooser<Command>();
        NetworkTableInstance.getDefault().setUpdateRate(.01);
        //Added to speed up auto running
        new AutoTrajectories();
        CameraServer.startAutomaticCapture();
        robotContainer.t_pneumatics.setCompressor(true);
        autoChooser.setDefaultOption("ThreeBallSimple", ThreeBallSimple);
        autoChooser.addOption("TwoBallSimple", TwoBallSimple);
        autoChooser.addOption("DriveBack", DriveBack);
        SmartDashboard.putData("Auto Routines", autoChooser);

    }

    @Override
    public void robotPeriodic() {   

        CommandScheduler.getInstance().run();
        robotContainer.drive.updateSmartDashboard();
        robotContainer.t_pneumatics.updatePressurSensor();
        robotContainer.t_shooter.updateShooter();
    }

    @Override
    public void autonomousInit() {
        
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) autonomousCommand.schedule();

        

    }

    @Override
    public void teleopInit() {

        if (autonomousCommand != null) autonomousCommand.cancel();

    }

    // @Override
    // public void testInit() {
    //     robotContainer.getTestCommand();
    // }

    @Override
    public void testPeriodic() {
        
    }

}
