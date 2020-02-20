/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Spins the ball intake roller while command is active.
package frc.robot.commands.BallIntake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.BallIntake;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpinIntakeRoller extends CommandBase {
  /**
   * Creates a new SpinIntakeRoller.
   */

   BallIntake m_ballIntake;

  public SpinIntakeRoller() {
    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(BallIntake.getInstance());
    m_ballIntake = BallIntake.getInstance();
  }

  public static void registerWithTestingDashboard() {
    BallIntake ballIntake = BallIntake.getInstance();
    SpinIntakeRoller cmd = new SpinIntakeRoller();
    TestingDashboard.getInstance().registerCommand(ballIntake, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double speed = SmartDashboard.getNumber("IntakeRollerSpeed",0.5);
    m_ballIntake.spinIntakeRoller(speed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballIntake.spinIntakeRoller(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
