/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.TestingDashboard;

import com.kauailabs.navx.frc.AHRS;

public class Drive extends SubsystemBase {
  VictorSPX frontLeft;
  VictorSPX frontRight;
  WPI_TalonSRX backLeft;
  WPI_TalonSRX backRight;

  Encoder leftEncoder, rightEncoder;

  final double PULSE_PER_FOOT = 1300;
  final double PULSE_PER_METER = 4265.1;

  private DifferentialDrive drivetrain;

  private AHRS ahrs;
  
  private static Drive drive;

  private DifferentialDriveKinematics m_kinematics; 
  private DifferentialDriveOdometry m_odometry;
  private SimpleMotorFeedforward m_feedforward;

  private PIDController leftPidController = new PIDController(Constants.kPDriveVel, 0, 0);
  private PIDController rightPidController = new PIDController(Constants.kPDriveVel, 0, 0);

  
  private Pose2d pose;

  /**
   * Creates a new Drive subsystem
   */
  private Drive() {
    frontLeft = new VictorSPX(RobotMap.D_FRONT_LEFT);
    frontRight = new VictorSPX(RobotMap.D_FRONT_RIGHT);
    backLeft = new WPI_TalonSRX(RobotMap.D_BACK_LEFT);
    backRight = new WPI_TalonSRX(RobotMap.D_BACK_RIGHT);

    leftEncoder = new Encoder(RobotMap.D_LEFT_ENCODER_A, RobotMap.D_LEFT_ENCODER_B);
    rightEncoder = new Encoder(RobotMap.D_RIGHT_ENCODER_A, RobotMap.D_RIGHT_ENCODER_B);
    leftEncoder.setDistancePerPulse(1/PULSE_PER_METER);
    rightEncoder.setDistancePerPulse(1/PULSE_PER_METER);

    

    frontLeft.follow(backLeft);
    frontRight.follow(backRight);
    frontRight.setInverted(true);


    drivetrain = new DifferentialDrive(backLeft, backRight);

    ahrs = new AHRS(RobotMap.D_NAVX);

    m_kinematics = new DifferentialDriveKinematics(Constants.kTrackwidthMeters);
    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getRoll()));
    m_feedforward = new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter, Constants.kaVoltSecondsSquaredPerMeter);
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

  //Drive Methods
  public void tankDrive(double leftSpeed, double rightSpeed) {
    drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  /**
   * Controls the drivetrain with raw voltage values
   *
   * @param leftVoltage  voltage fed to left side
   * @param rightVoltage voltage fed to right side
   */
  public void tankDriveVolts(double leftVoltage, double rightVoltage){
    backLeft.setVoltage(leftVoltage);
    backRight.setVoltage(rightVoltage);
    drivetrain.feed();
  }

  public void arcadeDrive(double fwd, double rot) {
    drive.arcadeDrive(fwd, rot);
  }

  public void setMaxOutput(double maxOutput) {
    drive.setMaxOutput(maxOutput);
  }

  //Sensor Methods

  //AHRS Methods
  public double getYaw() {
    return ahrs.getYaw();
  }

  public double getPitch() {
    return ahrs.getPitch();
  }

  public double getRoll() {
    return ahrs.getRoll();
  }

  public void zeroHeading() {
    ahrs.reset();
  }

  public Rotation2d getHeading() {
    return Rotation2d.fromDegrees(-ahrs.getYaw());
  }

  //Encoder Methods
  public Encoder getLeftEncoder(){
    return leftEncoder;
  }

  public Encoder getRightEncoder(){
    return rightEncoder;
  }

  //Kinematics Methods
  public Pose2d getPose() {
    //return m_odometry.getPoseMeters();
    return pose;
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
  }

  public void resetOdometry(Pose2d pose) {
    leftEncoder.reset();
    rightEncoder.reset();
    m_odometry.resetPosition(pose, getHeading());
  }

  /**
   * @return the odometry
   */
  public DifferentialDriveOdometry getOdometry() {
    return m_odometry;
  }

  /**
   * @return the kinematics
   */
  public DifferentialDriveKinematics getKinematics() {
    return m_kinematics;
  }

  public SimpleMotorFeedforward getFeedforward(){
    return m_feedforward;
  }

  /**
   * @return the leftPidController
   */
  public PIDController getLeftPidController() {
    return leftPidController;
  }

  /**
   * @return the rightPidController
   */
  public PIDController getRightPidController() {
    return rightPidController;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    pose = m_odometry.update(getHeading(), leftEncoder.getDistance(), rightEncoder.getDistance());

    SmartDashboard.putNumber("Yaw",getYaw());
    SmartDashboard.putString("Heading", getHeading().toString());
    SmartDashboard.putNumber("Left Dist", Drive.getInstance().getLeftEncoder().getDistance());
    SmartDashboard.putNumber("Right Dist", Drive.getInstance().getRightEncoder().getDistance());
    SmartDashboard.putNumber("Left Rate", Drive.getInstance().getLeftEncoder().getRate());
    SmartDashboard.putNumber("Right Rate", Drive.getInstance().getRightEncoder().getRate());
  }

  @Override
  public void setDefaultCommand(Command defaultCommand) {
    // TODO Auto-generated method stub
    super.setDefaultCommand(defaultCommand);
  }
}
