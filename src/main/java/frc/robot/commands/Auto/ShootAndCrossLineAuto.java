/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TestingDashboard;
import frc.robot.commands.BallIntake.BallIntakeDown;
import frc.robot.commands.Drive.TimedForward;
import frc.robot.commands.Shooter.ShootBalls;
import frc.robot.commands.Shooter.ShootBallsProcedure;
import frc.robot.commands.Shooter.ShooterUp;
import frc.robot.subsystems.Auto;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootAndCrossLineAuto extends SequentialCommandGroup {
  /**
   * Creates a new ComplexAuto.
   */
  public ShootAndCrossLineAuto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    // Autonomous mode will execute the below steps
    // 1. Wait a certain amount of time (controlled by StartAutoWaitTime in SmartDashboard)
    // 2. Move forward for a certain amount of time (controlled by DriveForwardTime in SmartDashboard)
    // TODO: Add the commands for shooting here
    super(new BallIntakeDown(),
          new Wait(1),
          new ShootBallsAuto(3800, 5500),
          new ShooterUp(),
          new TimedForward(3, 0.5));
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    ShootAndCrossLineAuto cmd = new ShootAndCrossLineAuto();
    TestingDashboard.getInstance().registerCommand(auto, "Auto", cmd);
  }

}
