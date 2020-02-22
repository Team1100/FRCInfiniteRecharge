/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Spins the conveyor motor until there is a ball ready to shoot.
package frc.robot.commands.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;
import frc.robot.TestingDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ConveyorPrepBalls extends CommandBase {
  /**
   * Creates a new PrepareBallsToShoot.
   */

   Conveyor m_conveyor;
   Shooter m_shooter;
   DoubleSolenoid.Value solenoidState;

   private static final int STATE1 = 1;
   private static final int STATE2 = 2;

   int state;
   int stateCounter;

  public ConveyorPrepBalls() {
    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(Conveyor.getInstance(), Shooter.getInstance());
    m_conveyor = Conveyor.getInstance();
    m_shooter = Shooter.getInstance();
    solenoidState = m_shooter.getPiston().get();
    
  }

  public static void registerWithTestingDashboard() {
    Conveyor conveyor = Conveyor.getInstance();
    ConveyorPrepBalls cmd = new ConveyorPrepBalls();
    TestingDashboard.getInstance().registerCommand(conveyor, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    state = STATE1;
    stateCounter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = SmartDashboard.getNumber("Conveyor1MotorSpeed",0.5);
    switch (state) {
    case STATE1:
      m_conveyor.spinVConveyor(speed);
      if (m_conveyor.ballReadyToShoot() == true) {
        state = STATE2;
      }
      break;

    case STATE2:
      if (solenoidState == DoubleSolenoid.Value.kForward) {
        state = STATE1;
      }
      else {
        while (solenoidState == DoubleSolenoid.Value.kReverse) {
          m_conveyor.spinVConveyor(0);
        }
      }
      state = STATE1;
      break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.spinHConveyors(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
