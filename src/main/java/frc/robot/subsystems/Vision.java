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
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.TestingDashboard;

public class Vision extends SubsystemBase {
  public static NetworkTable nt;
  public NetworkTableEntry yaw, isValid;
  private static Vision vision;

  /**
   * Creates a new Vision.
   */
  private Vision() {
    nt = NetworkTableInstance.getDefault().getTable("chameleon-vision").getSubTable("HD Pro Webcam C920");
    yaw = nt.getEntry("targetYaw");
    isValid = nt.getEntry("isValid");
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
