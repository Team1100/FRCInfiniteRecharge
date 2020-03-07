/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This command spins the control panel to a certain color.

package frc.robot.commands.Spinner;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Spinner;
import edu.wpi.first.wpilibj.Timer;

public class SpinSpinnerToColor extends CommandBase {
  Spinner m_spinner;
  boolean m_detected_blue;
  int m_direction;
  String m_currentColor;
  double m_period;
  boolean m_timePassed;
  boolean m_parameterized;
  String m_color;
  double m_speed;
  Timer m_timer;
  /**
   * Creates a new SpinSpinnerToColor.
   */
  public SpinSpinnerToColor(String color, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Spinner.getInstance());
    m_spinner = Spinner.getInstance();
    m_detected_blue = false;
    m_direction = 1;
    m_parameterized = parameterized;
    m_color = color;
    m_speed = 0.2;
    m_period = 5;
    m_timer = new Timer();
  }
  
  public static void registerWithTestingDashboard() {
    Spinner spinner = Spinner.getInstance();
    SpinSpinnerToColor cmd = new SpinSpinnerToColor("Yellow", false);
    TestingDashboard.getInstance().registerCommand(spinner, "Timed", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();

    m_detected_blue = false;
    m_direction = 1;
    m_currentColor = m_spinner.getColor();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_parameterized == false){
      m_period = SmartDashboard.getNumber("SpinnerColorNotFoundTimeout",5.0);
      m_speed = SmartDashboard.getNumber("SpinnerMotorSpeed",0.2);
      m_color = SmartDashboard.getString("SpinnerTargetColor", "Yellow");
    }
    
    String m_actualColor = m_spinner.getColor();
    
    if (!m_actualColor.equals(m_currentColor)) {
      m_currentColor = m_actualColor;
      m_timer.reset();
      m_timer.start();
    }
    if (m_spinner.getColor().equals("Blue")) {
      m_detected_blue = true;
      if (!m_color.equals("Yellow")) {
        m_direction = -1;
      }
    }
    m_spinner.spin((m_direction*m_speed));
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
    if(m_parameterized == false){
      m_color = SmartDashboard.getString("SpinnerTargetColor", "Yellow");
    }
    return ((m_spinner.getColor().equals(m_color) && m_detected_blue) || m_timePassed);
  }
}
