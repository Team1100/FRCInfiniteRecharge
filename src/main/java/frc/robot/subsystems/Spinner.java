/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
 * This subsystem includes a motor, a color sensor, a wheel, a piston, and an encoder.
 * It is used to spin the control panel three times or spin it to a specific color.
*/
package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.TestingDashboard;
import frc.robot.RobotMap;

public class Spinner extends SubsystemBase {
  private static Spinner spinner; 

  private VictorSP m_motor;

  private Compressor m_compressor;

  private DoubleSolenoid m_piston;

  private ColorSensorV3 m_colorSensor;

  private final ColorMatch m_colorMatcher;

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  /**
   * Creates a new Spinner.
   */
  private Spinner() {
    //m_compressor = new Compressor(RobotMap.S_PISTON);
    //m_compressor.setClosedLoopControl(true);
    
    m_piston = new DoubleSolenoid(RobotMap.S_PCM_CAN, RobotMap.S_PISTON_PORT0, RobotMap.S_PISTON_PORT1);
    
    m_motor = new VictorSP(RobotMap.S_MOTOR);
    
    m_colorSensor = new ColorSensorV3(RobotMap.S_COLOR_SENSOR);
    
    m_colorMatcher = new ColorMatch();
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }

  public static Spinner getInstance() {
    if (spinner == null) {
      spinner = new Spinner();
      TestingDashboard.getInstance().registerSubsystem(spinner, "Spinner");
    }
    return spinner;
  }

  public void spin(double speed) {
    m_motor.set(speed);
  }

  public String getColor() {
    String colorString;
    Color detectedColor = m_colorSensor.getColor();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }
    SmartDashboard.putString("SpinnerActualColor", colorString);
    return colorString;
  }

  public DoubleSolenoid getPiston() {
		return m_piston;
  }

  public void extendSpinnerArm() {
    m_piston.set(DoubleSolenoid.Value.kForward);
  }

  public void retractSpinnerArm() {
    m_piston.set(DoubleSolenoid.Value.kReverse);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
