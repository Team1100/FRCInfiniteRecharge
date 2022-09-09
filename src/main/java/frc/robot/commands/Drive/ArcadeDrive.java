// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.TestingDashboard;
import frc.robot.input.XboxController;
import frc.robot.input.XboxController.XboxAxis;
import frc.robot.subsystems.Drive;


public class ArcadeDrive extends CommandBase {
  /** Creates a new ArcadeDrive. */
  private final Drive m_drive;
  private static OI oi;
  private static XboxController m_xbox;
  

  public ArcadeDrive() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();

    addRequirements(m_drive);
    
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    ArcadeDrive cmd = new ArcadeDrive();
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    oi = OI.getInstance();
    m_xbox = oi.getXbox();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double rotation = m_xbox.getAxis(XboxAxis.kXRight);
    double speed = m_xbox.getAxis(XboxAxis.kYLeft);

    speed = speed * m_drive.getPercentPower();
    rotation = rotation * m_drive.getPercentPower();

    m_drive.arcadeDrive(-speed, rotation, true);
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
