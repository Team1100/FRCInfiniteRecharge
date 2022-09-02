/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.Auto.VisionShootBallsAuto;
import frc.robot.commands.BallIntake.*;
import frc.robot.commands.Climb.*;
import frc.robot.commands.Conveyor.*;
import frc.robot.commands.Drive.ToggleBrakeMode;
import frc.robot.commands.Drive.ArcadeDrive;
import frc.robot.commands.Drive.TankDrive;
import frc.robot.commands.Shooter.*;
//import frc.robot.commands.Spinner.*;
import frc.robot.commands.Turret.*;
import frc.robot.input.AttackThree;
import frc.robot.input.ButtonBox;
import frc.robot.input.XboxController;
import frc.robot.subsystems.Drive;

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
  public static OI getInstance() { 
    if (oi == null) {
      oi = new OI();
    }
    return oi;
  }

  private OI() {
    // User Input
    // TODO: Tune deadband
    leftStick = new AttackThree(RobotMap.U_JOYSTICK_LEFT, 0.01);
    rightStick = new AttackThree(RobotMap.U_JOYSTICK_RIGHT, 0.01);
    xbox = new XboxController(RobotMap.U_XBOX_CONTROLLER, 0.3);
    buttonBox = new ButtonBox(RobotMap.U_BUTTON_BOX);

    ////////////////////////////////////////////////////
    // Now Mapping Commands to XBox
    ////////////////////////////////////////////////////
    
    // Bumpers
    xbox.getButtonLeftBumper().whenPressed(new ShooterDown());
    xbox.getButtonRightBumper().whileHeld(new ShooterUp());

    // XYAB
    xbox.getDPad().getUp().whenPressed(new BallIntakeUp());
    xbox.getButtonB().whenHeld(new FeedBalls());
    xbox.getDPad().getDown().whenPressed(new BallIntakeDown());
    xbox.getButtonX().whenHeld(new SpitBalls());
    
    xbox.getDPad().getLeft().whenHeld(new TurretLeft());
    xbox.getDPad().getRight().whenHeld(new TurretRight());
    // Start and Back
    PIDBottomShooter pidBottomShooter = new PIDBottomShooter(5500, true);
    xbox.getButtonY().whenHeld(pidBottomShooter);
    //xbox.getButtonBack().toggleWhenPressed(new TankDrive(Drive.getInstance()));

    // DPAD
    //xbox.getDPad().getUp().whileHeld(new Climb(-0.5,true));

    // Left and Right Stick buttons
    //xbox.getButtonLeftStick().toggleWhenPressed(new HookUp(0));

    ////////////////////////////////////////////////////
    // Now Mapping Commands to AttackThree controllers
    ////////////////////////////////////////////////////

    leftStick.getButton(6).whenHeld(new Climb(0.5, true));

    leftStick.getButton(3).whenPressed(new ToggleBrakeMode(true));
    rightStick.getButton(3).whenPressed(new ToggleBrakeMode(false));
 
    ////////////////////////////////////////////////////
    // Now Mapping Commands to Button Box
    ////////////////////////////////////////////////////

    
    // Zone 1
    buttonBox.getFire().whenHeld(new PIDBottomShooter(6500, true));
    buttonBox.getFineFocus().whenHeld(new FeedBalls());
    buttonBox.getWideFocus().whenHeld(new SpitBalls());
    // Zone 2
    buttonBox.getIntakeIn().whenPressed(new BallIntakeUp());
    // Zone 3
    buttonBox.getIntakeOut().whenPressed(new BallIntakeDown());
    // Zone 4
    
    // Stop shooter
    buttonBox.getCPDeploy().whenPressed(new SpinShooter(0.0,0.0,true));
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

    buttonBox.getCPRed().whenHeld(new TurretLeft());
    buttonBox.getCPRed().whenReleased(new TurretStop());
    buttonBox.getCPBlue().whenHeld(new TurretRight());
    buttonBox.getCPBlue().whenReleased(new TurretStop());
    buttonBox.getCPYellow().whenPressed(new ShooterUp());
    buttonBox.getCPGreen().whenPressed(new ShooterDown());

    buttonBox.getCPSpin().whenPressed(new VisionShootBallsAuto());

    buttonBox.getHopper().whenHeld(new SpinIntakeRoller(1, false));

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
