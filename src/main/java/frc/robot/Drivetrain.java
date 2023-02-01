package frc.robot;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain {
    
    private static CANSparkMax leftDrive1, leftDrive2,
                               rightDrive1, rightDrive2;
                               
    private static MotorControllerGroup leftDrive,
                                        rightDrive;

    private static DifferentialDrive drive;

    private Joystick controller;

    public Drivetrain(int ld1, int ld2, int rd1, int rd2, Joystick cont) {
        leftDrive1 = new CANSparkMax(ld1, MotorType.kBrushless);
        leftDrive2 = new CANSparkMax(ld1, MotorType.kBrushless);
        rightDrive1 = new CANSparkMax(ld1, MotorType.kBrushless);
        leftDrive1 = new CANSparkMax(ld1, MotorType.kBrushless);

    }

}
