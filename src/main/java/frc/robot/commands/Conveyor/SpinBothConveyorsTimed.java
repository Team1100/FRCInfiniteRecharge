/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Spins the conveyor motor for a specified amount of time.
package frc.robot.commands.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Conveyor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpinBothConveyorsTimed extends CommandBase {
  private Timer m_timer;
  private Conveyor m_conveyor;
  private double m_vPeriod, m_hPeriod;
  private double m_hSpeedL, m_hSpeedR, m_vSpeed;
  private static final double HORIZONTAL_SPEED_L = 0.5;
  private static final double HORIZONTAL_SPEED_R = 0.5;
  private static final double VERTICAL_SPEED = 0.5;
  private static final double HORIZONTAL_PERIOD = 5;
  private static final double VERTICAL_PERIOD = 5;
  private boolean m_parameterized = true;

  /**
   * Creates a new SpinBothConveyorsTimed.
   */
  public SpinBothConveyorsTimed(double horizontalSpeedL, double horizontalSpeedR, double verticalSpeed, double horizontalPeriod, double verticalPeriod, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Conveyor.getInstance());
    m_timer = new Timer();
    m_conveyor = Conveyor.getInstance();

    m_hSpeedL = horizontalSpeedL;
    m_hSpeedR = horizontalSpeedR;
    m_vSpeed = verticalSpeed;
    m_hPeriod = horizontalPeriod;
    m_vPeriod = verticalPeriod;
  }

  public static void registerWithTestingDashboard() {
    double hSpeedL = HORIZONTAL_SPEED_L;
    double hSpeedR = HORIZONTAL_SPEED_R;
    double vSpeed = VERTICAL_SPEED;
    double vPeriod = VERTICAL_PERIOD;
    double hPeriod = HORIZONTAL_PERIOD;

    Conveyor conveyor = Conveyor.getInstance();
    SpinBothConveyorsTimed cmd = new SpinBothConveyorsTimed(hSpeedL, hSpeedR, vSpeed, vPeriod, hPeriod, false);
    TestingDashboard.getInstance().registerCommand(conveyor, "Timed", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double vSpeed = m_vSpeed;
    double hSpeedL = m_hSpeedL;
    double hSpeedR = m_hSpeedR;
    if (!m_parameterized) {
      vSpeed = SmartDashboard.getNumber("ConveyorVMotorSpeed", 1);
      hSpeedL = SmartDashboard.getNumber("ConveyorHMotor1Speed", 0.5);
      hSpeedR = SmartDashboard.getNumber("ConveyorHMotor2Speed", 0.5);
    }
    m_conveyor.spinAllConveyors(hSpeedL, hSpeedR, vSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.spinAllConveyors(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!m_parameterized) {
      double vPeriod = SmartDashboard.getNumber("ConveyorVMotorTimeout", 5); // default of 5 seconds
      double hPeriod = SmartDashboard.getNumber("ConveyorHMotorTimeout", 5); // default of 5 seconds
      boolean vTimerExpired = m_timer.hasPeriodPassed(vPeriod);
      boolean hTimerExpired = m_timer.hasPeriodPassed(hPeriod);
      return (vTimerExpired || hTimerExpired);
    } else {
      boolean vTimerExpired = m_timer.hasPeriodPassed(m_vPeriod);
      boolean hTimerExpired = m_timer.hasPeriodPassed(m_hPeriod);
      return (vTimerExpired || hTimerExpired);
    }
  }
}
