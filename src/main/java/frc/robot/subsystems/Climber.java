/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.TestingDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber extends SubsystemBase {
  private static Climber climber;
  private VictorSPX rightMotor;
  private VictorSPX leftMotor;
  private static DoubleSolenoid m_piston;

  /**
   * Creates a new Climber.
   */
  private Climber() {
    leftMotor = new VictorSPX(RobotMap.CL_MOTOR_LEFT);
    rightMotor = new  VictorSPX(RobotMap.CL_MOTOR_RIGHT);
    m_piston = new DoubleSolenoid(RobotMap.CL_PCM_CAN, 
    RobotMap.CL_PISTON_PORT2, RobotMap.CL_PISTON_PORT5);
  }

  public static Climber getInstance() {
    if (climber == null) {
      climber = new Climber();
      TestingDashboard.getInstance().registerSubsystem(climber, "Climber");
    }
    return climber;
  }

  public void climb(double speed) {
    leftMotor.set(ControlMode.PercentOutput, -speed);
    rightMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void release(double speed) {
    leftMotor.set(ControlMode.PercentOutput, speed);
    rightMotor.set(ControlMode.PercentOutput, speed);
  }

  
  public DoubleSolenoid getPiston() {
    return m_piston;
  }

  public void lowerClimber() {
    m_piston.set(DoubleSolenoid.Value.kForward);
  }

  public void raiseClimber() {
    m_piston.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
