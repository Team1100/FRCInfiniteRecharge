/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Auto.*;
import frc.robot.commands.BallIntake.*;
import frc.robot.commands.Climb.*;
import frc.robot.commands.Conveyor.*;
import frc.robot.commands.Drive.*;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.Spinner.*;
import frc.robot.commands.Turret.*;
import frc.robot.subsystems.*;

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
  private final Conveyor conveyor;
  private final Shooter shooter;
  private final Spinner spinner;
  private final Turret turret;
  private final Vision vision;

  //Commands
  private final DefaultDrive defaultdrive;
  private final DefaultIntake defaultintake;
  private final DefaultTurret defaultturret;

  BarrelRacingPath barrelRacingPath;
  DriveSquareAuto driveSquareAuto;
  DriveSquareAutoPID driveSquareAutoPID;
  SlalomPath slalomPath;
  DriveTriangleAuto driveTriangleAuto;
  
  //OI
  private static RobotContainer robotContainer;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Subsystem instantiation
    drive = Drive.getInstance();
    climber = Climber.getInstance();
    ballIntake = BallIntake.getInstance();
    conveyor = Conveyor.getInstance();
    shooter = Shooter.getInstance();
    spinner = Spinner.getInstance();
    turret = Turret.getInstance();
    vision = Vision.getInstance();

    //Default command instantiation
    defaultdrive = new DefaultDrive(drive);
    drive.setDefaultCommand(defaultdrive);
    defaultintake = new DefaultIntake(ballIntake);
    ballIntake.setDefaultCommand(defaultintake);
    defaultturret = new DefaultTurret(turret);
    turret.setDefaultCommand(defaultturret);

    // Auto command instantiation
    barrelRacingPath = new BarrelRacingPath();
    driveSquareAuto = new DriveSquareAuto();
    driveSquareAutoPID = new DriveSquareAutoPID();
    slalomPath = new SlalomPath();
    driveTriangleAuto = new DriveTriangleAuto();

    //OI Device instantiation
    OI.getInstance();


    // Register commands with TestingDashboard commands
    DefaultDrive.registerWithTestingDashboard();
    DriveDistance.registerWithTestingDashboard();
    TurnAngle.registerWithTestingDashboard();
    PIDTurnAngle.registerWithTestingDashboard();
    PIDDriveDistance.registerWithTestingDashboard();
    DriveSquareAuto.registerWithTestingDashboard();
    DriveSquareAutoPID.registerWithTestingDashboard();
    SlalomPath.registerWithTestingDashboard();
    ZoneShootAuto.registerWithTestingDashboard();
    DriveTriangleAuto.registerWithTestingDashboard();
    BarrelRacingPath.registerWithTestingDashboard();
    DefaultIntake.registerWithTestingDashboard();
    DefaultTurret.registerWithTestingDashboard();
    SpinConveyorHTimed.registerWithTestingDashboard();
    SpinConveyorVTimed.registerWithTestingDashboard();
    SpinIntakeRoller.registerWithTestingDashboard();
    SpinSpinnerMotorTimed.registerWithTestingDashboard();
    SpinSpinnerToColor.registerWithTestingDashboard();
    SpinSpinner3Times.registerWithTestingDashboard();
    DeploySpinner.registerWithTestingDashboard();
    RetractSpinner.registerWithTestingDashboard();
    TimedForward.registerWithTestingDashboard();
    SpinBothConveyorsTimed.registerWithTestingDashboard();
    SpinShooter.registerWithTestingDashboard();
    TurretStop.registerWithTestingDashboard();
    TurretLeft.registerWithTestingDashboard();
    TurretRight.registerWithTestingDashboard();
    ShootAndCrossLineAuto.registerWithTestingDashboard();
    BallIntakeDown.registerWithTestingDashboard();
    BallIntakeUp.registerWithTestingDashboard();
    ShooterUp.registerWithTestingDashboard();
    ShooterDown.registerWithTestingDashboard();
    PIDTopShooter.registerWithTestingDashboard();
    PIDBottomShooter.registerWithTestingDashboard();
    Wait.registerWithTestingDashboard();
    Climb.registerWithTestingDashboard();
    FeedBalls.registerWithTestingDashboard();
    ClimberUp.registerWithTestingDashboard();
    ClimberDown.registerWithTestingDashboard();
    Release.registerWithTestingDashboard();
    TurnToLimit.registerWithTestingDashboard();
    PIDTurnToCenter.registerWithTestingDashboard();
    SpitBalls.registerWithTestingDashboard();
    PIDTurnToAngle.registerWithTestingDashboard();
    BouncePath.registerWithTestingDashboard();
    Rotate.registerWithTestingDashboard();

    // Create Testing Dashboard
    TestingDashboard.getInstance().createTestingDashboard();
  }

  /**
   * <a href="https://en.wikipedia.org/wiki/Singleton_pattern">Singleton Method</a> to return one instance of a class
   * @return New instance of RobotContainer class
   */
  public static RobotContainer getInstance() {
   if (robotContainer == null) {
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
    // TODO: This needs to be changed to collect the autonomous command
    // from a chooser on ShuffleBoard
    // Create a voltage constraint to ensure we don't accelerate too fast
    return driveSquareAuto;
    // Run path following command, then stop at the end.
    //return ramseteCommand.andThen(() -> drive.tankDriveVolts(0, 0));
  }
}
