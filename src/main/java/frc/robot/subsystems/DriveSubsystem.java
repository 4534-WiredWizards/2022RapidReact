package frc.robot.subsystems;

//import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.CANDevices;
import frc.robot.Constants.DriveConstants;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

public class DriveSubsystem extends SubsystemBase {

    /**
     * Subsystem that controls the drivetrain of the robot
     * Handles all the odometry and base movement for the chassis
     */

    /**
     * absolute encoder offsets for the wheels
     * 180 degrees added to offset values to invert one side of the robot so that it doesn't spin in place
     */
    //private static final double frontLeftAngleOffset = Units.degreesToRadians(22.0);
    //private static final double frontRightAngleOffset = Units.degreesToRadians(114.34);
    //private static final double rearLeftAngleOffset = Units.degreesToRadians(109.1);
    //private static final double rearRightAngleOffset = Units.degreesToRadians(37.7);

    /**
     * SwerveModule objects
     * Parameters:
     * drive motor can ID
     * rotation motor can ID
     * external CANCoder can ID
     * measured CANCoder offset
     */
    

    private final SwerveModule frontLeft = 
        new SwerveModule(
            CANDevices.frontLeftDriveMotorId,
            CANDevices.frontLeftRotationMotorId,
            CANDevices.frontLeftRotationEncoderId,
            CANDevices.frontLeftAngleOffset
        );

    private final SwerveModule frontRight = 
        new SwerveModule(
            CANDevices.frontRightDriveMotorId,
            CANDevices.frontRightRotationMotorId,
            CANDevices.frontRightRotationEncoderId,
            CANDevices.frontRightAngleOffset
        );

    private final SwerveModule rearLeft = 
        new SwerveModule(
            CANDevices.rearLeftDriveMotorId,
            CANDevices.rearLeftRotationMotorId,
            CANDevices.rearLeftRotationEncoderId,
            CANDevices.rearLeftAngleOffset
        );

    private final SwerveModule rearRight = 
        new SwerveModule(
            CANDevices.rearRightDriveMotorId,
            CANDevices.rearRightRotationMotorId,
            CANDevices.rearRightRotationEncoderId,
            CANDevices.rearRightAngleOffset
        );

    // commanded values from the joysticks and field relative value to use in AlignWithTargetVision and AlignWithGyro
    private double commandedForward = 0;
    private double commandedStrafe = 0;
    private double commandedRotation = 0;

    private boolean isCommandedFieldRelative = true;

    //private final PigeonIMU imu = new PigeonIMU(CANDevices.imuId);
  
    public AHRS ahrs = new AHRS();

    /**
     * odometry for the robot, measured in meters for linear motion and radians for rotational motion
     * Takes in kinematics and robot angle for parameters
     */
    private final SwerveDriveOdometry odometry = 
        new SwerveDriveOdometry(
            DriveConstants.kinematics, 
            new Rotation2d(getHeading().getRadians())
        );

    public DriveSubsystem() {

        ahrs.zeroYaw();

        // initialize the rotation offsets for the CANCoders
        frontLeft.initRotationOffset();
        frontRight.initRotationOffset();
        rearLeft.initRotationOffset();
        rearRight.initRotationOffset();

        // reset the measured distance driven for each module
        frontLeft.resetDistance();
        frontRight.resetDistance();
        rearLeft.resetDistance();
        rearRight.resetDistance();

    }

    @Override
    public void periodic() {

        // update the odometry every 20ms
        odometry.update(getHeading(), getModuleStates());
        
    }
    
    /**
     * method for driving the robot
     * Parameters:
     * forward linear value
     * sideways linear value
     * rotation value
     * if the control is field relative or robot relative
     * @return 
     */
    public void drive(double forward, double strafe, double rotation, boolean isFieldRelative) {

        // update the drive inputs for use in AlignWithGyro and AlignWithTargetVision control
        commandedForward = forward;
        commandedStrafe = strafe;
        commandedRotation = rotation;

        isCommandedFieldRelative = isFieldRelative;

        /**
         * ChassisSpeeds object to represent the overall state of the robot
         * ChassisSpeeds takes a forward and sideways linear value and a rotational value
         * 
         * speeds is set to field relative or default (robot relative) based on parameter
         */
        ChassisSpeeds speeds =
            isCommandedFieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(
                    forward, strafe, rotation, getHeading())
                : new ChassisSpeeds(forward, strafe, rotation);
        
        // use kinematics (wheel placements) to convert overall robot state to array of individual module states
        SwerveModuleState[] states = DriveConstants.kinematics.toSwerveModuleStates(speeds);

        // make sure the wheels don't try to spin faster than the maximum speed possible
        //SwerveDriveKinematics.normalizeWheelSpeeds(states, DriveConstants.maxDriveSpeed);

        setModuleStates(states);

    }

    /**
     * Method to set the desired state for each swerve module
     * Uses PID and feedforward control to control the linear and rotational values for the modules
     */
    public void setModuleStates(SwerveModuleState[] moduleStates) {

        frontLeft.setDesiredStateClosedLoop(moduleStates[0]);
        frontRight.setDesiredStateClosedLoop(moduleStates[1]);
        rearLeft.setDesiredStateClosedLoop(moduleStates[2]);
        rearRight.setDesiredStateClosedLoop(moduleStates[3]);

    }

    // returns an array of SwerveModuleState
    public SwerveModuleState[] getModuleStates() {

        SwerveModuleState[] states = {
            new SwerveModuleState(frontRight.getCurrentVelocityMetersPerSecond(), frontRight.getCanEncoderAngle()),
            new SwerveModuleState(rearRight.getCurrentVelocityMetersPerSecond(), rearRight.getCanEncoderAngle()),
            new SwerveModuleState(frontLeft.getCurrentVelocityMetersPerSecond(), frontLeft.getCanEncoderAngle()),
            new SwerveModuleState(rearLeft.getCurrentVelocityMetersPerSecond(), rearLeft.getCanEncoderAngle())
        };

        return states;

    }

    /**
     * Return the current position of the robot on field
     * Based on drive encoder and gyro reading
     */
    public Pose2d getPose() {

        return odometry.getPoseMeters();

    }

    // reset the current pose to a desired pose
    public void resetPose(Pose2d pose) {

        ahrs.zeroYaw();
        odometry.resetPosition(pose, getHeading());

    }

    // reset the measured distance driven for each module
    public void resetDriveDistances() {

        frontLeft.resetDistance();
        frontRight.resetDistance();
        rearLeft.resetDistance();
        rearRight.resetDistance();

    }

    // return the average distance driven for each module to get an overall distance driven by the robot
    public double getAverageDriveDistanceRadians() {

        return ((
            Math.abs(frontLeft.getDriveDistanceRadians())
            + Math.abs(frontRight.getDriveDistanceRadians())
            + Math.abs(rearLeft.getDriveDistanceRadians())
            + Math.abs(rearRight.getDriveDistanceRadians())) / 4.0);

    }

    // return the average velocity for each module to get an overall velocity for the robot
    public double getAverageDriveVelocityRadiansPerSecond() {

        return ((
            Math.abs(frontLeft.getCurrentVelocityRadiansPerSecond())
            + Math.abs(frontRight.getCurrentVelocityRadiansPerSecond()) 
            + Math.abs(rearLeft.getCurrentVelocityRadiansPerSecond()) 
            + Math.abs(rearRight.getCurrentVelocityRadiansPerSecond())) / 4.0);

    }

    // get the current heading of the robot based on the gyro
    public Rotation2d getHeading() {

        double[] ypr = new double[3];

        ypr[0] = 0-ahrs.getAngle();

        return Rotation2d.fromDegrees(ypr[0]);

    }

    public double[] getCommandedDriveValues() {

        double[] values = {commandedForward, commandedStrafe, commandedRotation};

        return values;

    }

    public boolean getIsFieldRelative() {

        return isCommandedFieldRelative;

    }

    public void setFieldRelative() {
        isCommandedFieldRelative = !isCommandedFieldRelative;
    }

    public double getGyro() {
        return -ahrs.getAngle();
    }

    public double relativeDriveDirection() {
        double angle = Math.toDegrees(Math.atan2(commandedForward, -commandedStrafe));
        if(angle < 0) {
            return angle + 360;
        }
        return angle;
    }

    public void resetImu() {

        ahrs.zeroYaw();
    }

    public void updateSmartDashboard(){
        
    SmartDashboard.putNumber("Front Left Angle: ", frontLeft.getCanCoderRawAngle());
    SmartDashboard.putNumber("Front Right Angle: ", frontRight.getCanCoderRawAngle());
    SmartDashboard.putNumber("Back Left Angle: ", rearLeft.getCanCoderRawAngle());
    SmartDashboard.putNumber("Back Right Angle: ", rearRight.getCanCoderRawAngle());
    SmartDashboard.putNumber("Gyro Angle", -ahrs.getAngle());

    SmartDashboard.putNumber("Front Left Adjusted Angle: ", frontLeft.getCanCoderAngle().getDegrees());
    SmartDashboard.putNumber("Front Right Adjusted Angle: ", frontRight.getCanCoderAngle().getDegrees());
    SmartDashboard.putNumber("Back Left Adjusted Angle: ", rearLeft.getCanCoderAngle().getDegrees());
    SmartDashboard.putNumber("Back Right Adjusted Angle: ", rearRight.getCanCoderAngle().getDegrees());
    SmartDashboard.putNumber("Front Left NEO: ", frontLeft.getCanEncoderAngle().getDegrees());
    SmartDashboard.putNumber("Front Right NEO: ", frontRight.getCanEncoderAngle().getDegrees());
    SmartDashboard.putNumber("Rear Left NEO: ", rearLeft.getCanEncoderAngle().getDegrees());
    SmartDashboard.putNumber("Rear Right NEO: ", rearRight.getCanEncoderAngle().getDegrees());



    
    }

}