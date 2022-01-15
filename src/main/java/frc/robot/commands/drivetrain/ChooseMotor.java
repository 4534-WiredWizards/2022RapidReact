package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.CANDevices;



//Select Swerve Module(front right, front left, rear right, or rear left)
//Choose specific motor on Swerve Module (drive or rotation)
//Assign each motor to a button

public class ChooseMotor extends CommandBase {

    private final Command frontLeft  =  new FrontLeftSwerve();
    private final Command frontRight = new FrontRightSwerve();
    private final Command rearLeft = new RearLeftSwerve();
    private final Command rearRight = new RearRightSwerve();
    //private final SwerveModule frontLeft =  new SwerveModule(CANDevices.frontLeftDriveMotorId, CANDevices.frontLeftRotationMotorId, CANDevices.frontLeftRotationEncoderId, CANDevices.frontLeftAngleOffset);
    //private final SwerveModule frontRight = new SwerveModule(CANDevices.frontRightDriveMotorId, CANDevices.frontRightRotationMotorId, CANDevices.frontRightRotationEncoderId, CANDevices.frontRightAngleOffset);
    //private final SwerveModule backLeft = new SwerveModule(CANDevices.rearLeftDriveMotorId, CANDevices.rearRightRotationMotorId, CANDevices.rearLeftRotationEncoderId, CANDevices.rearLeftAngleOffset);
    //private final SwerveModule backRight = new SwerveModule(CANDevices.rearRightDriveMotorId, CANDevices.rearRightRotationMotorId, CANDevices.rearLeftRotationEncoderId, CANDevices.rearRightAngleOffset);
    // Create instances of Swerve Module and pass in different drive and rotation motor IDs. 

    public SendableChooser<Command> motorChooser = new SendableChooser<Command>();

    public ChooseMotor() {
        motorChooser.setDefaultOption("frontLeft", frontLeft);
        motorChooser.addOption("frontRight", frontRight);
        motorChooser.addOption("backLeft", rearLeft);
        motorChooser.addOption("backRight", rearRight);
        SmartDashboard.putData(motorChooser);


    }
    
}