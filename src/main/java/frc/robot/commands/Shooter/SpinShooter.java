/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Shooter;

public class SpinShooter extends CommandBase {
  /**
   * Creates a new SpinShooter.
   */
  Shooter m_shooter;
  double m_topSpeed;
  double m_botSpeed;

  public SpinShooter(double topShooterSpeed, double bottomShooterSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter.getInstance());
    m_shooter = Shooter.getInstance();
    m_topSpeed = topShooterSpeed;
    m_botSpeed = bottomShooterSpeed;
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    double topSpeed = SmartDashboard.getNumber("TopShooterSpeed", 0.2);
    double botSpeed = SmartDashboard.getNumber("BottomShooterSpeed", 0.2);
    SpinShooter cmd = new SpinShooter(topSpeed, botSpeed);
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.setTop(m_topSpeed);
    m_shooter.setBottom(m_botSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setTop(0);
    m_shooter.setBottom(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
