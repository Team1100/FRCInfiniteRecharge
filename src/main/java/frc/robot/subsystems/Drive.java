/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;
import frc.robot.TestingDashboard;

import com.kauailabs.navx.frc.AHRS;

public class Drive extends SubsystemBase {
  VictorSPX frontLeft;
  VictorSPX frontRight;
  WPI_TalonSRX backLeft;
  WPI_TalonSRX backRight;

  private DifferentialDrive drivetrain;

  private AHRS ahrs;
  
  private static Drive drive;

  /**
   * Creates a new Drive subsystem
   */
  private Drive() {
    frontLeft = new VictorSPX(RobotMap.D_FRONT_LEFT);
    frontRight = new VictorSPX(RobotMap.D_FRONT_RIGHT);
    backLeft = new WPI_TalonSRX(RobotMap.D_BACK_LEFT);
    backRight = new WPI_TalonSRX(RobotMap.D_BACK_RIGHT);
    
    frontLeft.follow(backLeft);
    frontRight.follow(backRight);
    frontRight.setInverted(true);


    drivetrain = new DifferentialDrive(backLeft, backRight);

    ahrs = new AHRS(RobotMap.D_NAVX);
  }

  /**
   * Used outside of the Drive subsystem to return an instance of Drive subsystem.
   * @return Returns instance of Drive subsystem formed from constructor.
   */
  public static Drive getInstance() {
    if (drive == null) {
      drive = new Drive();
      TestingDashboard.getInstance().registerSubsystem(drive, "Drive");
    }
    return drive;
  }

  protected double getYaw() {
    return ahrs.getYaw();
  }

  protected double getPitch() {
    return ahrs.getPitch();
  }

  protected double getRoll() {
    return ahrs.getRoll();
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  /**
   * Controls the drivetrain with raw voltage values
   *
   * @param leftVoltage  voltage fed to left side
   * @param rightVoltage voltage fed to right side
   */
  public void tankDriveVoltage(double leftVoltage, double rightVoltage){
    backLeft.setVoltage(leftVoltage);
    backRight.setVoltage(rightVoltage);
    drivetrain.feed();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Yaw",getYaw());
    SmartDashboard.putNumber("Pitch",getPitch());
    SmartDashboard.putNumber("Roll",getRoll());
  }

  @Override
  public void setDefaultCommand(Command defaultCommand) {
    // TODO Auto-generated method stub
    super.setDefaultCommand(defaultCommand);
  }
}
