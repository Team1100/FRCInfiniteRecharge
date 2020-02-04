/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallIntake;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.TestingDashboard;

public class IntakeBall extends CommandBase {
  /**
   * Creates a new IntakeBall.
   */

   BallIntake m_ballIntake;
   Timer m_timer;
   Integer m_numBallsStored;

  public IntakeBall() {
    // Use addRequirements() here to declare subsystem dependencies.

    m_ballIntake = BallIntake.getInstance();
    m_timer = new Timer();
    m_numBallsStored = 0;

  }

  public static void registerWithTestingDashboard() {
    BallIntake ballIntake = BallIntake.getInstance();
    IntakeBall cmd = new IntakeBall();
    TestingDashboard.getInstance().registerCommand(ballIntake, "Timed", cmd);

  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ballIntake.ballIncoming();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_ballIntake.ballIncoming() == false){
      m_timer.start();
      m_ballIntake.spinIntakeRoller(0.5);
      m_ballIntake.spinConveyor1(0);
    if (m_ballIntake.ballIncoming() == true){
      m_ballIntake.incrementBallsStored();
      m_ballIntake.spinIntakeRoller(0);
      m_ballIntake.spinConveyor1(0.5);
    }
    }
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballIntake.spinIntakeRoller(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean timerExpired = m_timer.hasPeriodPassed(30);
    return timerExpired;
  }
}
