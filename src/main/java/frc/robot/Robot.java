// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final CANSparkMax leftDrive1 = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax leftDrive2 = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax rightDrive1 = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax rightDrive2 = new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax clawArm = new CANSparkMax(6, MotorType.kBrushless);
  private final MotorControllerGroup leftDrive = new MotorControllerGroup(leftDrive1, leftDrive2);
  private final MotorControllerGroup rightDrive = new MotorControllerGroup(rightDrive1, rightDrive2);
  private final DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);
  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();
  private final Joystick stick = new Joystick(0);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    leftDrive.setInverted(true);
    CameraServer.startAutomaticCapture(0);
    //CameraServer.startAutomaticCapture(1);
    //server = CameraServer.getServer();
    //cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      drive.arcadeDrive(.3, 0.2);
    } else  if (m_timer.get() < 4.0) {
      drive.arcadeDrive(-0.3, -0.2);
    } else {
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {

    drive.arcadeDrive(stick.getY(), stick.getX());
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    //drive.arcadeDrive(stick.getY(), stick.getX());
    if (stick.getRawButton(0)||stick.getRawButtonPressed(0)) {
      clawArm.disable();
    } else {
      clawArm.set(stick.getRawAxis(3));
    }
  }
}
