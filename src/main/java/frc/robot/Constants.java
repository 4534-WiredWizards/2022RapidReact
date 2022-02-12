package frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;


public class Constants {

    public static final class CANDevices {

        public static final int frontLeftRotationMotorId = 11;
        public static final int frontLeftDriveMotorId = 10;

        public static final int frontRightRotationMotorId = 21;
        public static final int frontRightDriveMotorId =20;

        public static final int rearLeftRotationMotorId = 31;
        public static final int rearLeftDriveMotorId = 30;

        public static final int rearRightRotationMotorId = 41;
        public static final int rearRightDriveMotorId = 40;

        //public static final int leftClimberMotorId = 19;
        //public static final int rightClimberMotorId = 29;

        public static final int frontLeftRotationEncoderId = 12;
        public static final int frontRightRotationEncoderId = 22;
        public static final int rearLeftRotationEncoderId = 32;
        public static final int rearRightRotationEncoderId = 42;

        public static final double frontLeftAngleOffset = Units.degreesToRadians(201.4); //307.1  //26.60    //26.71
        public static final double frontRightAngleOffset = Units.degreesToRadians(116.3); //202.0 //111.18   //111.44
        public static final double rearLeftAngleOffset = Units.degreesToRadians(291.6); //202.6  //108.98    //109.60
        public static final double rearRightAngleOffset = Units.degreesToRadians(36.4); //294.0 //37.00     //37.00
    
        public static final int feederWheelMotorId = 35;
        public static final int rightFlywheelMotorId = 25;
        public static final int leftFlywheelMotorId = 15;

        public static final int kickerMotorId = 10;
        public static final int leftIntakeMotorId = 16; 
        public static final int rightIntakeMotorId = 26;
        public static final int centerIntakeMotorId = 36;
        public static final int conveyorMotorId = 9;

        public static final int imuId = 18;

        public static final double reductionFactor = 1/1;

    }

    public static final class DIOChannels {

        public static final int bottomBannerPort = 0;
        public static final int topBannerPort = 9;

    }

    public static final class InputDevices {

        public static final int leftJoystickPort = 0;
        public static final int rightJoystickPort = 1;

        public static final int gamepadPort = 2;

        public static final int btn_a = 1;
        public static final int btn_b = 2;
        public static final int btn_x = 3;
        public static final int btn_y = 4;
        public static final int btn_leftBumper = 5;
        public static final int btn_rightBumper = 6;
        public static final int btn_leftTrigger = 2;
        public static final int btn_rightTrigger = 3;
    }

    public static final class PneumaticChannels {

        public  static final int PCMId = 1;

        public static final int[] intakeSolenoidChannels = {0, 1, 2};
        public static final int[] hoodSolenoidChannels = {2, 3};
        public static final int[] climberSolenoidChannels = {4, 5};

    }

    public static final class DriveConstants {

        public static final double trackWidth = Units.inchesToMeters(22); //23.5 //17.25
        public static final double wheelBase = Units.inchesToMeters(22); //23.5 //28.5

        public static final SwerveDriveKinematics kinematics = 
            new SwerveDriveKinematics(
                new Translation2d(trackWidth / 2.0, wheelBase / 2.0), //front left
                new Translation2d(trackWidth / 2.0, -wheelBase / 2.0), //front right
                new Translation2d(-trackWidth / 2.0, wheelBase / 2.0), //rear left
                new Translation2d(-trackWidth / 2.0, -wheelBase / 2.0) //rear right
            );

        public static final double driveWheelGearReduction = 8.14; //6.86 for MK3
        public static final double rotationWheelGearReduction = 12.8; //12.8 for MK3

        public static final double wheelDiameterMeters = 0.050686 * 2;

        public static final double rotationMotorMaxSpeedRadPerSec = .25; //1.0;
        public static final double rotationMotorMaxAccelRadPerSecSq = 0.5; //1.0

        public static final SimpleMotorFeedforward driveFF = new SimpleMotorFeedforward(0.254, 0.137);

        public static final double maxDriveSpeed = 4; //14.4;  4   6
        public static final double teleopTurnRateDegPerSec = 120; //360.0;  was at 90 but we lowered because it was very bumpy
                                                                 // 45 worked but was too slow :(    //Rate the robot will spin with full rotation command

    }

    public static final class SuperstructureConstants {

        public static final double baseShootingSpeed = 6000; //rptations per minute

        public static final double intakingPower = 0.4;
        public static final double jogFowardPower = 0.2;

        public static final double kickerPower = 1.0;
        public static final double conveyorPower = 0.35;

        public static final double flywheelGearRatio = 32.0 / 18.0;

        public static final double jogDelaySeconds = 0.1;

    }

    public static final class ClimbingConstants {

        public static final double climberMotorGearReduction = 1;

        public static final double winchDiameterInches = 1;
        public static final double climberMaxHeightInches = 40.5;

        public static final double desiredClimberSpeedInchesPerSecond = 1;

    }

    public static final class VisionConstants {

        public static final double limelightHeightInches = 26.5; // distance from limelight to ground
        public static final double limelightMountAngleRadians = Units.degreesToRadians(53);

    }

    public static final class AutoConstants {

        public static final double maxVelMetersPerSec = 4.5;
        public static final double maxAccelMetersPerSecondSq = 1.95;
        
    }

    public static final class FieldConstants {

        public static final double targetHeightInches = 89.5;

    }
    
}
