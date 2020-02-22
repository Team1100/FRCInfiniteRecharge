/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.TestingDashboard;

public class Shooter extends SubsystemBase {
  private static Shooter shooter;

  private WPI_TalonSRX bottomShooter;
  private WPI_TalonSRX topShooter;

  /**
   * Creates a new Shooter.
   */
  private Shooter() {
    bottomShooter = new WPI_TalonSRX(2);
    topShooter = new WPI_TalonSRX(13);
  }

  public static Shooter getInstance() {
    if (shooter == null) {
      shooter = new Shooter();
      TestingDashboard.getInstance().registerSubsystem(shooter, "Shooter");
    }
    return shooter;
  }

  public void setBottom(double speed) {
    bottomShooter.set(-speed);
  }

  public void setTop(double speed) {
    topShooter.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
