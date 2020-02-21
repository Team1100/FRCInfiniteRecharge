/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.TestingDashboard;

public class Climber extends SubsystemBase {
  private static Climber climber;

  /**
   * Creates a new Climber.
   */
  private Climber() {

  }

  public static Climber getInstance() {
    if (climber == null) {
      climber = new Climber();
      TestingDashboard.getInstance().registerSubsystem(climber, "Climber");
    }
    return climber;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
