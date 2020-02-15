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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Turret extends SubsystemBase {
  private Victor m_turretMotor;
  private static Turret m_turret;
  private Encoder m_turretEncoder;
  private DigitalInput m_leftStop;
  private DigitalInput m_rightStop;

  /**
   * Creates a new Turret.
   */
  public Turret() {
    m_turretMotor = new Victor(RobotMap.T_MOTOR);
    m_leftStop = new DigitalInput(RobotMap.T_LEFT_STOP);
    m_rightStop = new DigitalInput(RobotMap.T_RIGHT_STOP);

  }

  public static Turret getInstance() {
    if (m_turret == null) {
      m_turret = new Turret();
      TestingDashboard.getInstance().registerSubsystem(m_turret, "Turret");

    }
    return m_turret;
  }

  
      
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
