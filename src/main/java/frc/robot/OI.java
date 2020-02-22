/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.BallIntake.*;
import frc.robot.commands.Conveyor.*;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.Turret.*;
import frc.robot.input.AttackThree;
import frc.robot.input.XboxController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private static OI oi;

  public static AttackThree leftStick;
  public static AttackThree rightStick;
  private static XboxController xbox;
  private static XboxController xbox_climb;

   /**
   * Used outside of the OI class to return an instance of the class.
   * @return Returns instance of OI class formed from constructor.
   */
  public static OI getInstance(){
    if (oi == null){
      oi = new OI();
    }
    return oi;
  }

  public OI(){
    //User Input
    //TODO:Tune deadband
    leftStick = new AttackThree(RobotMap.U_JOYSTICK_LEFT, 0.01);
    rightStick = new AttackThree(RobotMap.U_JOYSTICK_RIGHT, 0.01);

    xbox = new XboxController(RobotMap.U_XBOX_CONTROLLER, 0.3);

    //Now Mapping Commands to XBox
    xbox.getButtonLeftBumper().whileHeld(new TurretLeft());
    xbox.getButtonRightBumper().whileHeld(new TurretRight());
    xbox.getButtonB().whenPressed(new SpinBothConveyorsTimed());
    xbox.getButtonY().whileHeld(new SpinShooter());
    xbox.getButtonX().whenPressed(new BallIntakeUp());
    xbox.getButtonA().whenPressed(new BallIntakeDown());
    xbox.getDPad().getUp().whenPressed(new ShooterUp());
    xbox.getDPad().getDown().whenPressed(new ShooterDown());


  }

  /**
   * Returns the left Joystick
   * @return the leftStick
   */
  public AttackThree getLeftStick() {
    return leftStick;
  }

  /**
   * Returns the right Joystick
   * @return the rightStick
   */
  public AttackThree getRightStick() {
    return rightStick;
  }

  /**
   * Returns the Xbox Controller
   * @return the Xbox Controller
   */
  public XboxController getXbox() {
      return xbox;
  }

  public XboxController getXboxClimb() {
    return xbox_climb;
}

}
