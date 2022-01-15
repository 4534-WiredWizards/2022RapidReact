package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;
import frc.robot.Constants.CANDevices;

public class RearRightSwerve extends CommandBase{
    private final SwerveModule backRight = new SwerveModule(CANDevices.rearRightDriveMotorId, CANDevices.rearRightRotationMotorId, CANDevices.rearLeftRotationEncoderId, CANDevices.rearRightAngleOffset);
    public RearRightSwerve() {
        backRight.initRotationOffset();
    }
}
