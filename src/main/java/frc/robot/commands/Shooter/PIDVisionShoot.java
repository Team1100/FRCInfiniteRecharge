/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.TestingDashboard;
import frc.robot.commands.Vision.PIDVisionFindTarget;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PIDVisionShoot extends PIDCommand {
  double m_setpoint;
  boolean m_shooterPosition;
  Shooter m_shooter;
  /**
   * Creates a new PIDBottomShooter.
   */
  public PIDVisionShoot() {
    super(
        // The controller that the command will use
        new PIDController(Shooter.getInstance().getkP(), Shooter.getInstance().getkI(), Shooter.getInstance().getkD()),
        // This should return the measurement
        () -> Shooter.getInstance().getRPM(Shooter.getInstance().getBottomEncoder()),
        // This should return the setpoint (can also be a constant)
        () -> Shooter.getInstance().getZoneShooterSpeed(Vision.getInstance().getZone()),
        // This uses the output
        output -> {
          // Use the output here
          Shooter.getInstance().setBottom(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(10);
    getController().disableContinuousInput();
    m_shooter = Shooter.getInstance();
    m_setpoint = Shooter.getInstance().getZoneShooterSpeed(Vision.getInstance().getZone());
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    PIDVisionShoot cmd = new PIDVisionShoot();
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
    TestingDashboard.getInstance().registerSendable(shooter, "PIDController", "PIDVisionShootController", cmd.getController());
  }

  @Override
  public void initialize() {
    super.initialize();
    int zone = Vision.getInstance().getZone();
    m_shooterPosition = m_shooter.getZoneShooterPosition(zone);
  }

  @Override
  public void execute() {
    int zone = Vision.getInstance().getZone();
    m_setpoint = m_shooter.getZoneShooterSpeed(zone);
    m_useOutput.accept(m_controller.calculate(m_measurement.getAsDouble(), m_setpoint));

    if (m_shooterPosition && !(m_shooter.getPiston().get() == DoubleSolenoid.Value.kForward)) {
      m_shooter.raiseShooter();
    } else if (!m_shooterPosition && !(m_shooter.getPiston().get() == DoubleSolenoid.Value.kReverse)) {
      m_shooter.lowerShooter();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
