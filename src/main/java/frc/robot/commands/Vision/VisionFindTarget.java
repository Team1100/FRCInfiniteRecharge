// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Vision;
import frc.robot.TestingDashboard;

public class VisionFindTarget extends CommandBase {
  static Drive m_drive;
  static final int DRIFT_TOLERANCE = 2;
  // angle is in degrees

  double m_finalAngle;
  boolean m_parameterized;
  double m_speed;
  double m_initialAngle;
  // positive is clockwise, negative is counter-clockwise
  double m_direction;

  final double CLOCKWISE = 1;
  final double COUNTER_CLOCKWISE = -1;
  final double DEGREES_TO_TURN = 350;
  /** Creates a new VisionFindTarget. */
  public VisionFindTarget(double speed, boolean parameterized) {
    m_drive = Drive.getInstance();
    m_parameterized = parameterized;
    m_speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  public static void registerWithTestingDashboard() {
    Vision vision = Vision.getInstance();
    VisionFindTarget cmd = new VisionFindTarget(Vision.INITIAL_SPEED, false);
    TestingDashboard.getInstance().registerCommand(vision, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_initialAngle = m_drive.getYaw();
    m_direction = DEGREES_TO_TURN/Math.abs(DEGREES_TO_TURN);
    updateFinalAngle();
    if (!m_parameterized) {
      TestingDashboard.getInstance().updateNumber(Vision.getInstance(), "InitialAngle", m_initialAngle);
      m_speed = TestingDashboard.getInstance().getNumber(Vision.getInstance(), "SpeedWhenTurning");
      TestingDashboard.getInstance().updateNumber(Vision.getInstance(), "FinalAngle", m_finalAngle);
    }

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_speed = TestingDashboard.getInstance().getNumber(Vision.getInstance(), "SpeedWhenTurning");
    }
    m_drive.tankDrive(m_speed * m_direction, m_speed * m_direction * -1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean finished = false;
    double yaw = m_drive.getYaw();
    if (m_initialAngle >= 0 && m_finalAngle < 0) {
      // May need to add a tolerance to initial angle if the current angle
      // drifts to less than the initial angle at the start of the command.
      if (yaw < (m_initialAngle - DRIFT_TOLERANCE) && yaw > m_finalAngle) {
        finished = true;
      }
    } else if (m_initialAngle < 0 && m_finalAngle > 0) {
      // Test if the current angle is off at either end of the number line
      if (yaw < (m_initialAngle - DRIFT_TOLERANCE) || yaw > m_finalAngle) {
        finished = true;
      }
    } else if (m_initialAngle < 0 && m_finalAngle < 0) {
      if (yaw < (m_initialAngle - DRIFT_TOLERANCE) && yaw > m_finalAngle) {
        finished = true;
      }
    } else if (m_initialAngle > 0 && m_finalAngle > 0) {
      if (yaw < (m_initialAngle - DRIFT_TOLERANCE) && yaw > m_finalAngle) {
        finished = true;
      }
    }
    if (Vision.getInstance().isTargetFound())
      finished = true;
    System.out.println("m_finalAngle: " + m_finalAngle + " m_direction: " + m_direction + " yaw: " + yaw +  " Finished: " + finished);
    return finished;
  }

  public void updateFinalAngle() {

    m_finalAngle = m_initialAngle + DEGREES_TO_TURN;
    if (m_finalAngle > 180) {
      double delta = DEGREES_TO_TURN - (180-m_initialAngle);
      m_finalAngle = -180 + delta;
    } else if (m_finalAngle < -180) {
      double delta = DEGREES_TO_TURN - (-180-m_initialAngle);
      m_finalAngle = 180 + delta;
    }

  }
}
