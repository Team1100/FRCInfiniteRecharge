/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This command spins the control panel three times. 

package frc.robot.commands.Spinner;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Spinner;
import edu.wpi.first.wpilibj.Timer;

public class SpinSpinner3Times extends CommandBase {
  /**
   * Creates a new SpinSpinner3Times.
   */
  static final int NUM_ROTATIONS = 3;
  static final int NUM_COLORS_PER_ROTATION = 2;
  Spinner m_spinner;
  String m_startColor;
  String m_currentColor;
  int m_counter;
  Timer m_timer;
  double m_period;
  boolean m_timePassed;

  public SpinSpinner3Times() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Spinner.getInstance());
    m_spinner = Spinner.getInstance();
    m_counter = 0;
    m_timer = new Timer();
  }

  public static void registerWithTestingDashboard() {
    Spinner spinner = Spinner.getInstance();
    SpinSpinner3Times cmd = new SpinSpinner3Times();
    TestingDashboard.getInstance().registerCommand(spinner, "Basic", cmd);
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();

    m_startColor = "Blue";
    m_currentColor = m_startColor;
    m_counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_period = SmartDashboard.getNumber("SpinnerColorNotFoundTimeout", 5.0);
    double speed = SmartDashboard.getNumber("SpinnerMotorSpeed",0.2);    
    String color = m_spinner.getColor();
    if (!color.equals(m_currentColor)) {
      m_currentColor = color;
      m_timer.reset();
      m_timer.start();
      if (m_currentColor.equals(m_startColor)) {
        m_counter += 1;

      }
    }
    m_spinner.spin(speed);
    SmartDashboard.putNumber("m_counter", m_counter);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_spinner.spin(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    m_timePassed = m_timer.hasPeriodPassed(m_period);
    int target_count = NUM_ROTATIONS*NUM_COLORS_PER_ROTATION + 1;  
    return ((m_counter == target_count) || (m_currentColor.equals("Unknown")) || (m_timePassed == true));
  }
}
