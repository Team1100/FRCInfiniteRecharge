/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double ksVolts = 2.41;
    public static final double kvVoltSecondsPerMeter = 0.347;
    public static final double kaVoltSecondsSquaredPerMeter = 0.000404;

    //public static final double kPDriveVel = 8.5;
    public static final double kPDriveVel = 0.68;

    public static final double kTrackwidthMeters = Units.inchesToMeters(27);

    public static final double kMaxSpeedMetersPerSecond = 2.5;
    public static final double kMaxAccelerationMetersPerSecondSquared = 1.5;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    public static final boolean kGyroReversed = false;

    // defines zone constants
    public static final int kZoneGreen = 0;
    public static final int kZoneYellow = 1;
    public static final int kZoneBlue = 2;
    public static final int kZoneRed = 3;
    public static final int kZonePurple = 4;

    public static final double kZoneGreenStart = 0;
    public static final double kZoneYellowStart = 90;
    public static final double kZoneBlueStart = 150;
    public static final double kZoneRedStart = 210;
    public static final double kZonePurpleStart = 270;

    public static final double kZoneGreenEnd = kZoneYellowStart;
    public static final double kZoneYellowEnd = kZoneBlueStart;
    public static final double kZoneBlueEnd = kZoneRedStart;
    public static final double kZoneRedEnd = kZonePurpleStart;
    public static final double kZonePurpleEnd = 360;

    public static final double kZoneGreenSpeed = 1000;
    public static final double kZoneYellowSpeed = 2000;
    public static final double kZoneBlueSpeed = 3000;
    public static final double kZoneRedSpeed = 4000;
    public static final double kZonePurpleSpeed = 5000;



    public static final double kRobotLength = 36; // in inches
}
