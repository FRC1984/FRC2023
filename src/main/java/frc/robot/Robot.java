// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import frc.lib.tools.PIDTuner;
/*
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */

 public class Robot extends TimedRobot {
  //private final CANSparkMax clawArm = new CANSparkMax(6, MotorType.kBrushless);
  private final PIDTuner tuner = new PIDTuner(6);
  private final Timer m_timer = new Timer();
  //private final Joystick stick = new Joystick(0);
  private final Arm arm = new Arm(6);
  //private final Drivetrain drive = new Drivetrain(1, 2, 3, 4, stick, 0.5);


  @Override
  public void robotInit() {

    //CameraServer.startAutomaticCapture(0);
    
    //pixyCam.initialize();
    
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

  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {

  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    //System.out.println(pixyCam.getBiggestBlock());
    //drive.smoothDrive();
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
    tuner.run();
  }
}
