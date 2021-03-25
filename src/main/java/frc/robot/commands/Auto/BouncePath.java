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
public class BouncePath extends SequentialCommandGroup {
  /** Creates a new BouncePath. */
  public BouncePath() {
    // distance is in inches
    // N/A % power
    // angle setpoint is in degrees

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new PIDDriveDistance(144.0,true),
    new PIDDriveDistance(50, true),
    new PIDTurnAngle(-75, true),
    new PIDDriveDistance(63, true),
    new PIDTurnAngle(-35, true),
    new PIDDriveDistance(-140, true),
    new PIDTurnAngle(-110, true),
    new PIDDriveDistance(-42, true),
    new PIDTurnAngle(-90, true),
    new PIDDriveDistance(-130, true),
    
    new PIDDriveDistance(130, true),
    new PIDTurnAngle(-90, true),
    new PIDDriveDistance(90, true),
    new PIDTurnAngle(-90, true),
    new PIDDriveDistance(132, true),
    new PIDDriveDistance(-36, true),
    new PIDTurnAngle(-90, true),
    new PIDDriveDistance(-72, true)
   );

  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    BouncePath cmd = new BouncePath();
    TestingDashboard.getInstance().registerCommand(auto, "Auto", cmd);
  }
}
