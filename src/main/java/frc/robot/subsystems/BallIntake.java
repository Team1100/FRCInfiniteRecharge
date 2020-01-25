/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.TestingDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

  /**
   * Creates a new Ball Intake subsystem
   */
public class BallIntake extends SubsystemBase {

  public static BallIntake ballIntake;

  WPI_TalonSRX intakeRoller;
  WPI_TalonSRX conveyor1;
  WPI_TalonSRX conveyor2;

  private Encoder conveyor1Encoder;
  private Encoder conveyor2Encoder;

  private BallIntake() {
    intakeRoller = new WPI_TalonSRX(RobotMap.B_INTAKE_ROLLER);
    conveyor1 = new WPI_TalonSRX(RobotMap.B_CONVEYOR1);
    conveyor2 = new WPI_TalonSRX(RobotMap.B_CONVEYOR2);
  }

  public static BallIntake getInstance() {
    if (ballIntake == null) {
      ballIntake = new BallIntake();
      TestingDashboard.getInstance().registerSubsystem(ballIntake, "BallIntake");
    }
    return ballIntake;
  }

  public void spinIntakeRoller(double speed){
    intakeRoller.set(speed);
  }

  public void spinConveyor1(double speed){
    conveyor1.set(speed);
  }

  public void spinConveyor2(double speed){
    conveyor2.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
