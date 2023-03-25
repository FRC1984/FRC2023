package frc.robot;


import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Smart%20Motion%20Example/src/main/java/frc/robot/Robot.java

public class Arm {

    private DigitalInput limit_switch = new DigitalInput(1);

    private boolean locationKnown = false;
    private Joystick cont;
    private ArmPID armPID;
    private Intake intake;


    //TODO: SET THESE
    private final double STOWPOS = 0;
    private final double CONEPOS = 5;
    private final double CUBEPICKUP = 20;
    private final double FLOORPOS = 90;


    public Arm(int armId, int intakeId, Joystick cont) {
        //motor = new CANSparkMax(armId, MotorType.kBrushless);
        this.cont = cont;
        armPID = new ArmPID(armId);
        intake = new Intake(intakeId);
        SmartDashboard.putNumber("Arm Current Limit", 0);
        callibrate();
    }

    public Arm(int armId, int intakeId) {
        armPID = new ArmPID(armId);
        intake = new Intake(intakeId);
        SmartDashboard.putNumber("Arm Current Limit", 0);
        callibrate();
    }
    public Arm(int armId) {
        armPID = new ArmPID(armId);
        SmartDashboard.putNumber("Arm Current Limit", 0);
        callibrate();
    }

    public void reset() {
        locationKnown = false;
    }

    public void callibrate() {
        if (!limit_switch.get()) {
            locationKnown = true;
            armPID.encoder.setPosition(0);
        }

        if (!locationKnown) {
            armPID.setMotorRaw(-0.3);
        }
    }
    public void runRaw() {
        armPID.setMotorRaw(-cont.getY());
    }

    public void runCont() {
        if (!locationKnown) {
            callibrate();
        } else {
            if (cont.getRawButton(11)) {
                armPID.setTarget(STOWPOS);
            } else if (cont.getRawButton(12)) {
                armPID.setTarget(FLOORPOS);
            } else if (cont.getRawButton(10)) {
                armPID.setTarget(CONEPOS);
            } else if (cont.getRawButton(8)) {
                armPID.setTarget(0);
            } 
            intake.run(cont.getY());
        }
        
    }
    public void runTest() {

        if (!locationKnown) {
            callibrate();
        } else {
            armPID.setTarget(SmartDashboard.getNumber("SetPoint", 0));
        }
    }

    public void updateStats() {
        armPID.updateStats();
        //intake.updateStats();
    }
}