/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Turret;

public class TurnToLimit extends CommandBase {
  /**
   * Creates a new TurnToLimit.
   */
  Turret m_turret;
  public TurnToLimit() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Turret.getInstance());
  }

  public static void registerWithTestingDashboard() {
    Turret turret = Turret.getInstance();
    TurnToLimit cmd = new TurnToLimit();
    TestingDashboard.getInstance().registerCommand(turret, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_turret = Turret.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_turret.spinTurretMotor(0.3);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_turret.spinTurretMotor(0);
  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_turret.getTurretLimit();
  }
}
