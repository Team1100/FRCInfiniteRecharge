// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PIDVisionTurretTarget extends PIDCommand {
  static final double OFFSET_TOLERANCE = 10; // measured in pixels 
  /** Creates a new PIDVisionCenterTarget. */
  public PIDVisionTurretTarget() {
    super(
        // The controller that the command will use
        new PIDController(0.003, 0.001, 0),
        // This should return the measurement
        () -> Vision.getInstance().getTargetOffset(),
        // This should return the setpoint (can also be a constant)
        () -> 0, // the measurement is the offset of the target to the camera, we want to end up with 0 offset
        // This uses the output
        output -> {
          // Use the output here
          Turret.getInstance().spinTurretMotor(-output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(Turret.getInstance());
    getController().setTolerance(OFFSET_TOLERANCE);
    getController().disableContinuousInput();
  }

  public static void registerWithTestingDashboard() {
    Vision vision = Vision.getInstance();
    PIDVisionTurretTarget cmd = new PIDVisionTurretTarget();
    TestingDashboard.getInstance().registerCommand(vision, "Basic", cmd);
    TestingDashboard.getInstance().registerSendable(vision, "PIDTurretRotation", "PIDTurretRotation", cmd.getController());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean finished = false;
    if (!Vision.getInstance().isTargetFound())
      finished = true;
    else
      finished = getController().atSetpoint();
    return finished;
  }
}
