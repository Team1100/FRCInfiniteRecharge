/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Auto;

public class Wait extends CommandBase {

  Timer m_timer;
	double m_time;

  /**
   * Creates a new Wait.
   */
  public Wait(double waitTime) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_timer = new Timer();
    m_time = waitTime;
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    double time = SmartDashboard.getNumber("StartAutoWaitTime", 3);
    Wait cmd = new Wait(time);
    TestingDashboard.getInstance().registerCommand(auto, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_timer.get() > m_time;
  }
}
