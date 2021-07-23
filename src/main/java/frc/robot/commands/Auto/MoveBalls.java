/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.TestingDashboard;
import frc.robot.commands.BallIntake.SpinIntakeRoller;
import frc.robot.commands.Conveyor.FeedBalls;
import frc.robot.subsystems.Auto;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class MoveBalls extends ParallelCommandGroup {
  /**
   * Creates a new MoveBalls.
   */
  public MoveBalls() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    super(new SpinIntakeRoller(1, true),
          new FeedBalls()
          );
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    MoveBalls cmd = new MoveBalls();
    TestingDashboard.getInstance().registerCommand(auto, "ConveyorCmds", cmd);
  }
}
