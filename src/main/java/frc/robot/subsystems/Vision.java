/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.TestingDashboard;

public class Vision extends SubsystemBase {
  public static NetworkTable nt;
  public NetworkTableEntry yaw, isValid, targetPose;
  private static Vision vision;
  private double[] defaultDoubleArray = {4500,5500};
  /**
   * Creates a new Vision.
   */
  private Vision() {
    nt = NetworkTableInstance.getDefault().getTable("chameleon-vision").getSubTable("HD Pro Webcam C920");
    yaw = nt.getEntry("targetYaw");
    isValid = nt.getEntry("isValid");
    targetPose = nt.getEntry("targetPose");
  }

  public static Vision getInstance() {
    if (vision == null) {
      vision = new Vision();
      TestingDashboard.getInstance().registerSubsystem(vision, "Vision");
    }
    return vision;
  }

  public NetworkTableEntry getYaw(){
    return yaw;
  }

  public NetworkTableEntry getIsValid(){
    return isValid;
  }

  public NetworkTableEntry getTargetPose(){
    return targetPose;
  }

  public double[] getTargetPoseArray(){
    return targetPose.getDoubleArray(defaultDoubleArray);
  }

  public double getXFeet(){
    return (getTargetPoseArray()[0] * 3.28084);
  }

  public double getYFeet(){
    return (getTargetPoseArray()[1] * 3.28084);
  }

  public double getDistance(){
    return Math.sqrt(Math.pow(getXFeet(),2) + Math.pow(getYFeet(),2));
  }

  public double[] calculateRPM(){
    double dist = getDistance();
    double topVal = 82.759*(dist) + 2931.0345;
    double botVal = -68.966*(dist) + 6224.138;
    double[] doubleArray = new double[]{topVal, botVal};
    //return doubleArray;
    return defaultDoubleArray;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumberArray("pose", getTargetPoseArray());
  }
}
