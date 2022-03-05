package frc.robot;

import java.util.Map;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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
import frc.robot.Constants.HoodConstants;
import frc.robot.Constants.InputDevices;
import frc.robot.Constants.fancyJoystick;
import frc.robot.autonomous.AutoTrajectories;
//import frc.robot.commands.drivetrain.QuickTurn
import frc.robot.commands.drivetrain.FollowTrajectory;
import frc.robot.commands.drivetrain.OperatorControl;
//import frc.robot.commands.superstructure.Indexing.Waiting;
//import frc.robot.commands.superstructure.shooting.RampUpWithVision;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederWheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.ClimbMotor;
import frc.robot.subsystems.ClimbPiston;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.commands.ActuateIntake;
import frc.robot.commands.ChooseAuto;
import frc.robot.commands.HoodAdjust;
import frc.robot.commands.Limelight;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.RunFeeder;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooter;
import frc.robot.commands.ThreeBallSimple;
import frc.robot.commands.TwoBallSimple;
import frc.robot.commands.ControlArmMotor;
import frc.robot.commands.ControlArmPiston;
import frc.robot.commands.DisconnectGyro;
import frc.robot.Constants.fancyJoystick;
import frc.robot.Constants.InputDevices;

public class RobotContainer {

    /**
     * Establishes the controls and subsystems of the robot
     */

 
    public static XboxController m_joystick = new XboxController(1);
    public static XboxController m_fancyJoystick = new XboxController(0);

    SendableChooser<Command> chooser = new SendableChooser<>();

    //chooser.simpleDrive("Simple Drive", simpleDrive)

    public DriveSubsystem drive = new DriveSubsystem();
    public Intake t_intake = new Intake();
    public Shooter t_shooter = new Shooter();
    public FeederWheel t_feeder = new FeederWheel();
    public Pneumatics t_pneumatics = new Pneumatics();
    public ClimbMotor t_climbMotor = new ClimbMotor();
    public ClimbPiston t_ClimbPiston = new ClimbPiston();
    public Limelight t_limelight = new Limelight();
    
    public RobotContainer() {
        //callibrates joysticks
        drive.setDefaultCommand(
            new OperatorControl(
                drive, 
                () -> m_fancyJoystick.getLeftY(), 
                () -> m_fancyJoystick.getLeftX(), 
                () -> m_fancyJoystick.getRawAxis(3), //4
                () -> m_fancyJoystick.getRawAxis(2),
                true
            )
        );

        /*
        shooter.setDefaultCommand(
            new RunCommand(() -> shooter.runShooterPercent(gamepad.getRawAxis(3) / 5), shooter)
        );
        */
        

        Shuffleboard.getTab("Shooter")
    .add("FeederSpeed", 0)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 1))
    .getEntry();

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

        new JoystickButton(m_fancyJoystick, fancyJoystick.l1).whenPressed(new ResetGyro(drive));
        //new JoystickButton(m_fancyJoystick, fancyJoystick.r1).whenPressed(new DisconnectGyro(drive));

        new JoystickButton(m_joystick, InputDevices.btn_a).whileHeld(new RunIntake(t_intake, false));
        new JoystickButton(m_joystick, InputDevices.btn_y).whileHeld(new RunIntake(t_intake, true));

        new JoystickButton(m_joystick, InputDevices.btn_b).whenPressed(new ActuateIntake(t_intake, -1));

        new JoystickButton(m_joystick, InputDevices.btn_leftBumper).whileHeld(new RunFeeder(t_feeder, false));
        new JoystickButton(m_joystick, InputDevices.btn_rightBumper).whileHeld(new RunFeeder(t_feeder, true));

        new JoystickButton(m_joystick, InputDevices.btn_x).whileHeld(new RunShooter(t_shooter, t_limelight, false));
        
        new POVButton(m_joystick, 0).whenPressed(new HoodAdjust(t_shooter, HoodConstants.high));  //POV up
        new POVButton(m_joystick, 90).whenPressed(new HoodAdjust(t_shooter, HoodConstants.far));  //POV right
        new POVButton(m_joystick, 180).whenPressed(new HoodAdjust(t_shooter, HoodConstants.low)); //POV down

        //new POVButton(m_joystick, 0).whileHeld(new HoodAdjust(t_shooter, HoodConstants.high));  //POV up
        //new POVButton(m_joystick, 180).whileHeld(new HoodAdjust(t_shooter, HoodConstants.low)); //POV down

        //CLIMB
        new JoystickButton(m_fancyJoystick, fancyJoystick.r2).whileHeld(new ControlArmMotor(t_climbMotor));
        new JoystickButton(m_fancyJoystick, fancyJoystick.square).whileHeld(new ControlArmMotor(t_climbMotor));

        new JoystickButton(m_fancyJoystick, fancyJoystick.se).whenPressed(new ControlArmPiston(t_ClimbPiston));
        new JoystickButton(m_fancyJoystick, fancyJoystick.st).whenPressed(new ControlArmPiston(t_ClimbPiston));


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
    

      //a.whenPressed(new GoToColor());
      //b.whenPressed(new SpinTimes());
      //x.whenPressed(new AutoTest());


    }

    public Command getAutonomousCommand() {

        SmartDashboard.putNumber("Initialized", 1);
        drive.resetPose(AutoTrajectories.testTrajectory.getInitialPose());
        return new ChooseAuto().autoChooser.getSelected(); //ThreeBallSimple(drive, t_shooter, t_intake, t_feeder, t_limelight); //FollowTrajectory(drive, AutoTrajectories.testTrajectory);
        //return new simpleDrive();  

    }

    // public Command getTestCommand() {
    //     return new ChooseMotor().motorChooser.getSelected();
    // }


}
