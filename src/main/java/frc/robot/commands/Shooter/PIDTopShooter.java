/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PIDTopShooter extends PIDCommand {
  /**
   * Creates a new PIDShooter.
   */
  public PIDTopShooter() {
    super(
        // The controller that the command will use
        new PIDController(0.00125, 0.00045, 0.000027),
        // This should return the measurement
        () -> Shooter.getInstance().getRPM(Shooter.getInstance().getTopEncoder()),
        // This should return the setpoint (can also be a constant)
        () -> SmartDashboard.getNumber("Top Setpoint", 0),
        // This uses the output
        output -> {
          // Use the output here
          Shooter.getInstance().setTop(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(5);
    getController().disableContinuousInput();
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    PIDTopShooter cmd = new PIDTopShooter();
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    //getController().atSetpoint();
  }
}