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

  private VictorSPX m_HconveyerL;
  private VictorSPX m_HconveyerR;
  private VictorSPX m_Vconveyor;

  private DigitalInput m_ballIncoming;
  private DigitalInput m_ballReadyToShoot;

  private Encoder m_conveyor1Encoder;
  private Encoder m_conveyor2Encoder;
  
  /**
   * Creates a new Conveyor.
   */
  private Conveyor() {

    m_HconveyerL = new VictorSPX(RobotMap.C_HCONVEYOR_L);
    m_HconveyerR = new VictorSPX(RobotMap.C_HCONVEYOR_R);
    m_Vconveyor = new VictorSPX(RobotMap.C_VCONVEYOR);

    m_ballIncoming = new DigitalInput(RobotMap.C_INCOMING);
    m_ballReadyToShoot = new DigitalInput(RobotMap.C_READYTOSHOOT);
  }

  public static Conveyor getInstance() {
    if (m_conveyor == null) {
      m_conveyor = new Conveyor();
      TestingDashboard.getInstance().registerSubsystem(m_conveyor, "Conveyor");
    }
    return m_conveyor;
  }

  public void spinHConveyors(double speed) {
    spinHConveyorL(speed);
    spinHConveyorR(speed);
  }

  public void spinHConveyorL(double speed) {
    // Note that positive is the correct direction for this belt
    m_HconveyerL.set(ControlMode.PercentOutput, speed);
  }

  public void spinHConveyorR(double speed) {
    // Note that negative is the correct direction for this belt
    m_HconveyerR.set(ControlMode.PercentOutput, -speed);
  }

  public void spinVConveyor(double speed) {
    m_Vconveyor.set(ControlMode.PercentOutput, speed);
  }

  public void spinBothConveyors(double hSpeedL, double hSpeedR, double vSpeed) {
    spinHConveyorL(hSpeedL);
    spinHConveyorR(hSpeedR);
    spinVConveyor(vSpeed);
  }

  public boolean ballIncoming() {
    return !m_ballIncoming.get();
  }

  public boolean ballReadyToShoot() {
    return !m_ballReadyToShoot.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("ballIncoming", ballIncoming());
    SmartDashboard.putBoolean("ballReadyToShoot", ballReadyToShoot());
  }
}
