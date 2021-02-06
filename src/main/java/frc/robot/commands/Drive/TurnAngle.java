// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.TestingDashboard;

public class TurnAngle extends CommandBase {
  static Drive m_drive;
  // angle is in degrees
  double m_angle;
  boolean m_parameterized;
  double m_speed;
  double m_initialAngle;

  /** Creates a new TurnAngle. */
  public TurnAngle(double angle, boolean parameterized) {
    m_drive = Drive.getInstance();
    m_parameterized = parameterized;
    m_angle = angle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    TurnAngle cmd = new TurnAngle(12.0, false);
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_initialAngle = m_drive.getYaw();
    if (!m_parameterized) {
      TestingDashboard.getInstance().updateNumber(m_drive, "InitialAngle", m_initialAngle);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_angle = TestingDashboard.getInstance().getNumber(m_drive, "AngleToTurnInDegrees");
      m_speed = TestingDashboard.getInstance().getNumber(m_drive, "SpeedWhenTurning");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
