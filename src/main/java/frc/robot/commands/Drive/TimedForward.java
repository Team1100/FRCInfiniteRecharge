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
  Timer t;
  Drive drive;
  double time;      
  /**
   * Creates a new timedforward.
   */
  public TimedForward() {
    addRequirements(Drive.getInstance());
    drive = Drive.getInstance();
    t = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
  }
  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    TimedForward cmd = new TimedForward();
    TestingDashboard.getInstance().registerCommand(drive, "Timed", cmd);
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time = SmartDashboard.getNumber("DriveForwardTime", 3);
    t.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed =  SmartDashboard .getNumber("AutoDriveSpeed", 0.5);
    drive.tankDrive(speed, speed);

  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return t.get() > time; 
  }
}
