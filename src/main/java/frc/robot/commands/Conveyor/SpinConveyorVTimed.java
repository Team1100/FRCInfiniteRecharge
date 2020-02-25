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

public class SpinConveyorVTimed extends CommandBase {
  Timer m_timer;
  Conveyor m_conveyor;
  double m_period;
  double m_vSpeed;
  private static final double VERTICAL_SPEED = 0.5;
  private static final double PERIOD = 5;
  private boolean m_parameterized = true;

  /**
   * Creates a new SpinConveyorVTimed.
   */
  public SpinConveyorVTimed(double vSpeed, double period, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Conveyor.getInstance());
    m_timer = new Timer();
    m_conveyor = Conveyor.getInstance();

    m_vSpeed = vSpeed;
    m_period = period;
    m_parameterized = parameterized;
  }

  public static void registerWithTestingDashboard() {
    double vSpeed = VERTICAL_SPEED;
    double period = PERIOD;

    Conveyor conveyor = Conveyor.getInstance();
    SpinConveyorVTimed cmd = new SpinConveyorVTimed(vSpeed, period, false);
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
    if (!m_parameterized) {
      vSpeed = SmartDashboard.getNumber("ConveyorVMotorSpeed", 0.5);
    }
    m_conveyor.spinVConveyor(vSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.spinVConveyor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!m_parameterized) {
      double period = SmartDashboard.getNumber("ConveyorVMotorTimeout", 5); // default of 5 seconds
      boolean timerExpired = m_timer.hasPeriodPassed(period);
      return timerExpired;
    } else {
      boolean timerExpired = m_timer.hasPeriodPassed(m_period);
      return timerExpired;
    }
  }
}
