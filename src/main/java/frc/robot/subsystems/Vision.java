/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.TestingDashboard;

public class Vision extends SubsystemBase {
  private static Vision vision;

  /**
   * Creates a new Vision.
   */
  private Vision() {

  }

  public static Vision getInstance() {
    if (vision == null) {
      vision = new Vision();
      TestingDashboard.getInstance().registerSubsystem(vision, "Vision");
    }
    return vision;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
