/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Spins the conveyor motor until there is a ball ready to shoot.
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallIntake;
import frc.robot.TestingDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PrepareBallsToShoot extends CommandBase {
  /**
   * Creates a new PrepareBallsToShoot.
   */

   BallIntake m_ballIntake;
   Timer m_timer;
   private static final int m_period = 10;

  public PrepareBallsToShoot() {
    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(BallIntake.getInstance());
    m_ballIntake = BallIntake.getInstance();
    m_timer = new Timer();
    
  }

  public static void registerWithTestingDashboard() {
    BallIntake ballIntake = BallIntake.getInstance();
    PrepareBallsToShoot cmd = new PrepareBallsToShoot();
    TestingDashboard.getInstance().registerCommand(ballIntake, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = SmartDashboard.getNumber("Conveyor1MotorSpeed",0.5);
    if (m_ballIntake.ballReadyToShoot() == false){
      m_ballIntake.spinConveyor1(speed);
    }
    if (m_ballIntake.ballReadyToShoot() == true){
      m_ballIntake.spinConveyor1(0);
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
    boolean timerExpired = m_timer.hasPeriodPassed(m_period);
    return (timerExpired || (m_ballIntake.ballReadyToShoot() == true));
  }
}
