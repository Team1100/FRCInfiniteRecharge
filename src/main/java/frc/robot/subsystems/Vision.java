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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

import frc.robot.TestingDashboard;

public class Vision extends SubsystemBase {
  private static Vision vision;
  NetworkTable table;

  public final static double INITIAL_SPEED = 0.3;

  /**
   * Creates a new Vision.
   */
  private Vision() {
    table = NetworkTableInstance.getDefault().getTable("Shuffleboard/Vision");
  }

  public static Vision getInstance() {
    if (vision == null) {
      vision = new Vision();
      TestingDashboard.getInstance().registerSubsystem(vision, "Vision");
      TestingDashboard.getInstance().registerNumber(vision, "Turn", "InitialAngle", 0);
      TestingDashboard.getInstance().registerNumber(vision, "Turn", "FinalAngle", 0);
      TestingDashboard.getInstance().registerNumber(vision, "Turn", "SpeedWhenTurning", 0);
      
      Shuffleboard.getTab("Vision")
          .add("hueMin", 0)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", 0, "max", 255)) // specify widget properties here
          .getEntry();
      Shuffleboard.getTab("Vision")
          .add("hueMax", 255)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", 0, "max", 255)) // specify widget properties here
          .getEntry();
      Shuffleboard.getTab("Vision")
          .add("satMin", 0)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", 0, "max", 255)) // specify widget properties here
          .getEntry();
      Shuffleboard.getTab("Vision")
          .add("satMax", 255)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", 0, "max", 255)) // specify widget properties here
          .getEntry();
      Shuffleboard.getTab("Vision")
          .add("valMin", 0)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", 0, "max", 255)) // specify widget properties here
          .getEntry();
      Shuffleboard.getTab("Vision")
          .add("valMax", 255)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", 0, "max", 255)) // specify widget properties here
          .getEntry();
    }
    return vision;
  }

  public double getTargetOffset() {
    return table.getEntry("offset").getDouble(0);
  }
  public boolean isTargetFound() {
    if (table.getEntry("targetDetected").getDouble(0) == 0)
      return false;
    else
      return true;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
