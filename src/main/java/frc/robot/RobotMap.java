/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.I2C;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  //List of all PWM ports
	private final static int PWM_0 = 0;
	private final static int PWM_1 = 1;
	private final static int PWM_2 = 2;
	private final static int PWM_3 = 3;
	private final static int PWM_4 = 4;
	private final static int PWM_5 = 5;
	private final static int PWM_6 = 6;
	private final static int PWM_7 = 7;
	private final static int PWM_8 = 8;
	private final static int PWM_9 = 9;
	// see https://www.pdocs.kauailabs.com/navx-mxp/installation/io-expansion/
	private final static int PWM_MXP_0 = 10;
	private final static int PWM_MXP_1 = 11;
	private final static int PWM_MXP_2 = 12;
	private final static int PWM_MXP_3 = 13;
	private final static int PWM_MXP_4 = 14;
	private final static int PWM_MXP_5 = 15;
	private final static int PWM_MXP_6 = 16;
	private final static int PWM_MXP_7 = 17;
	private final static int PWM_MXP_8 = 18;
	private final static int PWM_MXP_9 = 19;

	// List of PCM CAN IDs
	private final static int PCM_CAN = 16;
	private final static int PCM_CAN_2 = 18;

	// List of Talon SRX CAN IDs
	private final static int SRX_CAN_0 = 0;
	private final static int SRX_CAN_1 = 1;
	private final static int SRX_CAN_2 = 2;
	private final static int SRX_CAN_3 = 3;

	// List of CAN IDs
	private final static int CAN_0 = 0; 
	private final static int CAN_1 = 1; 
	private final static int CAN_2 = 2; 
	private final static int CAN_3 = 3; 
	private final static int CAN_4 = 4; 
	private final static int CAN_5 = 5;
	private final static int CAN_6 = 6; 
	private final static int CAN_7 = 7; 
	private final static int CAN_8 = 8;
	private final static int CAN_9 = 9; 
	private final static int CAN_10 = 10;
	private final static int CAN_11 = 11;
	private final static int CAN_12 = 12; 
	private final static int CAN_13 = 13; 
	private final static int CAN_14 = 14; 
    private final static int CAN_15 = 15; 
    
	//List of all analog ports
	private final static int ANALOG_0 = 0;
	private final static int ANALOG_1 = 1;
	private final static int ANALOG_2 = 2;
	private final static int ANALOG_3 = 3;
	// see https://www.pdocs.kauailabs.com/navx-mxp/installation/io-expansion/
	private final static int ANALOG_MXP_0 = 4;
	private final static int ANALOG_MXP_1 = 5;
	private final static int ANALOG_MXP_2 = 6;
	private final static int ANALOG_MXP_3 = 7;

	//List of all relays
	private final static int RELAY_0 = 0;
	private final static int RELAY_1 = 1;
	private final static int RELAY_2 = 2;
	private final static int RELAY_3 = 3;

	//List of all DIO ports
	private final static int DIO_0 = 0;
	private final static int DIO_1 = 1;
	private final static int DIO_2 = 2;
	private final static int DIO_3 = 3;
	private final static int DIO_4 = 4;
	private final static int DIO_5 = 5;
	private final static int DIO_6 = 6;
	private final static int DIO_7 = 7;
	private final static int DIO_8 = 8;
	private final static int DIO_9 = 9;

	// see https://www.pdocs.kauailabs.com/navx-mxp/installation/io-expansion/
	private final static int DIO_MXP_0 = 10;
	private final static int DIO_MXP_1 = 11;
	private final static int DIO_MXP_2 = 12;
	private final static int DIO_MXP_3 = 13;
	private final static int DIO_MXP_4 = 18;
	private final static int DIO_MXP_5 = 19;
	private final static int DIO_MXP_6 = 20;
	private final static int DIO_MXP_7 = 21;
	private final static int DIO_MXP_8 = 22;
	private final static int DIO_MXP_9 = 23;

	//List of all USB ports
	private static final int USB_0 = 0;
	private static final int USB_1 = 1;
	private static final int USB_2 = 2;
	private static final int USB_3 = 3;

	// Pneumatic Control Module (PCM) ports
	private static final int PCM_0 = 0;
	private static final int PCM_1 = 1;
	private static final int PCM_2 = 2;
	private static final int PCM_3 = 3;
	private static final int PCM_4 = 4;
	private static final int PCM_5 = 5;
	private static final int PCM_6 = 6;
	private static final int PCM_7 = 7;
	private static final int PCM_8 = 8;

	//List of all PDP ports
	private static final int PDP_0 = 0;
	private static final int PDP_1 = 1;
	private static final int PDP_2 = 2;
	private static final int PDP_3 = 3;
	private static final int PDP_4 = 4;
	private static final int PDP_5 = 5;
	private static final int PDP_6 = 6;
	private static final int PDP_7 = 7;
	private static final int PDP_8 = 8;
	private static final int PDP_9 = 9;
	private static final int PDP_10 = 10;
	private static final int PDP_11 = 11;
	private static final int PDP_12 = 12;
	private static final int PDP_13 = 13;
	private static final int PDP_14 = 14;
	private static final int PDP_15 = 15;
	private static final int PDP_16 = 16;

	//[D]rive
	public static final int D_FRONT_LEFT = CAN_14;
	public static final int D_FRONT_RIGHT = CAN_1;
	public static final int D_BACK_LEFT = CAN_15;
	public static final int D_BACK_RIGHT = CAN_0;
	public static final Port D_NAVX = SPI.Port.kMXP;
	
	//[U]ser Input
	public static final int U_JOYSTICK_LEFT = 0;
	public static final int U_JOYSTICK_RIGHT = 1;
	public static final int U_XBOX_CONTROLLER = 2;

	//[B]all Intake
	public static final int B_INTAKE_ROLLER = CAN_5;
	public static final int B_PCM_CAN = PCM_CAN;
	public static final int B_PISTON_PORT2 = PCM_1;
	public static final int B_PISTON_PORT3 = PCM_6;

	//[C]onveyer
	public static final int C_HCONVEYOR_L = CAN_4;
	public static final int C_HCONVEYOR_R = CAN_11;
	public static final int C_VCONVEYOR = CAN_9;
	public static final int C_INCOMING = DIO_0;
	public static final int C_READYTOSHOOT = DIO_1;

	//[S]pinner
	public static final int S_MOTOR = PWM_3;
	public static final int S_PCM_CAN = PCM_CAN;
	public static final int S_PISTON_PORT0 = PCM_4;
	public static final int S_PISTON_PORT1 = PCM_5;
	public static final I2C.Port S_COLOR_SENSOR = I2C.Port.kOnboard;

	//[T]urret
	public static final int T_MOTOR = CAN_3;
	public static final int T_LEFT_STOP = DIO_2;
	public static final int T_RIGHT_STOP = DIO_3;

	//[SH]ooter
	public static final int SH_TOP = CAN_13;
	public static final int SH_BOTTOM = CAN_2;
	public static final int SH_PCM_CAN = PCM_CAN;
	public static final int SH_PISTON_PORT0 = PCM_0;
	public static final int SH_PISTON_PORT1 = PCM_7;

	//Encoder mappings are placeholders, will replace on 2/23/20
	public static final int SH_TOP_ENCODER_A = DIO_MXP_0;
	public static final int SH_TOP_ENCODER_B = DIO_MXP_1;
	public static final int SH_BOT_ENCODER_A = DIO_MXP_2;
	public static final int SH_BOT_ENCODER_B = DIO_MXP_3;

}
