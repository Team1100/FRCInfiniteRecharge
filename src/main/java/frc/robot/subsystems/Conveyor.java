/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.TestingDashboard;

public class Conveyor extends SubsystemBase {
  private static Conveyor m_conveyor;

  int m_numBallsStored;

  VictorSPX m_Hconveyor1;
  VictorSPX m_Hconveyor2;
  VictorSPX m_Vconveyor;

  DigitalInput m_ballIncoming;
  DigitalInput m_ballReadyToShoot;

  private Encoder m_conveyor1Encoder;
  private Encoder m_conveyor2Encoder;
  /**
   * Creates a new Conveyor.
   */
  public Conveyor() {
    m_numBallsStored = 0;

    m_Hconveyor1 = new VictorSPX(RobotMap.B_HCONVEYOR1);
    m_Hconveyor2 = new VictorSPX(RobotMap.B_HCONVEYOR2);
    m_Vconveyor = new VictorSPX(RobotMap.B_VCONVEYOR);

    m_ballIncoming = new DigitalInput(RobotMap.B_INCOMING);
    m_ballReadyToShoot = new DigitalInput(RobotMap.B_READYTOSHOOT);

  }

  public static Conveyor getInstance() {
    if (m_conveyor == null) {
      m_conveyor = new Conveyor();
      TestingDashboard.getInstance().registerSubsystem(m_conveyor, "Conveyor");
    }
    return m_conveyor;
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
