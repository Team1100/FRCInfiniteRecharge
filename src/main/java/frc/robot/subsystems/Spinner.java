/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.TestingDashboard;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Compressor;

public class Spinner extends SubsystemBase {
  public static Spinner spinner;
  private ColorSensorV3 m_colorSensor; 
  private VictorSP m_motor;
  private Compressor m_compressor;
  private DoubleSolenoid m_piston;

  /**
   * Creates a new Spinner.
   */
  public Spinner() {
    m_compressor = new Compressor(RobotMap.S_PISTON);
    m_compressor.setClosedLoopControl(true);
    m_piston = new DoubleSolenoid(RobotMap.S_PISTON_PORT0, RobotMap.S_PISTON_PORT1);
    m_colorSensor = new ColorSensorV3(RobotMap.S_COLOR_SENSOR);
    m_motor = new VictorSP(RobotMap.S_MOTOR);
    
  }

  public static Spinner getInstance() {
    if (spinner == null) {
      spinner = new Spinner();
      TestingDashboard.getInstance().registerSubsystem(spinner, "Spinner");
    }
    return spinner;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
