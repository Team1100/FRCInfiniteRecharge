/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.TestingDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

  /**
   * Creates a new Ball Intake subsystem
   */
public class BallIntake extends SubsystemBase {

  public static BallIntake m_ballIntake;

  WPI_TalonSRX m_intakeRoller;
  WPI_TalonSRX m_conveyor1;

  DigitalInput m_ballIncoming;
  DigitalInput m_ballReadyToShoot;

  private Encoder m_conveyor1Encoder;

  private BallIntake() {
    m_intakeRoller = new WPI_TalonSRX(RobotMap.B_INTAKE_ROLLER);
    m_conveyor1 = new WPI_TalonSRX(RobotMap.B_CONVEYOR1);
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
    m_intakeRoller.set(speed);
  }

  public void spinConveyor1(double speed){
    m_conveyor1.set(speed);
  }

  public boolean getBallIncoming(){
    return !m_ballIncoming.get();
  }

  public boolean getBallReadyToShoot(){
    return !m_ballReadyToShoot.get();
  }

 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
