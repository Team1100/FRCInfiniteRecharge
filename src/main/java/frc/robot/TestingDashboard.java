/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import frc.robot.subsystems.*;

/**
 * This class sets up a testing dashboard using
 * WPILib's ShuffleBoard. The testing dashboard
 * contains a single tab for every subsystem
 * containing the subsystem status and all
 * commands associated with that subsystem.
 * 
 * There is also a debug tab that contains sensor
 * variables and debug values.
 * 
 * This class is a singleton that should be
 * instantiated in the robotInit method
 * of the Robot class.
 */
public class TestingDashboard {
    private static TestingDashboard testingDashboard;

    private TestingDashboard() {
    }

    public static TestingDashboard getInstance() {
        if (testingDashboard == null) {
            testingDashboard = new TestingDashboard();
        }
        return testingDashboard;
    }

    public void createTestingDashboard() {
    
        createDebugTab();
    }
    
    public void createDebugTab() {

    }
    
    public void updateDebugTab() {

    }
}