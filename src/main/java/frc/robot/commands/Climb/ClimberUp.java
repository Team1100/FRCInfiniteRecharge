/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TestingDashboard;
import frc.robot.subsystems.Climber;

public class ClimberUp extends CommandBase {
    private Climber m_climber;
    private DoubleSolenoid m_piston;
    private boolean m_finished = false;

    /**
     * Creates a new ClimberUp.
     */
    public ClimberUp() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Climber.getInstance());
        m_climber = Climber.getInstance();
        m_piston = m_climber.getPiston();
    }

    public static void registerWithTestingDashboard() {
        Climber climber = Climber.getInstance();
        ClimberUp cmd = new ClimberUp();
        TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_climber.raiseClimber();
        
        if (m_piston.get() == DoubleSolenoid.Value.kReverse) {
            m_finished = true;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_finished;
    }
}
