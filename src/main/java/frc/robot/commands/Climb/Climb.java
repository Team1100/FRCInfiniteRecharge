/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Climber;


public class Climb extends CommandBase {
  private Climber m_climber;
  private double m_speed;
  private boolean m_parameterized = true;

  /**
   * Creates a new Climb.
   */
  public Climb(double speed, boolean parameterized) {
    m_climber = Climber.getInstance();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Climber.getInstance()); 
    m_speed = speed;
    m_parameterized = parameterized;
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    double speed = SmartDashboard.getNumber("ClimberSpeed", 0.5);
    Climb cmd = new Climb(speed, false);
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = m_speed;
    if (!m_parameterized) {
      speed = SmartDashboard.getNumber("ClimberSpeed", 0.5);
    }
    m_climber.climb(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.climb(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
