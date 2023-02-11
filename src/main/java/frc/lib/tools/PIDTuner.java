package frc.lib.tools;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Smart%20Motion%20Example/src/main/java/frc/robot/Robot.java

//use Shuffleboard and load the config in Downloads

public class PIDTuner {
    private CANSparkMax motor;
    private SparkMaxPIDController pid;
    private RelativeEncoder encoder;

    // PID coefficients
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;


    public PIDTuner(int id) {
        motor = new CANSparkMax(id, MotorType.kBrushless);
        this.pid = motor.getPIDController();
        encoder = motor.getEncoder();
        
        // PID coefficients
        kP = 5e-5; 
        kI = 1e-6;
        kD = 0; 
        kIz = 0; 
        kFF = 0.000156; 
        kMaxOutput = 1; 
        kMinOutput = -1;
        maxRPM = 5700;

        // Smart Motion Coefficients
        maxVel = 2000; // rpm
        maxAcc = 1500;

        //set pid coeff.
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
        pid.setIZone(kIz);
        pid.setFF(kFF);
        pid.setOutputRange(kMinOutput, kMaxOutput);

        //smart motion
        int smartMotionSlot = 0;
        pid.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        pid.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        pid.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        pid.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

        // display Smart Motion coefficients
        SmartDashboard.putNumber("Max Velocity", maxVel);
        SmartDashboard.putNumber("Min Velocity", minVel);
        SmartDashboard.putNumber("Max Acceleration", maxAcc);
        SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
        SmartDashboard.putNumber("Set Position", 0);
        SmartDashboard.putNumber("Set Velocity", 0);

        // button to toggle between velocity and smart motion modes
        SmartDashboard.putBoolean("Mode", true);
    }

    public void run() {
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);
        double maxV = SmartDashboard.getNumber("Max Velocity", 0);
        double minV = SmartDashboard.getNumber("Min Velocity", 0);
        double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
        double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP)) { pid.setP(p); kP = p; }
        if((i != kI)) { pid.setI(i); kI = i; }
        if((d != kD)) { pid.setD(d); kD = d; }
        if((iz != kIz)) { pid.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { pid.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
        pid.setOutputRange(min, max); 
        kMinOutput = min; kMaxOutput = max; 
        }
        if((maxV != maxVel)) { pid.setSmartMotionMaxVelocity(maxV,0); maxVel = maxV; }
        if((minV != minVel)) { pid.setSmartMotionMinOutputVelocity(minV,0); minVel = minV; }
        if((maxA != maxAcc)) { pid.setSmartMotionMaxAccel(maxA,0); maxAcc = maxA; }
        if((allE != allowedErr)) { pid.setSmartMotionAllowedClosedLoopError(allE,0); allowedErr = allE; }

        double setPoint, processVariable;
        boolean mode = SmartDashboard.getBoolean("Mode", false);
        if(mode) {
        setPoint = SmartDashboard.getNumber("Set Velocity", 0);
        pid.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
        processVariable = encoder.getVelocity();
        } else {
        setPoint = SmartDashboard.getNumber("Set Position", 0);
        /**
         * As with other PID modes, Smart Motion is set by calling the
         * setReference method on an existing pid object and setting
         * the control type to kSmartMotion
         */
        pid.setReference(setPoint, CANSparkMax.ControlType.kSmartMotion);
        processVariable = encoder.getPosition();
        }
        
        SmartDashboard.putNumber("SetPoint", setPoint);
        SmartDashboard.putNumber("Process Variable", processVariable);
        SmartDashboard.putNumber("Output", motor.getAppliedOutput());
    }
}
