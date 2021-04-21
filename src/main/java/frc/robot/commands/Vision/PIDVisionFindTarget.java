// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PIDVisionFindTarget extends PIDCommand {
  static final double OFFSET_TOLERANCE = 100;
  /** Creates a new PIDVisionTurnToTarget. */
  public PIDVisionFindTarget() {
    super(
        // The controller that the command will use
        new PIDController(0, 0, 0),
        // This should return the measurement
        () -> Vision.getInstance().getTargetOffset(),
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          // Use the output here
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }
  public static void registerWithTestingDashboard() {
    Vision vision = Vision.getInstance();
    PIDVisionFindTarget cmd = new PIDVisionFindTarget();
    TestingDashboard.getInstance().registerCommand(vision, "Basic", cmd);
    TestingDashboard.getInstance().registerSendable(vision, "PIDFindTarget", "PIDFindTarget", cmd.getController());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean finished = false;
    if (!Vision.getInstance().isTargetFound())
      finished = true;
    if (Math.abs(Vision.getInstance().getTargetOffset()) < OFFSET_TOLERANCE)
      finished = true;
    return finished;
  }
}
