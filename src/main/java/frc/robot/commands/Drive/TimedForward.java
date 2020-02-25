/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import frc.robot.TestingDashboard;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TimedForward extends CommandBase {
  private Timer m_timer;
  private Drive m_drive;
  private double m_time;
  private double m_speed;
  
  /**
   * Creates a new TimedForward.
   */
  public TimedForward(double driveTime, double driveSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.    
    addRequirements(Drive.getInstance());
    m_drive = Drive.getInstance();
    m_timer = new Timer();
    m_time = driveTime;
    m_speed = driveSpeed;
  }
  
  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    double time = SmartDashboard.getNumber("DriveForwardTime", 3);
    double speed = SmartDashboard.getNumber("AutoDriveSpeed", 0.5);
    TimedForward cmd = new TimedForward(time, speed);
    TestingDashboard.getInstance().registerCommand(drive, "Timed", cmd);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.tankDrive(m_speed, m_speed);
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_timer.get() > m_time; 
  }
}
