/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import edu.wpi.first.networktables.NetworkTableEntry;

/**
 * TestingDashboardDataTable is a map of data groups to
 * sensor/testing values that can be mapped to a shuffleboard
 * tab.
 */
public class TestingDashboardDataTable {
  Hashtable<String, ArrayList<String>> table;
  ArrayList<String> names;
  Hashtable<String, NetworkTableEntry> entries;
  public TestingDashboardDataTable() {
    table = new Hashtable<String, ArrayList<String>>();
    names = new ArrayList<String>();
    entries = new Hashtable<String, NetworkTableEntry>();
  }

  public void addName(String str, String name) {
    if (table.containsKey(str)) {
      ArrayList<String> l = table.get(str);
      l.add(name);
    } else {
      ArrayList<String> l = new ArrayList<String>();
      l.add(name);
      table.put(str, l);
    }
    names.add(name);
  }

  /*
   *  This function adds the NetworkTableEntry for a given
   *  named data item.
   */
  public void addEntry(String str, NetworkTableEntry entry) {
      if (names.contains(str)) {
          entries.put(str,entry);
      }
  }

  public NetworkTableEntry getEntry(String str) {
      return entries.get(str);
  }

  public Enumeration<String> getDataGroups() {
    return table.keys();
  }

  public ArrayList<String> getDataList(String dataGrgName) {
    return table.get(dataGrgName);
  }
}
