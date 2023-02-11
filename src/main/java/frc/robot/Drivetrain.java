package frc.robot;


import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain {
    
    private CANSparkMax leftDrive1, leftDrive2,
                        rightDrive1, rightDrive2;
                               
    private MotorControllerGroup leftDrive,
                                rightDrive;

    private SlewRateLimiter rateLimit;


    private DifferentialDrive drive;

    private Joystick controller;

    public Drivetrain(int ld1, int ld2, int rd1, int rd2, Joystick cont, double smoothRate) {
        leftDrive1 = new CANSparkMax(ld1, MotorType.kBrushless);
        leftDrive2 = new CANSparkMax(ld2, MotorType.kBrushless);
        rightDrive1 = new CANSparkMax(rd1, MotorType.kBrushless);
        leftDrive1 = new CANSparkMax(rd2, MotorType.kBrushless);

        leftDrive = new MotorControllerGroup(leftDrive1, leftDrive2);
        rightDrive = new MotorControllerGroup(rightDrive1, rightDrive2);

        drive = new DifferentialDrive(leftDrive, rightDrive);

        this.rateLimit = new SlewRateLimiter(smoothRate);
        
        this.controller = cont;

    }

    public void drive() {
        drive.arcadeDrive(controller.getX(), controller.getY());
    }
    
    public void drive(double xSpeed, double zRot) {
        drive.arcadeDrive(xSpeed, zRot);
    }

    //Maybe only rate limit the x and not the turn, dont know whats better until test
    public void smoothDrive() {
        drive.arcadeDrive(rateLimit.calculate(controller.getX()), rateLimit.calculate(controller.getY()));
    }

}
