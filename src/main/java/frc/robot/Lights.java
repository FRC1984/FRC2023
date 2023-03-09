package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;


//unfinished
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
