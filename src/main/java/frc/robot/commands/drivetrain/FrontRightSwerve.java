package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;
import frc.robot.Constants.CANDevices;

public class FrontRightSwerve extends CommandBase {
    private final SwerveModule frontRight = new SwerveModule(CANDevices.frontRightDriveMotorId, CANDevices.frontRightRotationMotorId, CANDevices.frontRightRotationEncoderId, CANDevices.frontRightAngleOffset);
    public FrontRightSwerve() {
        frontRight.initRotationOffset();
        frontRight.resetDistance();
    }
}
