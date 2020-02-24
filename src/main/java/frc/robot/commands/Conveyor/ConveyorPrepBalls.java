/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Conveyor;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.TestingDashboard;
import frc.robot.commands.Shooter.ShooterUp;
import frc.robot.subsystems.Conveyor;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ConveyorPrepBalls extends ParallelDeadlineGroup {
  /**
   * Creates a new ConveyorPrepBalls.
   */
  public ConveyorPrepBalls() {
    // Add your commands in the super() call.  Add the deadline first.
    super(
        new InstantCommand()
        /*
        new BallReadyToShoot(),
        new ShooterUp(),
        new SpinBothConveyorsTimed(0.5, 0.5, 1, 20, 20)
        */
    );
  }

  public static void registerWithTestingDashboard() {
    Conveyor conveyor = Conveyor.getInstance();
    ConveyorPrepBalls cmd = new ConveyorPrepBalls();
    TestingDashboard.getInstance().registerCommand(conveyor, "Timed", cmd);
  }


}


