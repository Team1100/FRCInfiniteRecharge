/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Conveyor;

public class SpitBalls extends CommandBase {
  private Conveyor m_conveyor;

  /**
   * Creates a new ShootBalls.
   */
  public SpitBalls() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Conveyor.getInstance());
    m_conveyor = Conveyor.getInstance();
  }

  public static void registerWithTestingDashboard() {
    Conveyor conveyor = Conveyor.getInstance();
    SpitBalls cmd = new SpitBalls();
    TestingDashboard.getInstance().registerCommand(conveyor, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_conveyor.spinAllConveyors(0, 0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Load Ball into bottom of v conveyor
    m_conveyor.spinAllConveyors(-0.7, -0.5, -0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.spinAllConveyors(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
