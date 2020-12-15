/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;
import frc.robot.TestingDashboard;

public class Turret extends SubsystemBase {
  private static Turret m_turret;
  private VictorSPX m_turretMotor;
  private Encoder m_turretEncoder;
  private DigitalInput m_turretLimit;
  private AnalogInput m_potentiometer;
  public static final double POTENTIOMETER_MIN = 0.0; // voltage
  public static final double POTENTIOMETER_MAX = 5.0; // voltage
  public static final double MIN_ANGLE = 0.0;
  public static final double MAX_ANGLE = 360.0;
  public static final double VOLTS_PER_DEGREE = 0.01;

  /**
   * Creates a new Turret.
   */
  private Turret() {
    m_turretMotor = new VictorSPX(RobotMap.T_MOTOR);
    m_turretLimit = new DigitalInput(RobotMap.T_LIMIT);
    m_turretEncoder = new Encoder(RobotMap.T_ENCODER_A, RobotMap.T_ENCODER_B);
    m_potentiometer = new AnalogInput(RobotMap.T_POTENTIOMETER);
  }

  public static Turret getInstance() {
    if (m_turret == null) {
      m_turret = new Turret();
      TestingDashboard.getInstance().registerSubsystem(m_turret, "Turret");
      TestingDashboard.getInstance().registerNumber(m_turret, "PIDRotation", "TurretSetpoint", 180);
      TestingDashboard.getInstance().registerNumber(m_turret, "Potentiometer", "PotVoltage", POTENTIOMETER_MIN);
      TestingDashboard.getInstance().registerNumber(m_turret, "Potentiometer", "TurretAngle", MIN_ANGLE);
    }
    return m_turret;
  }

  public void spinTurretMotor(double speed) {
    if (speed > 0 && getTurretLimit()) {
      speed = 0;
    }
    m_turretMotor.set(ControlMode.PercentOutput, -speed);
  }

  public boolean getTurretLimit() {
    return !m_turretLimit.get();
  }

  public Encoder getEncoder() {
    return m_turretEncoder;
  }

  public double getPotentiometerValue() {
    return m_potentiometer.getVoltage();
  }

  public double getRotationAngle() {
    double volt = m_potentiometer.getVoltage();
    double angle = MIN_ANGLE + (volt - POTENTIOMETER_MIN) * VOLTS_PER_DEGREE;
    return angle;
  }
      
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("TurretLimit", getTurretLimit());
    SmartDashboard.putNumber("TurretEnc", getEncoder().getDistance());
    TestingDashboard.getInstance().updateNumber(m_turret, "PotVoltage", getPotentiometerValue());
    TestingDashboard.getInstance().updateNumber(m_turret, "TurretAngle", getRotationAngle());
    
    if (getTurretLimit()) {
      m_turretEncoder.reset();
    }
  }
}
