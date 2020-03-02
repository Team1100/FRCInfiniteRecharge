/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.Auto.ShootBallsAuto;
import frc.robot.commands.BallIntake.*;
import frc.robot.commands.Climb.*;
import frc.robot.commands.Conveyor.*;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.Spinner.*;
import frc.robot.commands.Turret.*;
import frc.robot.input.AttackThree;
import frc.robot.input.ButtonBox;
import frc.robot.input.XboxController;
import frc.robot.subsystems.Vision;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private static OI oi;

  public static AttackThree leftStick;
  public static AttackThree rightStick;
  private static XboxController xbox;
  private static ButtonBox buttonBox;

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
    buttonBox = new ButtonBox(RobotMap.U_BUTTON_BOX);



    //Now Mapping Commands to XBox
    xbox.getButtonLeftBumper().whileHeld(new TurretLeft());
    xbox.getButtonRightBumper().whileHeld(new TurretRight());
    xbox.getButtonB().whenHeld(new FeedBalls());
    xbox.getButtonY().whileHeld(new ShootBallsAuto(5500, 4500));
    xbox.getButtonBack().whileHeld(new PIDTurret());
    xbox.getButtonX().whenPressed(new BallIntakeUp());
    xbox.getButtonA().whenPressed(new BallIntakeDown());
    xbox.getDPad().getUp().whenPressed(new ShooterUp());
    xbox.getDPad().getDown().whenPressed(new ShooterDown());
    xbox.getDPad().getLeft().whenHeld(new Climb(0.3, true));
    xbox.getDPad().getRight().whenHeld(new Climb(-0.3, true));

    buttonBox.getWideFocus().whenHeld(new PIDTurretProcedure());
    buttonBox.getFineFocus().whenHeld(new PIDTurretProcedure());
    buttonBox.getFire().whenHeld(new ShootBallsProcedure(Vision.getInstance().calculateRPM()));
    buttonBox.getFire().whenReleased(new ShooterDown());
    buttonBox.getIntakeIn().whenPressed(new BallIntakeUp());
    buttonBox.getIntakeOut().whenPressed(new BallIntakeDown());
    buttonBox.getHopper().whenHeld(new SpinIntakeRoller(1,true));
    /*
    buttonBox.getCPDeploy().whenPressed(new DeploySpinner());
    buttonBox.getCPSpin().whenPressed(new SpinSpinner3Times());
    buttonBox.getCPYellow().whenPressed(new SpinSpinnerToColor("Yellow", true));
    buttonBox.getCPGreen().whenPressed(new SpinSpinnerToColor("Green", true));
    buttonBox.getCPRed().whenPressed(new SpinSpinnerToColor("Red", true));
    buttonBox.getCPBlue().whenPressed(new SpinSpinnerToColor("Blue", true));
    */
    buttonBox.getClimberDeploy().whenPressed(new ClimberUp());
    buttonBox.getClimb().whenHeld(new Climb(0.5, true));    
    buttonBox.getClimb().whenReleased(new ClimberDown());

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

}
