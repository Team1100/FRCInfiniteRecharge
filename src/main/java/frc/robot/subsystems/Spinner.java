/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Spinner extends SubsystemBase {
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    /**
   * Creates a new Spinner.
   */
  public Spinner() {
    Color detectedColor = m_colorSensor.getColor();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
