/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TestingDashboard;

public class Auto extends SubsystemBase {

  public static Auto auto;

  /**
   * Creates a new Auto.
   */
  public Auto() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public static Auto getInstance(){
    if (auto == null){
      auto = new Auto();
      TestingDashboard.getInstance().registerSubsystem(auto, "Auto");
    }
    return auto;
  }

}
