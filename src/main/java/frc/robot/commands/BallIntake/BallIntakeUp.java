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
package frc.robot.commands.BallIntake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallIntake;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.TestingDashboard;

public class BallIntakeUp extends CommandBase {
  /**
   * Creates a new IntakeBall.
   */

   BallIntake m_ballIntake;
   DoubleSolenoid m_piston;
   boolean m_finished = false;
   boolean isDown = false;

  public BallIntakeUp() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(BallIntake.getInstance());
    m_ballIntake = BallIntake.getInstance();
    m_piston = m_ballIntake.getPiston();

  }

  public static void registerWithTestingDashboard() {
    BallIntake ballIntake = BallIntake.getInstance();
    BallIntakeUp cmd = new BallIntakeUp();
    TestingDashboard.getInstance().registerCommand(ballIntake, "Basic", cmd);

  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(m_piston.get() == DoubleSolenoid.Value.kForward){
      isDown = true;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isDown){
      m_ballIntake.raiseIntake();
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
