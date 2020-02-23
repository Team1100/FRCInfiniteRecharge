/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.TestingDashboard;

public class Shooter extends SubsystemBase {
  private static Shooter shooter;

  private WPI_TalonSRX bottomShooter;
  private WPI_TalonSRX topShooter;
  private Encoder topEncoder, bottomEncoder;
  private final double TOP_PPD = 2048;
  private final double BOT_PPD = 2048;
  private DoubleSolenoid m_piston;

  /**
   * Creates a new Shooter.
   */
  private Shooter() {

    bottomShooter = new WPI_TalonSRX(RobotMap.SH_BOTTOM);
    topShooter = new WPI_TalonSRX(RobotMap.SH_TOP);
    topEncoder = new Encoder(RobotMap.SH_TOP_ENCODER_A, RobotMap.SH_TOP_ENCODER_B);
    bottomEncoder = new Encoder(RobotMap.SH_BOT_ENCODER_A, RobotMap.SH_BOT_ENCODER_B);
    m_piston = new DoubleSolenoid(RobotMap.SH_PCM_CAN, 
    RobotMap.SH_PISTON_PORT0, RobotMap.SH_PISTON_PORT1);
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

  public Encoder getBottomEncoder(){
    return bottomEncoder;
  }

  public Encoder getTopEncoder(){
    return topEncoder;
  }

  public double getRPM(Encoder encoder){
    return encoder.getRate() * 60;
  }

  public DoubleSolenoid getPiston() {
    return m_piston;
  }

  public void raiseShooter() {
    m_piston.set(DoubleSolenoid.Value.kForward);
  }
  public void lowerShooter() {
    m_piston.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
