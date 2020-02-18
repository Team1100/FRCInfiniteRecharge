/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.TestingDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

  /**
   * Creates a new Ball Intake subsystem
   */
public class BallIntake extends SubsystemBase {

  public static BallIntake m_ballIntake;

  int m_numBallsStored;

  VictorSPX m_intakeRoller;
  VictorSPX m_Hconveyor1;
  VictorSPX m_Hconveyor2;
  VictorSPX m_Vconveyor;

  DigitalInput m_ballIncoming;
  DigitalInput m_ballReadyToShoot;

  private Encoder m_conveyor1Encoder;
  private Encoder m_conveyor2Encoder;

  private BallIntake() {
    m_numBallsStored = 0;

    m_intakeRoller = new VictorSPX(RobotMap.B_INTAKE_ROLLER);
    m_Hconveyor1 = new VictorSPX(RobotMap.B_HCONVEYOR1);
    m_Hconveyor2 = new VictorSPX(RobotMap.B_HCONVEYOR2);
    m_Vconveyor = new VictorSPX(RobotMap.B_VCONVEYOR);

    m_ballIncoming = new DigitalInput(RobotMap.B_INCOMING);
    m_ballReadyToShoot = new DigitalInput(RobotMap.B_READYTOSHOOT);
  }

  public static BallIntake getInstance() {
    if (m_ballIntake == null) {
      m_ballIntake = new BallIntake();
      TestingDashboard.getInstance().registerSubsystem(m_ballIntake, "BallIntake");
    }
    return m_ballIntake;
  }

  public void spinIntakeRoller(double speed){
   m_intakeRoller.set(ControlMode.PercentOutput, speed);
  }

  public void spinHConveyor(double speed){
    m_Hconveyor1.set(ControlMode.PercentOutput, -speed);
    m_Hconveyor2.set(ControlMode.PercentOutput, -speed);
  }

  public void spinVConveyor(double speed){
    m_Vconveyor.set(ControlMode.PercentOutput, -speed);
  }

  public void spinBothConveyors(double hSpeed, double vSpeed){
    m_Hconveyor1.set(ControlMode.PercentOutput, -hSpeed);
    m_Hconveyor2.set(ControlMode.PercentOutput, -hSpeed);
    m_Vconveyor.set(ControlMode.PercentOutput, -vSpeed);

  }

  public boolean ballIncoming(){
    return !m_ballIncoming.get();
  }

  public boolean ballReadyToShoot(){
    return !m_ballReadyToShoot.get();
  }

  public void decrementBallsStored(){
    m_numBallsStored -= 1;
  }

  public void incrementBallsStored(){
    m_numBallsStored += 1;
  }

 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("ballIncoming", ballIncoming());
    SmartDashboard.putBoolean("ballReadyToShoot", ballReadyToShoot());
  }
}
