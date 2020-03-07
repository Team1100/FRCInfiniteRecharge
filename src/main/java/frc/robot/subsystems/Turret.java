/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;
import frc.robot.TestingDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends SubsystemBase {
  private VictorSPX m_turretMotor;
  
  private static Turret m_turret;
  
  private Encoder m_turretEncoder;

  private DigitalInput m_turretLimit;

  /**
   * Creates a new Turret.
   */
  private Turret() {
    m_turretMotor = new VictorSPX(RobotMap.T_MOTOR);
    m_turretLimit = new DigitalInput(RobotMap.T_LIMIT);
    m_turretEncoder = new Encoder(RobotMap.T_ENCODER_A, RobotMap.T_ENCODER_B);
  }

  public static Turret getInstance() {
    if (m_turret == null) {
      m_turret = new Turret();
      TestingDashboard.getInstance().registerSubsystem(m_turret, "Turret");
    }
    return m_turret;
  }

  public void spinTurretMotor(double speed) {
    if(speed > 0 && getTurretLimit()){
      speed = 0;
    }
    m_turretMotor.set(ControlMode.PercentOutput, -speed);
  }

  public boolean getTurretLimit(){
    return !m_turretLimit.get();
  }

  public Encoder getEncoder(){
    return m_turretEncoder;
  }
      
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("TurretLimit", getTurretLimit());
    SmartDashboard.putNumber("TurretEnc", getEncoder().getDistance());

    if(getTurretLimit()){
      m_turretEncoder.reset();
    }
  }
}
