/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This command retracts the piston that is used to push up the spinner motor and color sensor.

package frc.robot.commands.Spinner;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.subsystems.Spinner;

import frc.robot.TestingDashboard;

public class RetractSpinner extends CommandBase {
  Spinner m_spinner;
  boolean m_finished = false;
  DoubleSolenoid m_piston;
  boolean isExtended = false;
  
  public RetractSpinner() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Spinner.getInstance());
    m_spinner = Spinner.getInstance();
    m_piston = m_spinner.getPiston();
  }

  public static void registerWithTestingDashboard() {
    Spinner spinner = Spinner.getInstance();
    RetractSpinner cmd = new RetractSpinner();
    TestingDashboard.getInstance().registerCommand(spinner, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (m_piston.get() == DoubleSolenoid.Value.kForward) {
      isExtended = true;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (isExtended) {
      m_spinner.retractSpinnerArm();
    }
    
    if (m_piston.get() == DoubleSolenoid.Value.kReverse) {
      m_finished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_finished;
  }
}
