// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import edu.wpi.first.wpilibj.Timer;

public class BallFired extends CommandBase {

  boolean m_ballFired;
  Timer m_timer;
  /** Creates a new BallFired. */
  public BallFired() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_ballFired = false;
    m_timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ballFired = false;
    m_timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Conveyor.getInstance().ballReadyToShoot()) {
      m_timer.start();
    }
    if (m_timer.hasElapsed(2)) {
      m_ballFired = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_ballFired;
  }
}
