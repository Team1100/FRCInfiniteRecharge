/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/* 
 * Spins the intake roller until a ball has entered the ball intake. 
 * Once the ball is inside the ball intake, the conveyor motor will run
 * to move the ball towards the end of the conveyor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallIntake;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.TestingDashboard;

public class IntakeBall extends CommandBase {
  /**
   * Creates a new IntakeBall.
   */

   BallIntake m_ballIntake;
   Timer m_timer;
   private static final int m_period = 30;

   private static final int START = 1;
   private static final int MOVE = 2;
   private static final int END = 3;
   int state = START;
   int stateCounter = 0;

  public IntakeBall() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(BallIntake.getInstance());
    m_ballIntake = BallIntake.getInstance();
    m_timer = new Timer();

  }

  public static void registerWithTestingDashboard() {
    BallIntake ballIntake = BallIntake.getInstance();
    IntakeBall cmd = new IntakeBall();
    TestingDashboard.getInstance().registerCommand(ballIntake, "Timed", cmd);

  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    state = START;
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = SmartDashboard.getNumber("IntakeRollerSpeed",0.5);

    switch (state){
    case START:
      m_ballIntake.spinIntakeRoller(speed);
      m_ballIntake.spinConveyor1(0);
      if (m_ballIntake.ballIncoming() == true){
        state = MOVE;
      }
      if (m_timer.hasPeriodPassed(m_period)){
        state = END;
      }

    case MOVE:
      m_ballIntake.spinIntakeRoller(0);
      m_ballIntake.spinConveyor1(speed);
      if ((m_ballIntake.ballIncoming() == false) || (m_timer.hasPeriodPassed(m_period))){
        if (m_ballIntake.ballIncoming() == false) {
          m_ballIntake.incrementBallsStored();
        }
        state = END;
      }
    case END:
      m_ballIntake.spinIntakeRoller(0);
      m_ballIntake.spinConveyor1(0);
    default:

    }
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballIntake.spinIntakeRoller(0);
    m_ballIntake.spinConveyor1(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (state == END);
  }
}
