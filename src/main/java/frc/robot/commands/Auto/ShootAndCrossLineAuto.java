/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.TestingDashboard;
import frc.robot.commands.BallIntake.BallIntakeDown;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Shooter.ShooterDown;
import frc.robot.subsystems.Auto;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootAndCrossLineAuto extends SequentialCommandGroup {
  /**
   * Creates a new ShootAndCrossLineAuto.
   */
  public ShootAndCrossLineAuto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    // Autonomous mode will execute the below steps
    // 1. Wait a certain amount of time (controlled by StartAutoWaitTimeSeconds in SmartDashboard)
    // 2. Move forward for a certain amount of time (controlled by DriveForwardTime in SmartDashboard)
    // TODO: Add the commands for shooting here
    addCommands(
          new Wait(0, false),
          new BallIntakeDown(),
          new ShooterDown(),
          new Wait(1, true),
          new ShootBallsAuto(Constants.kZoneYellowSpeed, true),
          new DriveDistance(-Constants.kRobotLength - 6, Constants.kRobotNormalDriveSpeed, true)
          );
  }
  

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    ShootAndCrossLineAuto cmd = new ShootAndCrossLineAuto();
    TestingDashboard.getInstance().registerCommand(auto, "FullAutoSequence", cmd);
  }
}
