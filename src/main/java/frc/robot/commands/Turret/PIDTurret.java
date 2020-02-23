/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PIDTurret extends PIDCommand {
  /**
   * Creates a new PIDTurret.
   */
  public PIDTurret() {
    super(
        // The controller that the command will use
        new PIDController(0.01,0.0015, 0),
        // This should return the measurement
        () -> Vision.getInstance().getYaw().getDouble(0.0),
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          // Use the output here
          Turret.getInstance().spinTurretMotor(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(5);
    getController().disableContinuousInput();
  }

  public static void registerWithTestingDashboard() {
    Turret turret = Turret.getInstance();
    PIDTurret cmd = new PIDTurret();
    TestingDashboard.getInstance().registerCommand(turret, "Basic", cmd);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //TODO: implement end condition with getController().atSetpoint();
    return false;
    
  }
}