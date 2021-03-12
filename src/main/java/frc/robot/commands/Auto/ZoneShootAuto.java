package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Auto;

import frc.robot.commands.Drive.PIDDriveDistance;
import frc.robot.commands.Drive.PIDTurnAngle;
import frc.robot.commands.Shooter.PIDBottomShooter;

public class ZoneShootAuto extends SequentialCommandGroup{
    public ZoneShootAuto(String zone) {
        
            if (zone == "green") {
                addCommands(
                new PIDBottomShooter(0.3));
            }
            if (zone == "yellow") {
                addCommands(
                new PIDBottomShooter(0.4));
            }
            if (zone == "blue") {
                addCommands(
                new PIDBottomShooter(0.5));
            }
            if (zone == "red") {
                addCommands(
                new PIDBottomShooter(0.6));
            }
    }

    public static void registerWithTestingDashboard() {
        Auto auto = Auto.getInstance();
        ZoneShootAuto cmd = new ZoneShootAuto("hello");
        TestingDashboard.getInstance().registerCommand(auto, "Auto", cmd);
      }
}
