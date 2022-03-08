package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autonomous.AutoTrajectories;
import frc.robot.commands.ChooseAuto;

public class Robot extends TimedRobot {
    
    private RobotContainer robotContainer;

    private Command autonomousCommand;

    @Override
    public void robotInit() {

        robotContainer = new RobotContainer();
        NetworkTableInstance.getDefault().setUpdateRate(.01);
        //Added to speed up auto running
        new AutoTrajectories();
        CameraServer.startAutomaticCapture();
        robotContainer.t_pneumatics.setCompressor(true);
        //new ChooseAuto();

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
