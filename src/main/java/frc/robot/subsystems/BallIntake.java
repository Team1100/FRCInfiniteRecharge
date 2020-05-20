/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;
import frc.robot.TestingDashboard;

public class BallIntake extends SubsystemBase {
  private static BallIntake m_ballIntake;
  private static DoubleSolenoid m_piston;
  private VictorSPX m_intakeRoller;

  /**
   * Creates a new Ball Intake subsystem
   */
  private BallIntake() {
    m_intakeRoller = new VictorSPX(RobotMap.B_INTAKE_ROLLER);
    m_piston = new DoubleSolenoid(RobotMap.B_PCM_CAN, RobotMap.B_PISTON_PORT2, RobotMap.B_PISTON_PORT3);
  }

  public static BallIntake getInstance() {
    if (m_ballIntake == null) {
      m_ballIntake = new BallIntake();
      TestingDashboard.getInstance().registerSubsystem(m_ballIntake, "BallIntake");
    }
    return m_ballIntake;
  }

  public void spinIntakeRoller(double speed) {
    m_intakeRoller.set(ControlMode.PercentOutput, speed);
  }

  public DoubleSolenoid getPiston() {
    return m_piston;
  }

  public void lowerIntake() {
    m_piston.set(DoubleSolenoid.Value.kForward);
  }

  public void raiseIntake() {
    m_piston.set(DoubleSolenoid.Value.kReverse);
  }
 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
