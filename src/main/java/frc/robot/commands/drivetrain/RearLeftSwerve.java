package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;
import frc.robot.Constants.CANDevices;  

public class RearLeftSwerve extends CommandBase{
    private final SwerveModule rearLeft = new SwerveModule(CANDevices.rearLeftDriveMotorId, CANDevices.rearRightRotationMotorId, CANDevices.rearLeftRotationEncoderId, CANDevices.rearLeftAngleOffset);
    public RearLeftSwerve() {
        rearLeft.initRotationOffset();
        
    }
}
