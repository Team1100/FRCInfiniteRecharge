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

import frc.robot.Constants;
import frc.robot.TestingDashboard;

public class Vision extends SubsystemBase {
  private static Vision vision;
  private static Shooter m_shooter;
  NetworkTable table;

  public final static double INITIAL_SPEED = 0.6;

  /**
   * Creates a new Vision.
   */
  private Vision() {
    table = NetworkTableInstance.getDefault().getTable("Shuffleboard/Vision");
    m_shooter = Shooter.getInstance();
  }

  public static Vision getInstance() {
    if (vision == null) {
      vision = new Vision();
      TestingDashboard.getInstance().registerSubsystem(vision, "Vision");
      TestingDashboard.getInstance().registerNumber(vision, "Turn", "InitialAngle", 0);
      TestingDashboard.getInstance().registerNumber(vision, "Turn", "FinalAngle", 0);
      TestingDashboard.getInstance().registerNumber(vision, "Turn", "SpeedWhenTurning", INITIAL_SPEED);
      TestingDashboard.getInstance().registerNumber(m_shooter, "Vision", "Zone", 0);
      
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

  public int getZone() {
    double distance = table.getEntry("distance").getDouble(0);
    int zone = 0;
    if (distance > Constants.kZonePurpleStart + Constants.kRobotLength) {
      zone = Constants.kZonePurple;
    } else if (distance > Constants.kZoneRedStart + Constants.kRobotLength) {
      zone = Constants.kZoneRed;
    } else if (distance > Constants.kZoneBlueStart + Constants.kRobotLength) {
      zone = Constants.kZoneBlue;
    } else if (distance > Constants.kZoneYellowStart + Constants.kRobotLength) {
      zone = Constants.kZoneYellow;
    } else {
      zone = Constants.kZoneGreen;
    }
    return zone;
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
    TestingDashboard.getInstance().updateNumber(m_shooter, "Zone", getZone());
  }
}
