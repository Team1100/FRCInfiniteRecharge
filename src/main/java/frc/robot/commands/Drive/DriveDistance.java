// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Drive;

public class DriveDistance extends CommandBase {
  static Drive m_drive;
  boolean m_parameterized;
  double m_distance;
  double m_speed;
  final double INITIAL_SPEED = 0.3;

  /** Creates a new DriveDistance. */
  // distance is in inches
  public DriveDistance(double distance, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    addRequirements(m_drive);
    m_parameterized = parameterized;
    m_distance = distance;
    m_speed = INITIAL_SPEED;
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    DriveDistance cmd = new DriveDistance(12.0, false);
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_distance = TestingDashboard.getInstance().getNumber(m_drive, "DistanceToTravelInInches");
    }
    m_drive.tankDrive(m_speed, m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Encoder leftEncoder = m_drive.getLeftEncoder();
    Encoder rightEncoder = m_drive.getRightEncoder();
    boolean finished = false;
    if (leftEncoder.getDistance() >= m_distance && rightEncoder.getDistance() >= m_distance) {
      finished = true;
    }
    return finished;
  }
}
