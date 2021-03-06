// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TestingDashboard;
import frc.robot.commands.Drive.PIDDriveDistance;
import frc.robot.commands.Drive.PIDTurnAngle;
import frc.robot.subsystems.Auto;


public class SlalomPath extends SequentialCommandGroup {

  public SlalomPath() {
  
    addCommands(
      new PIDDriveDistance(46.5,true),
      new PIDTurnAngle(-90, true),
      new PIDDriveDistance(58,true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(192,true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(58,true),
      new PIDTurnAngle(-90, true),
      new PIDDriveDistance(60,true),
      new PIDTurnAngle(-90, true),
      new PIDDriveDistance(64.5,true),
      new PIDTurnAngle(-90, true),
      new PIDDriveDistance(64.5,true),
      new PIDTurnAngle(-90, true),
      new PIDDriveDistance(64.5,true),
      new PIDTurnAngle(-90, true),
      new PIDDriveDistance(180,true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(67,true),
      new PIDTurnAngle(-90, true),
      new PIDDriveDistance(54,true)
      );
  }
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    SlalomPath cmd = new SlalomPath();
    TestingDashboard.getInstance().registerCommand(auto, "Auto", cmd);
  }
}
     
