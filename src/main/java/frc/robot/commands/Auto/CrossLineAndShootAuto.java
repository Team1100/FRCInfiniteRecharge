// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.TestingDashboard;
import frc.robot.commands.BallIntake.BallIntakeDown;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.subsystems.Auto;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class CrossLineAndShootAuto extends SequentialCommandGroup {
  /** Creates a new CrossLineAndShootAuto. */
  public CrossLineAndShootAuto() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveDistance(Constants.kInitiationLine, Constants.kRobotNormalDriveSpeed, true),
      new BallIntakeDown(),
      new Wait(1),
      new ShootBallsAuto(Constants.kZoneYellowSpeed, false)
    );
  }
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    CrossLineAndShootAuto cmd = new CrossLineAndShootAuto();
    TestingDashboard.getInstance().registerCommand(auto, "FullAutoSequence", cmd);
  }
}
