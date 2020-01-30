/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;

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
    private ArrayList<TestingDashboardTab> testingTabs;
    
    private TestingDashboard() {
        testingTabs = new ArrayList<TestingDashboardTab>();
    }

    public static TestingDashboard getInstance() {
        if (testingDashboard == null) {
            testingDashboard = new TestingDashboard();
        }
        return testingDashboard;
    }

    private boolean hasSubsystem(SubsystemBase subsystem) {
        for (int i =0; i < testingTabs.size(); i++) {
            TestingDashboardTab tab = testingTabs.get(i);
            if (tab.subsystem == subsystem) {
                return true;
            }
        }
        return false;
    }

    private TestingDashboardTab getSubsystemTab(SubsystemBase subsystem) {
        for (int i =0; i < testingTabs.size(); i++) {
            TestingDashboardTab tab = testingTabs.get(i);
            if (tab.subsystem == subsystem) {
                return tab;
            }
        }
        return null;
    }

    /*
     * This function registers a subsystem with
     * the testing dashboard.
     */
    public void registerSubsystem(SubsystemBase subsystem, String name) {
        if (hasSubsystem(subsystem)) {
            // Subsystem has already been registered
            return;
        }
        TestingDashboardTab tdt = new TestingDashboardTab();
        tdt.subsystem = subsystem;
        tdt.subsystemName = name;
        tdt.commandTable = new TestingDashboardCommandTable();
        testingTabs.add(tdt);
        System.out.println("Subsystem " + name + " registered with TestingDashboard");
    }
    
    /*
     * This function registers a command with a subsystem
     * and a command group in the command table on the testing
     * dashboard.
     */
    public void registerCommand(SubsystemBase subsystem, String cmdGrpName, CommandBase command) {
        TestingDashboardTab tab = getSubsystemTab(subsystem);
        if (tab == null) {
            System.out.println("WARNING: Subsystem for command does not exist!");
            return;
        }
        System.out.println("Adding command " + command.toString());
        tab.commandTable.add(cmdGrpName, command);
    }

    public void createTestingDashboard() {
        System.out.println("Creating Testing Dashboard");
        for (int i = 0; i < testingTabs.size(); i++) {
            TestingDashboardTab tdt = testingTabs.get(i);
            tdt.tab = Shuffleboard.getTab(tdt.subsystemName);
            Enumeration<String> cmdGrpNames = tdt.commandTable.getCommandGroups();
            Iterator<String> it = cmdGrpNames.asIterator();
            System.out.println("Created tab for " + tdt.subsystemName + " subsystem");
            int colpos = 0;
            while (it.hasNext()) {
                String cmdGrpName = it.next();
                System.out.println("Creating \"" + cmdGrpName + "\" command group");
                ArrayList<CommandBase> cmdList = tdt.commandTable.getCommandList(cmdGrpName);
                ShuffleboardLayout layout = tdt.tab.getLayout(cmdGrpName, BuiltInLayouts.kList);
                layout.withPosition(colpos,0);
                layout.withSize(1,cmdList.size());
                for (int j = 0; j < cmdList.size(); j++) {
                    layout.add(cmdList.get(j));
                }
                colpos++;
            }
        }
        createDebugTab();
    }

    public void createDebugTab() {
        ShuffleboardTab debug_tab = Shuffleboard.getTab("Debug");

        // Controlling inputs for Conveyor 2 motor
        SmartDashboard.putNumber("Conveyor2MotorSpeed", 0.5);
        SmartDashboard.putNumber("Conveyor2MotoryTimeout", 5);

        // Controlling inputs for Conveyor 1 motor
        SmartDashboard.putNumber("Conveyor1MotorSpeed", 0.5);
        SmartDashboard.putNumber("Conveyor1MotoryTimeout", 5);

        // Controlling inputs for Intake Roller motor
        SmartDashboard.putNumber("IntakeRollerSpeed", 0.5);

        // Controlling time for spinner
        SmartDashboard.putNumber("SpinnerMotorPeriod",5);
        SmartDashboard.putNumber("SpinnerMotorSpeed",0.2);
        SmartDashboard.putString("SpinnerTargetColor","Yellow");
        SmartDashboard.putString("SpinnerActualColor","Yellow");
    }

    public void updateDebugTab() {

    }
}
