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

public class SpinConveyorHTimed extends CommandBase {
  Timer m_timer;
  Conveyor m_conveyor;
  double m_period;
  double m_hSpeedL, m_hSpeedR;
  private static final double HORIZONTAL_SPEED_L = 0.5;
  private static final double HORIZONTAL_SPEED_R = 0.5;
  private static final double PERIOD = 5;
  private boolean m_parameterized = true;

  /**
   * Creates a new SpinConveyorHTimed.
   */
  public SpinConveyorHTimed(double horizontalSpeedL, double horizontalSpeedR, double period, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Conveyor.getInstance());
    m_timer = new Timer();
    m_conveyor = Conveyor.getInstance();

    m_hSpeedL = horizontalSpeedL;
    m_hSpeedR = horizontalSpeedR;
    m_period = period;
    m_parameterized = parameterized;
  }

  public static void registerWithTestingDashboard() {
    double hSpeedL = HORIZONTAL_SPEED_L;
    double hSpeedR = HORIZONTAL_SPEED_R;
    double period = PERIOD;

    Conveyor conveyor = Conveyor.getInstance();
    SpinConveyorHTimed cmd = new SpinConveyorHTimed(hSpeedL, hSpeedR, period, false);
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
    double hSpeedL = m_hSpeedL;
    double hSpeedR = m_hSpeedR;
    if (!m_parameterized) {
      hSpeedL = SmartDashboard.getNumber("ConveyorHMotor1Speed", 0.5);
      hSpeedR = SmartDashboard.getNumber("ConveyorHMotor2Speed", 0.5);
    }
    m_conveyor.spinHConveyors(hSpeedL, hSpeedR);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.spinHConveyors(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!m_parameterized) {
      double period = SmartDashboard.getNumber("ConveyorHMotorTimeout", 5); // default of 5 seconds
      boolean timerExpired = m_timer.hasPeriodPassed(period);
      return timerExpired;
    } else {
      boolean timerExpired = m_timer.hasPeriodPassed(m_period);
      return timerExpired;
    }
  }
}
