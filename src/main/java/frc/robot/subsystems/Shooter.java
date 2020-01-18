/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TestingDashboard;

public class Shooter extends SubsystemBase {
  public static Shooter shooter;

  /**
   * Creates a new Shooter.
   */
  private Shooter() {

  }

  public static Shooter getInstance() {
    if (shooter == null) {
      shooter = new Shooter();
      TestingDashboard.getInstance().registerSubsystem(shooter, "Shooter");
    }
    return shooter;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
