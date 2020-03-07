/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.BallIntake.SpinIntakeRoller;
import frc.robot.commands.Conveyor.FeedBalls;
import frc.robot.commands.Shooter.PIDBottomShooter;
import frc.robot.commands.Shooter.PIDTopShooter;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootBallsAuto extends ParallelDeadlineGroup {
  /**
   * Creates a new ShootBalls.
   */
  public ShootBallsAuto(double topSetpoint, double botSetpoint)
  {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    super(new Wait(4),
    new SpinIntakeRoller(1, true),
    new FeedBalls(), 
    new PIDTopShooter(topSetpoint), 
    new PIDBottomShooter(botSetpoint));
  }

  
}
