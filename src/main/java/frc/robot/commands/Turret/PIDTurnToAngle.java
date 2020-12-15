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

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PIDTurnToAngle extends PIDCommand {
  /**
   * Creates a new PIDTurnToAngle.
   */
  /*
   * Constructor that operates with a fixed setpoint. This is good for
   * running a command that turns to a specific unchanging setpoint.
   */
  public PIDTurnToAngle(double setpoint) {
    super(
        // The controller that the command will use
        new PIDController(0.00001, 0.00001, 0),
        // This should return the measurement
        () -> Turret.getInstance().getRotationAngle(),
        // This should return the setpoint (can also be a constant)
        () -> setpoint,
        // This uses the output
        output -> {
          // Use the output here
          Turret.getInstance().spinTurretMotor(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(Turret.getInstance());
    getController().setTolerance(5);
    getController().disableContinuousInput();
  }

  /*
   * Alternate constructor that allows the setpoint to be dynamically
   * retrieved from the testing dashboard.
   */
  public PIDTurnToAngle() {
    super(
        // The controller that the command will use
        new PIDController(0.00001, 0.00001, 0),
        // This should return the measurement
        () -> Turret.getInstance().getRotationAngle(),
        // This should return the setpoint (can also be a constant)
        () -> TestingDashboard.getInstance().getNumber(Turret.getInstance(), "TurretSetpoint"),
        // This uses the output
        output -> {
          // Use the output here
          Turret.getInstance().spinTurretMotor(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(Turret.getInstance());
    getController().setTolerance(5);
    getController().disableContinuousInput();
  }

  public static void registerWithTestingDashboard() {
    Turret turret = Turret.getInstance();
    PIDTurnToAngle cmd = new PIDTurnToAngle();
    TestingDashboard.getInstance().registerCommand(turret, "Basic", cmd);
    TestingDashboard.getInstance().registerSendable(turret, "PIDRotation", "TurretPIDController", cmd.getController());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
