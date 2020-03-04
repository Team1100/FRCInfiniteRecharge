/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Conveyor;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class FeedBalls extends CommandBase {
  private Conveyor m_conveyor = Conveyor.getInstance();
  private Boolean topToggled = false; 
  private int counter = 0;
  private Boolean lastTopState;
  private Boolean isFinished = false;
  private int STATE = 1;
  private Timer t;

  /**
   * Creates a new ShootBalls.
   */
  public FeedBalls() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Conveyor.getInstance());
    t = new Timer();
  }

  public static void registerWithTestingDashboard() {
    Conveyor conveyor = Conveyor.getInstance();
    FeedBalls cmd = new FeedBalls();
    TestingDashboard.getInstance().registerCommand(conveyor, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_conveyor.spinAllConveyors(0, 0, 0);
    topToggled = false;
    counter = 0;
    lastTopState = false;
    STATE = 1;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Load Ball into bottom of v conveyor
    switch(STATE){
      case 1:
        t.stop();
        t.reset();
        counter = 0;
        m_conveyor.spinAllConveyors(0.7, 0.5, 0.5);
        if(m_conveyor.ballIncoming()){
          STATE = 2;
          m_conveyor.spinAllConveyors(0, 0, 0);
        }
        lastTopState = m_conveyor.ballReadyToShoot();
        break;
      case 2:
        t.start();
        m_conveyor.spinVConveyor(1);
        if(m_conveyor.ballReadyToShoot() != lastTopState){
          counter += 1;
          lastTopState = m_conveyor.ballReadyToShoot();
        }

        if(counter == 2){
          STATE = 1;
        }
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.spinAllConveyors(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished || t.hasElapsed(2.5);
  }
}
