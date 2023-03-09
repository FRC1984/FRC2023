package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//JUST A LAYOUT, NOT FOR ACTUAL USE YET

public class Intake {
    private CANSparkMax motor;
    private int curLimit;

    public Intake(int id) {
        motor = new CANSparkMax(id, MotorType.kBrushless);
    }


    public void run(double speed) {
        motor.set(speed);
    }


    public void updateStats() {
        SmartDashboard.putNumber("Intake Current", motor.getOutputCurrent());
        //SmartDashboard.putNumber("Intake Limit", curLimit);
        curLimit = (int) SmartDashboard.getNumber("Intake Limit", 0);
        motor.setSmartCurrentLimit(curLimit);
        SmartDashboard.putNumber("Intake Voltage", motor.getVoltageCompensationNominalVoltage());
    }
}
