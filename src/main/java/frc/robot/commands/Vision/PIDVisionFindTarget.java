// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PIDVisionFindTarget extends PIDCommand {
  // the width of the camera stream is 480 pixels
  static final double OFFSET_TOLERANCE = 100; // measured in pixels 
  static final double MAX_SPEED = 1;
  static Drive m_drive;
  /** Creates a new PIDVisionTurnToTarget. */
  public PIDVisionFindTarget() {
    super(
        // The controller that the command will use
        new PIDController(0.01, 0.01, 0.0),
        // This should return the measurement
        () -> Vision.getInstance().getTargetOffset(),
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          // Use the output here
          if (output > MAX_SPEED) {
            output = MAX_SPEED;
          }
          if (output < -MAX_SPEED) {
            output = -MAX_SPEED;
          }
            
          m_drive.tankDrive(output, -output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(OFFSET_TOLERANCE);
    m_drive = Drive.getInstance();

  }
  public static void registerWithTestingDashboard() {
    Vision vision = Vision.getInstance();
    PIDVisionFindTarget cmd = new PIDVisionFindTarget();
    TestingDashboard.getInstance().registerCommand(vision, "Basic", cmd);
    TestingDashboard.getInstance().registerSendable(vision, "PIDVisionFindTarget", "PIDVisionFindTarget", cmd.getController());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean finished = false;
    if (!Vision.getInstance().isTargetFound()) // if the target is not found, the command is finished
      finished = true;
    if (getController().atSetpoint())
      finished = true;
    return finished;
  }
}
