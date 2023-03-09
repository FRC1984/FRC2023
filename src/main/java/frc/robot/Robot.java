// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.lib.tools.PIDTuner;
//import com.analog.adis16448.frc.ADIS16448_IMU;
/*
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */

 public class Robot extends TimedRobot {
  //private final CANSparkMax clawArm = new CANSparkMax(6, MotorType.kBrushless);
  //private final PIDTuner tuner = new PIDTuner(7);
  private final Timer m_timer = new Timer();
  //private final Joystick stick = new Joystick(0);
  //private final XboxController cont = new XboxController(0);
  //private final Intake intake = new Intake(6 , stick);
  //private final Solenoid sol = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
  private final Arm arm = new Arm(7);
  //private final Drivetrain drive = new Drivetrain(1, 2, 3, 4, stick, .07);
  //private final Lights lights = new Lights();

  @Override
  public void robotInit() {
    //arm.updateStats();
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
    //drive.drive(0.1, 0);
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    //System.out.println(pixyCam.getBiggestBlock());
    //drive.go();
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
    m_timer.reset();
    m_timer.start();
    arm.reset();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    //lights.setSolidColor(255, 255, 255);
    //drive.drive();
    //tuner.run();
    //drive.updateStats();
   // sol.set((int) m_timer.get() % 2 == 0);

    //arm.runSemiRaw();
    //arm.updateStats();
    //arm.test();
    arm.runTest();
    arm.updateStats();
    //intake.run();
    //intake.updateStats();
    /* 
    SmartDashboard.putNumber("Joystick X", stick.getX());
    SmartDashboard.putNumber("Joystick Y", stick.getY());
    SmartDashboard.putNumber("Joystick Z", stick.getZ());
    */
    
  }
}
