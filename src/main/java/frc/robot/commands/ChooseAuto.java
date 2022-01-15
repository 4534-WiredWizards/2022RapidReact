
package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChooseAuto extends CommandBase {
  //private final Command simpleDrive = new simpleDrive();
  private final Command DoNothing = new DoNothing();
  public SendableChooser<Command> autoChooser = new SendableChooser<Command>();

  public ChooseAuto() {
      //autoChooser.setDefaultOption("simpleDrive", simpleDrive);
      autoChooser.addOption("DoNothing", DoNothing);
      SmartDashboard.putData(autoChooser);
  }
}