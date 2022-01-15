package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;
import frc.robot.Constants.CANDevices;



public class FrontLeftSwerve extends CommandBase{
    private final SwerveModule frontLeft =  new SwerveModule(CANDevices.frontLeftDriveMotorId, CANDevices.frontLeftRotationMotorId, CANDevices.frontLeftRotationEncoderId, CANDevices.frontLeftAngleOffset);

    public FrontLeftSwerve() {
        frontLeft.initRotationOffset();
        frontLeft.resetDistance();
        //driveMotorId = CANDevices.frontLeftDriveMotorId;
        //rotationMotorId = CANDevices.frontLeftRotationMotorId;
        //rotationEncoderId = CANDevices.frontLeftRotationEncoderId;
        //offset = CANDevices.frontLeftAngleOffset;
    }

}
