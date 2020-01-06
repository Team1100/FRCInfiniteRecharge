/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drive extends SubsystemBase {
  WPI_TalonSRX frontLeft;
  WPI_TalonSRX frontRight;
  WPI_TalonSRX backLeft;
  WPI_TalonSRX backRight;

  SpeedControllerGroup left;
  SpeedControllerGroup right;

  DifferentialDrive drivetrain;
  
  /**
   * Creates a new Drive subsystem
   */
  public Drive() {
    frontLeft = new WPI_TalonSRX(RobotMap.D_FRONT_LEFT);
    frontRight = new WPI_TalonSRX(RobotMap.D_FRONT_RIGHT);
    backLeft = new WPI_TalonSRX(RobotMap.D_BACK_LEFT);
    backRight = new WPI_TalonSRX(RobotMap.D_BACK_RIGHT);
    
    left = new SpeedControllerGroup(frontLeft, backLeft);
    right = new SpeedControllerGroup(frontRight, backRight);

    drivetrain = new DifferentialDrive(left, right);
  }

  public void tankDrive(double leftSpeed, double rightSpeed){
    drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
