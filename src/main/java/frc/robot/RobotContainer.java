package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.InputDevices;
import frc.robot.autonomous.AutoTrajectories;
//import frc.robot.commands.drivetrain.QuickTurn
import frc.robot.commands.drivetrain.FollowTrajectory;
import frc.robot.commands.drivetrain.OperatorControl;
//import frc.robot.commands.superstructure.Indexing.Waiting;
//import frc.robot.commands.superstructure.shooting.RampUpWithVision;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooter;

public class RobotContainer {

    /**
     * Establishes the controls and subsystems of the robot
     */

 
    public static XboxController m_joystick = new XboxController(1);

    SendableChooser<Command> chooser = new SendableChooser<>();

    //chooser.simpleDrive("Simple Drive", simpleDrive)

    public DriveSubsystem drive = new DriveSubsystem();
    public Intake t_intake = new Intake();
    public Shooter t_shooter = new Shooter();
    
    public RobotContainer() {
        //callibrates joysticks
        drive.setDefaultCommand(
            new OperatorControl(
                drive, 
                () -> m_joystick.getLeftY(), 
                () -> m_joystick.getLeftX(), 
        //NOTE: Greg's Right X controller is on Axis 2
                () -> m_joystick.getRawAxis(4),
                true
            )
        );

        /*
        shooter.setDefaultCommand(
            new RunCommand(() -> shooter.runShooterPercent(gamepad.getRawAxis(3) / 5), shooter)
        );
        */

        t_intake.setDefaultCommand(new RunIntake(t_intake));
        t_shooter.setDefaultCommand(new RunShooter(t_shooter));


        configureButtonBindings();

    }

    public void updateShuffleBoard() {

        SwerveModuleState[] tempStates; 

        SmartDashboard.putNumber("Left  Y Joy", m_joystick.getLeftY());
        SmartDashboard.putNumber("Left  X Joy", m_joystick.getLeftX());
        //NOTE: Greg's Right X controller is on Axis 2
        SmartDashboard.putNumber("Right X Joy", m_joystick.getRawAxis(2));

        tempStates = drive.getModuleStates();
        SmartDashboard.putNumber("CANcoder S7 FL", tempStates[2].angle.getDegrees());
        SmartDashboard.putNumber("CANcoder S1 FR", tempStates[0].angle.getDegrees());
        SmartDashboard.putNumber("CANcoder S6 RL", tempStates[3].angle.getDegrees());
        SmartDashboard.putNumber("CANcoder S3 RR", tempStates[1].angle.getDegrees());
        SmartDashboard.putNumber("CANcoder D8 FL", tempStates[2].speedMetersPerSecond);
        SmartDashboard.putNumber("CANcoder D2 FR", tempStates[0].speedMetersPerSecond);
        SmartDashboard.putNumber("CANcoder D5 RL", tempStates[3].speedMetersPerSecond);
        SmartDashboard.putNumber("CANcoder D4 RR", tempStates[1].speedMetersPerSecond);
    }

    public void configureButtonBindings() {
        /*
        // ramp up shooter using vision
        new JoystickButton(gamepad, Button.kBumperRight.value)
            .whenPressed(new RampUpWithVision(shooter, limelight));
        */
        // new JoystickButton(GMDJoystick, 4) //XboxController.Button.kY
        //     .whileHeld(new resetGyro(drive));
            //!!!!!!!!!!!!!!!!!!!!fix this !!!!!!!!!!!!!!!!!!!
            //joystick button does not like xbox controller!!!!!!!!!!!!!!!!!!!!!!!
    

        // // align with vision
        // new JoystickButton(GMDJoystick, Joystick.ButtonType.kTop.value)
        //     .whileHeld(new AlignWithTargetVision(drive, limelight));

        // // align with 0 degrees
        // new JoystickButton(GMDJoystick, Joystick.ButtonType.kTrigger.value)
        //     .whileHeld(new QuickTurn(drive, Math.PI));

        // // align with 180 degrees
        // new JoystickButton(GMDJoystick, Joystick.ButtonType.kTrigger.value) 
        //     .whileHeld(new QuickTurn(drive, 0));

        // // reset imu 
        // new JoystickButton(GMDJoystick, 3)
        //     .whenPressed(new InstantCommand(drive::resetImu));

        // toggle hood
        /*new JoystickButton(rightJoystick, Joystick.ButtonType.kTop.value)
            .whenPressed(new InstantCommand(shooter::toggleHood));
        */
      final JoystickButton a = new JoystickButton(m_joystick, 1);
      final JoystickButton b = new JoystickButton(m_joystick, 2);
      final JoystickButton x = new JoystickButton(m_joystick, 3);
      final JoystickButton leftBumper = new JoystickButton(m_joystick, 4);

      //a.whenPressed(new GoToColor());
      //b.whenPressed(new SpinTimes());
      //x.whenPressed(new AutoTest());

    }

    public Command getAutonomousCommand() {

        SmartDashboard.putNumber("Initialized", 1);
        drive.resetPose(AutoTrajectories.testTrajectory.getInitialPose());
        return new FollowTrajectory(drive, AutoTrajectories.practiceTrajectory);
        //return new simpleDrive(); //ChooseAuto().autoChooser.getSelected(); 

    }

    // public Command getTestCommand() {
    //     return new ChooseMotor().motorChooser.getSelected();
    // }


}
