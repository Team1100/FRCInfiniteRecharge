/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveDistance extends CommandBase {
  double m_speed;
  double m_distance;

  Encoder left, right;
  /**
   * Creates a new DriveDistance.
   */
  public DriveDistance(double distance, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Drive.getInstance());
    m_speed = speed;
    m_distance = distance;
    left = Drive.getInstance().getLeftEncoder();
    right = Drive.getInstance().getRightEncoder();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    left.reset();
    right.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Drive.getInstance().tankDrive(m_speed, m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Drive.getInstance().tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return left.getDistance() >= m_distance || right.getDistance() >= m_distance;
  }
}
