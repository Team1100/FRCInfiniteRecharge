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
  /**
   * Creates a new SpinConveyor1Timed.
   */

   Timer m_timer;
   Conveyor m_conveyor;
   double m_Vperiod, m_Hperiod;
   double m_hSpeed1, m_hSpeed2, m_vSpeed;

  public SpinBothConveyorsTimed(double horizontalSpeed1, double horizontalSpeed2, double verticalSpeed, double horizontalPeriod, double verticalPeriod) {
    // Use addRequirements() here to declare subsystem dependencies.
    
    addRequirements(Conveyor.getInstance());
    m_conveyor = Conveyor.getInstance();
    m_timer = new Timer();

    m_hSpeed1 = horizontalSpeed1;
    m_hSpeed2 = horizontalSpeed2;
    m_vSpeed = verticalSpeed;
  }

  public static void registerWithTestingDashboard() {
    double vSpeed = SmartDashboard.getNumber("ConveyorVMotorSpeed",0.5);
    double hSpeed1 = SmartDashboard.getNumber("ConveyorHMotor1Speed",0.5);
    double hSpeed2 = SmartDashboard.getNumber("ConveyorHMotor2Speed",0.5);
    double Vperiod = SmartDashboard.getNumber("ConveyorVMotorTimeout", 5); // default of 5 seconds
    double Hperiod = SmartDashboard.getNumber("ConveyorHMotorTimeout", 5); // default of 5 seconds
     

    Conveyor conveyor = Conveyor.getInstance();
    SpinBothConveyorsTimed cmd = new SpinBothConveyorsTimed(hSpeed1, hSpeed2, vSpeed, Vperiod, Hperiod);
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
    m_conveyor.spinBothConveyors(m_hSpeed1, m_hSpeed2, m_vSpeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.spinBothConveyors(0,0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean vTimerExpired = m_timer.hasPeriodPassed(m_Vperiod);
    boolean hTimerExpired = m_timer.hasPeriodPassed(m_Hperiod);
    return (vTimerExpired || hTimerExpired);
  }
}
