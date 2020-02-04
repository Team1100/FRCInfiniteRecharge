/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallIntake;
import frc.robot.TestingDashboard;
import edu.wpi.first.wpilibj.Timer;

public class PrepareBallsToShoot extends CommandBase {
  /**
   * Creates a new PrepareBallsToShoot.
   */

   BallIntake m_ballIntake;
   Timer m_timer;

  public PrepareBallsToShoot() {
    // Use addRequirements() here to declare subsystem dependencies.

    m_ballIntake = BallIntake.getInstance();
    m_timer = new Timer();
    
  }

  public static void registerWithTestingDashboard() {
    BallIntake ballIntake = BallIntake.getInstance();
    PrepareBallsToShoot cmd = new PrepareBallsToShoot();
    TestingDashboard.getInstance().registerCommand(ballIntake, "Timed", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ballIntake.ballReadyToShoot();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_ballIntake.ballReadyToShoot() == false){
      m_timer.start();
      m_ballIntake.spinConveyor1(0.5);
    if (m_ballIntake.ballReadyToShoot() == true){
      m_ballIntake.spinConveyor1(0);
    }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballIntake.spinConveyor1(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean timerExpired = m_timer.hasPeriodPassed(10);
    return timerExpired;
  }
}
