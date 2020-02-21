/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.TestingDashboard;
import frc.robot.input.XboxController.XboxAxis;
import frc.robot.subsystems.Turret;

public class DefaultTurret extends CommandBase {
  /**
   * Creates a new DefaultTurret.
   */
  Turret m_turret;
  private static OI oi;

  public DefaultTurret(Turret turret) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Turret.getInstance());
    oi = OI.getInstance();
    m_turret = Turret.getInstance();
  }

  public static void registerWithTestingDashboard() {
    Turret turret = Turret.getInstance();
    DefaultTurret cmd = new DefaultTurret(turret);
    TestingDashboard.getInstance().registerCommand(turret, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Drives the Turret with the x-axis of the right Xbox joystick.
    double speed = oi.getXbox().getAxis(XboxAxis.kXRight);
    m_turret.spinTurretMotor(0.5*speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
