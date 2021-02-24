// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Auto;

import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.PIDDriveDistance;
import frc.robot.commands.Drive.PIDTurnAngle;
import frc.robot.commands.Drive.TurnAngle;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class BarrelRacingPath extends SequentialCommandGroup {
  /** Creates a new BarrelRacingPath. */
  public BarrelRacingPath() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new PIDDriveDistance(144.0,true),
      new PIDTurnAngle(90, true));
      new PIDDriveDistance(60, true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(60, true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(57, true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(213, true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(60, true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(36, true),
      new PIDTurnAngle(45, true),
      new PIDDriveDistance(132, true),
      new PIDTurnAngle(45, true),
      new PIDDriveDistance(33, true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(60, true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(60, true),
      new PIDTurnAngle(90, true),
      new PIDDriveDistance(222, true)
    );
  }
  
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    BarrelRacingPath cmd = new BarrelRacingPath();
    TestingDashboard.getInstance().registerCommand(auto, "Auto", cmd);
  }
}

/**
This is the sequence for following the BarrelRacingPath
1. drive 12 feet-
1.5 turn 90 deg-
2. drive 5 feet-
2.5 turn 90 deg-
3. drive 5 feet-
3.5 turn 90 deg-
4. drive 4 feet 9 inches-
4.5 turn 90 deg-
5. drive 17 feet 6 inches-
5.5 turn 90 deg-
6. drive 5 feet-
6.5 turn 90 deg-
7. drive 3 feet-
7.5 turn 45 deg-
8. 11 feet-
8.5 turn 45 deg-
9. drive 2 feet 9 inches-
9.5 turn 90 deg-
10. drive 5 feet
10.5 turn 90 deg-
11. drive 5 feet-
11.5 turn 90 deg
12. drive 18 feet 6 inches 

*/
