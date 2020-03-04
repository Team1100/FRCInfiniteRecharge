/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.commands.Auto.*;
import frc.robot.commands.BallIntake.*;
import frc.robot.commands.Climb.*;
import frc.robot.commands.Conveyor.*;
import frc.robot.commands.Drive.*;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.Spinner.*;
import frc.robot.commands.Turret.*;
import frc.robot.subsystems.*;
import io.github.oblarg.oblog.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;


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

    //OI Device instantiation
    OI.getInstance();

    //Configure OBlog
    Logger.configureLoggingAndConfig(this, false);

    // Register commands with TestingDashboard commands
    DefaultDrive.registerWithTestingDashboard();
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
    PIDTurret.registerWithTestingDashboard();
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
    // TODO: This needs to be changed to collect the autonomous command
    // from a chooser on ShuffleBoard
    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(Constants.ksVolts,
                                       Constants.kvVoltSecondsPerMeter,
                                       Constants.kaVoltSecondsSquaredPerMeter),
            Constants.kDriveKinematics,
            8);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(Constants.kMaxSpeedMetersPerSecond,
                             Constants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    
    // An default trajectory to follow.  All units in meters. Should be overwritten.
    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
        
    );
    
    
    String trajectoryJSON = "paths/Forward.wpilib.json";
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
      DriverStation.reportError("Opened File Yay", false);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
    }
    

    RamseteCommand ramseteCommand = new RamseteCommand(
        trajectory,
        drive::getPose,
        new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),
        new SimpleMotorFeedforward(Constants.ksVolts,
                                   Constants.kvVoltSecondsPerMeter,
                                   Constants.kaVoltSecondsSquaredPerMeter),
        Constants.kDriveKinematics,
        drive::getWheelSpeeds,
        new PIDController(Constants.kPDriveVel, 0, 0),
        new PIDController(Constants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        drive::tankDriveVolts,
        drive
    );

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> drive.tankDriveVolts(0, 0));
  }
}
