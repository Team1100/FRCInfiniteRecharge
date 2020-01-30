/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.SpinConveyor1Timed;
import frc.robot.commands.SpinIntakeRoller;
import frc.robot.commands.SpinSpinnerMotorTimed;
import frc.robot.commands.SpinSpinnerToColor;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and default commands are defined here...
  
  //Subsystems
  private final Drive drive;
  private final Climber climber;
  private final BallIntake ballIntake;
  private final Shooter shooter;
  private final Spinner spinner;
  private final Vision vision;

  //Commands
  private final DefaultDrive defaultdrive;
  
  //OI
  private static RobotContainer robotContainer;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    //Subsystem instantiation
    drive = Drive.getInstance();
    climber = Climber.getInstance();
    ballIntake = BallIntake.getInstance();
    shooter = Shooter.getInstance();
    spinner = Spinner.getInstance();
    vision = Vision.getInstance();

    //Default command instantiation
    defaultdrive = new DefaultDrive(drive);
    drive.setDefaultCommand(defaultdrive);

    //OI Device instantiation
    OI.getInstance();

    // Register commands with TestingDashboard commands
    DefaultDrive.registerWithTestingDashboard();
    SpinConveyor1Timed.registerWithTestingDashboard();
    SpinIntakeRoller.registerWithTestingDashboard();
    SpinSpinnerMotorTimed.registerWithTestingDashboard();
    SpinSpinnerToColor.registerWithTestingDashboard();

    // Create Testing Dashboard
    TestingDashboard.getInstance().createTestingDashboard();
  }

  /**
   * <a href="https://en.wikipedia.org/wiki/Singleton_pattern">Singleton Method</a> to return one instance of a class
   * @return New instance of RobotContainer class
   */
  public static RobotContainer getInstance(){
   if(robotContainer == null){
     robotContainer = new RobotContainer();
   } 
   return robotContainer;
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //TODO: Fill with autocommand
    return null;
  }
}
