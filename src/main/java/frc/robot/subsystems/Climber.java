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

public class Climber extends SubsystemBase {
  
  private static Climber climber;
  private VictorSPX rightMotor;
  private VictorSPX leftMotor;

  /**
   * Creates a new Climber.
   */
  private Climber() {
    rightMotor = new VictorSPX(RobotMap.CL_MOTOR_LEFT);
    leftMotor = new  VictorSPX(RobotMap.CL_MOTOR_RIGHT);
  }

  public static Climber getInstance() {
    if (climber == null) {
      climber = new Climber();
      TestingDashboard.getInstance().registerSubsystem(climber, "Climber");
    }
    return climber;
  }

  public void setRight(double speed) {
    rightMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setLeft(double speed) {
    leftMotor.set(ControlMode.PercentOutput, speed);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
