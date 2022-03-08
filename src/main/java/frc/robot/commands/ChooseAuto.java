
package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.ThreeBallSimple;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.FeederWheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;

public class ChooseAuto extends CommandBase {
  //private final Command simpleDrive = new simpleDrive();
  private final Shooter shooter = new Shooter();
  private final DriveSubsystem drive = new DriveSubsystem();
  private final Intake intake = new Intake();
  private final FeederWheel feeder = new FeederWheel();
  private final Limelight limelight = new Limelight();

  private final Command TwoBallSimple = new TwoBallSimple(drive, shooter, intake, feeder,limelight);
  private final Command ThreeBallSimple = new ThreeBallSimple(drive, shooter, intake, feeder, limelight);
  public SendableChooser<Command> autoChooser = new SendableChooser<Command>();

  public ChooseAuto() {
      autoChooser.setDefaultOption("ThreeBallSimple", ThreeBallSimple);
      autoChooser.addOption("TwoBallSimple", TwoBallSimple);
      SmartDashboard.putData("Auto Routines", autoChooser);
  }
}