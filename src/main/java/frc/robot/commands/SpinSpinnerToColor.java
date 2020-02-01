/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Spinner;

public class SpinSpinnerToColor extends CommandBase {
  Spinner m_spinner;
  /**
   * Creates a new SpinSpinnerToColor.
   */
  public SpinSpinnerToColor() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Spinner.getInstance());
    m_spinner = Spinner.getInstance();
  }
  
  public static void registerWithTestingDashboard() {
    Spinner spinner = Spinner.getInstance();
    SpinSpinnerToColor cmd = new SpinSpinnerToColor();
    TestingDashboard.getInstance().registerCommand(spinner, "Timed", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = SmartDashboard.getNumber("SpinnerMotorSpeed",0.2);
    m_spinner.spin(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_spinner.spin(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    String color = SmartDashboard.getString("SpinnerTargetColor", "Yellow");
    return m_spinner.getColor().equals(color);
  }
}
