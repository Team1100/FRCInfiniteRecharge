// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TestingDashboard;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.PIDDriveDistance;
import frc.robot.commands.Drive.PIDTurnAngle;
import frc.robot.commands.Drive.TurnAngle;
import frc.robot.subsystems.Auto;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveSquareAuto extends SequentialCommandGroup {
  /** Creates a new DriveSquareAuto. */
  public DriveSquareAuto() {
    double distance = 60; // inches
    double speed = .5; // % power
    double angle = 90.0; // degrees

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(/**new DriveDistance(distance, speed,true),
                new TurnAngle(angle, speed, true),
                new DriveDistance(distance, speed,true),
                new TurnAngle(angle,speed, true),
                new DriveDistance(distance, speed,true),
                new TurnAngle(angle,speed, true),
                new DriveDistance(distance, speed,true),
                new TurnAngle(angle,speed, true));*/
                new PIDDriveDistance(144.0,true),
                new PIDTurnAngle(90, true),
                new PIDDriveDistance(60.0, true),
                new PIDTurnAngle(90, true),
                new PIDDriveDistance(72.0, true),
                new PIDTurnAngle(90, true),
                new PIDDriveDistance(57.0, true),
                new PIDTurnAngle(90, true));
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    DriveSquareAuto cmd = new DriveSquareAuto();
    TestingDashboard.getInstance().registerCommand(auto, "Auto", cmd);
  }
}
