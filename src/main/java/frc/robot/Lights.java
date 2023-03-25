package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;



public class Lights {
    private Relay spike1, spike2;

    public Lights(int channel1, int channel2) {
        spike1 = new Relay(channel1);
        spike2 = new Relay(channel2);
    }


    public void on() {
        spike1.set(Value.kForward);
        spike2.set(Value.kForward);
    }
    public void off() {
        spike1.set(Value.kOff);
        spike2.set(Value.kOff);
    }
}
//unfinished
/* 
public class Lights {

    private int numLights = 147;
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;

    public Lights() {
        m_led = new AddressableLED(0);
        m_ledBuffer = new AddressableLEDBuffer(numLights);
        m_led.setLength(m_ledBuffer.getLength());
    
        // Set the data
        m_led.setData(m_ledBuffer);
        m_led.start();
    }


    public void setSolidColor(int r, int g, int b) {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, r, g, b);
         }
         
         m_led.setData(m_ledBuffer);
    }
}
*/