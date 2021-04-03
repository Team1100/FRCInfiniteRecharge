// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Drive;

public class Rotate extends CommandBase {
  Drive m_drive;
  double m_speed;
  /** Creates a new Rotate. */
  public Rotate() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    addRequirements(m_drive);
    m_speed = 0.5;
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    Rotate cmd = new Rotate();
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_speed = TestingDashboard.getInstance().getNumber(m_drive, "SpeedWhenTurning");
    m_drive.tankDrive(m_speed, m_speed * -1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
