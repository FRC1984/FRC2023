package frc.robot;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Smart%20Motion%20Example/src/main/java/frc/robot/Robot.java

public class Arm {
    private CANSparkMax motor;
    private SparkMaxPIDController pid;
    private RelativeEncoder encoder;
    
    private Intake intake;
    // PID coefficients
    private final double kP = 5e-5;
    private final double kI = 1e-6;
    private final double kD = 0;
    private final double kIz = 0;
    private final double kFF = 0.000156;
    private final double kMaxOutput = 1;
    private final double kMinOutput = -1;
    private final double maxRPM = 5700;

    //smart motion
    private final double maxVel = 2000;
    private final double maxAcc = 1500;

    //Not used yet
    //private final double minVel = 0;
    //private final double allowedErr = 0;


    public Arm(int id) {
        motor = new CANSparkMax(id, MotorType.kBrushless);
        this.pid = motor.getPIDController();
        encoder = motor.getEncoder();

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
        //pid.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        pid.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        //pid.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
    }

    public void setTarget(double setPoint) {


        pid.setReference(setPoint, CANSparkMax.ControlType.kSmartMotion);
        double processVariable = encoder.getPosition();
        
        SmartDashboard.putNumber("SetPoint", setPoint);
        SmartDashboard.putNumber("Process Variable", processVariable);
        SmartDashboard.putNumber("Output", motor.getAppliedOutput());
    }
}