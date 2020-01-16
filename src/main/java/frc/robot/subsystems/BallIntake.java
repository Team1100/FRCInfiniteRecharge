/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class BallIntake extends SubsystemBase {

  WPI_TalonSRX intakeRoller;
  WPI_TalonSRX conveyer1;
  WPI_TalonSRX conveyer2;

  public BallIntake() {
    
    intakeRoller = new WPI_TalonSRX(RobotMap.B_INTAKE_ROLLER);
    conveyer1 = new WPI_TalonSRX(RobotMap.B_CONVEYER1);
    conveyer2 = new WPI_TalonSRX(RobotMap.B_CONVEYER2);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
