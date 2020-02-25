/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Climb;

import frc.robot.TestingDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Climb extends CommandBase {
  /**
   * Creates a new Climb.
   */
  Climber m_climber;
  boolean m_parametrized = true;
  double m_speed;



  public Climb(double speed, boolean parametrized) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Climber.getInstance()); 
    m_climber = Climber.getInstance();
    m_parametrized = parametrized;
    m_speed = speed;
  }

  
  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    double speed = SmartDashboard.getNumber("ClimberSpeed", 0.3);
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
    if(!m_parametrized) {
      speed = SmartDashboard.getNumber("ClimberSpeed", 0.3);
    }
    m_climber.setLeft(speed);
    m_climber.setRight(-speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.setRight(0);
    m_climber.setLeft(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
