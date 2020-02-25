/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Conveyor.SpinBothConveyorsTimed;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootBalls extends ParallelCommandGroup {
  /**
   * Creates a new ShootBalls.
   */
  public ShootBalls(double topRPM, double botRPM) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    super(new SpinBothConveyorsTimed(0.5,0.5,1,5,5), new PIDBottomShooter(botRPM), new PIDTopShooter(topRPM));
  }
}
