package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmPID {

    private CANSparkMax motor;
    private SparkMaxPIDController pid;
    public RelativeEncoder encoder;

    private int curLimit;
    
    public double currentTarget = 0;

    // PID coefficients
    private final double kP = 5e-5;
    private final double kI = 1e-7;
    private final double kD = 1e-6;
    private final double kIz = 0;
    private final double kFF = 0.000156;
    private final double kMaxOutput = 1;
    private final double kMinOutput = -1;
    private final double maxRPM = 5700;

    //smart motion
    private final double maxVel = 2000;
    private final double maxAcc = 10000;



    public ArmPID(int id) {
        motor = new CANSparkMax(id, MotorType.kBrushless);
        this.pid = motor.getPIDController();
        encoder = motor.getEncoder();
        //set pid coeff.;
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

        SmartDashboard.putNumber("Arm Current Limit", 0);
        SmartDashboard.putNumber("SetPoint", 0);
    }

    public void setMotorRaw(double speed) {
        motor.set(speed);
    }

    public void setTarget(double setPoint) {

        currentTarget = setPoint;
        pid.setReference(setPoint, CANSparkMax.ControlType.kSmartMotion);
        
    }

    public void updateStats() {
        motor.setSmartCurrentLimit(curLimit);
        //SmartDashboard.putNumber("SetPoint", currentTarget);
        SmartDashboard.putNumber("Process Variable", encoder.getPosition());
        SmartDashboard.putNumber("Output Current", motor.getOutputCurrent());
        curLimit = (int) SmartDashboard.getNumber("Arm Current Limit", 0);
        //System.out.println(limit_switch.get());
        //This calculation will be more complicated, waiting to do pid stuff before I change
        SmartDashboard.putBoolean("Point Reached", (encoder.getPosition()+10 >= currentTarget && encoder.getPosition()-10 <= currentTarget));
    }


}
