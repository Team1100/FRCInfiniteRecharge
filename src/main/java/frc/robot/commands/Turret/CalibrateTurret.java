/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Turret;

public class CalibrateTurret extends CommandBase {
  /**
   * Creates a new CalibrateTurret.
   */
  Turret m_turret;
  Timer m_timer;
  double m_period;
  double m_speed;
  boolean m_parameterized = true;

  public CalibrateTurret(double period, double speed, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Turret.getInstance());
    m_timer = new Timer();
    m_parameterized = parameterized;
    m_period = period;
    m_speed = speed;
    m_turret = Turret.getInstance();
  }

  public static void registerWithTestingDashboard() {
    Turret turret = Turret.getInstance();
    CalibrateTurret cmd = new CalibrateTurret(3, 0.3, false);
    TestingDashboard.getInstance().registerCommand(turret, "Basic", cmd);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (!m_parameterized) {
      m_period = SmartDashboard.getNumber("CalibrateTurretPeriod", 3);
      m_speed = SmartDashboard.getNumber("CalibrateTurretSpeed", 0.3);
    }
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_timer.hasPeriodPassed(m_period)) {
       m_turret.spinTurretMotor(m_speed);
    }
    else {
      m_turret.spinTurretMotor(-m_speed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_turret.getTurretLimit();
  }
}
