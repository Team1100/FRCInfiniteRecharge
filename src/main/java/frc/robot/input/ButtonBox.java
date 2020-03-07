package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is for a ButtonBox. All information about any given button
 * can be accessed through the functions written here.
 */
public class ButtonBox extends Joystick {

	private JoystickButton WideFocus;
	private JoystickButton FineFocus;
	private JoystickButton Fire;
	private JoystickButton IntakeIn;
	private JoystickButton IntakeOut;
	private JoystickButton Hopper;
	private JoystickButton CPDeploy;
	private JoystickButton CPSpin;
	private JoystickButton CPYellow;
	private JoystickButton CPGreen;
	private JoystickButton CPRed;
	private JoystickButton CPBlue;
	private JoystickButton ClimberDeploy;
	private JoystickButton Climb;
	private JoystickButton ClimberLeft;
	private JoystickButton ClimberRight;


	/**
	 * Initializes a ButtonBox Controller on a specific channel, mapping the buttons. The
	 * 
	 * @param channel the channel the ButtonBox is plugged into
	 */
	public ButtonBox(int channel) {
		super(channel);
		
		WideFocus = new JoystickButton(this, 3);
		FineFocus = new JoystickButton(this, 2);
		Fire = new JoystickButton(this, 1);
		IntakeIn = new JoystickButton(this, 12);
		IntakeOut = new JoystickButton(this, 5);
		Hopper = new JoystickButton(this, 4);
		CPDeploy = new JoystickButton(this, 6);
		CPSpin = new JoystickButton(this, 11);
		CPYellow = new JoystickButton(this, 10);
		CPGreen = new JoystickButton(this, 9);
		CPRed = new JoystickButton(this, 8);
		CPBlue = new JoystickButton(this, 7);
		ClimberDeploy = new JoystickButton(this, 16);
		Climb = new JoystickButton(this, 15);
		ClimberLeft = new JoystickButton(this, 14);
		ClimberRight = new JoystickButton(this, 13);
	}


	/**
	 * Public Button Getters
	 * @return ButtonBox button
	 */
	
	public JoystickButton getWideFocus() {
		return this.WideFocus;
	}


	public JoystickButton getFineFocus() {
		return this.FineFocus;
	}


	public JoystickButton getFire() {
		return this.Fire;
	}


	public JoystickButton getIntakeIn() {
		return this.IntakeIn;
	}


	public JoystickButton getIntakeOut() {
		return this.IntakeOut;
	}


	public JoystickButton getHopper() {
		return this.Hopper;
	}


	public JoystickButton getCPDeploy() {
		return this.CPDeploy;
	}


	public JoystickButton getCPSpin() {
		return this.CPSpin;
	}


	public JoystickButton getCPYellow() {
		return this.CPYellow;
	}


	public JoystickButton getCPGreen() {
		return this.CPGreen;
	}


	public JoystickButton getCPRed() {
		return this.CPRed;
	}


	public JoystickButton getCPBlue() {
		return this.CPBlue;
	}


	public JoystickButton getClimberDeploy() {
		return this.ClimberDeploy;
	}


	public JoystickButton getClimb() {
		return this.Climb;
	}


	public JoystickButton getClimberLeft() {
		return this.ClimberLeft;
	}


	public JoystickButton getClimberRight() {
		return this.ClimberRight;
	}

}