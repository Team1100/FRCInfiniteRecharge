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

  public SpinShooter() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter.getInstance());
    m_shooter = Shooter.getInstance();
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    SpinShooter cmd = new SpinShooter();
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double topSpeed = SmartDashboard.getNumber("TopShooterSpeed",0);
    double botSpeed = SmartDashboard.getNumber("BottomShooterSpeed",0);
    m_shooter.setTop(-topSpeed);
    m_shooter.setBottom(-botSpeed);
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
