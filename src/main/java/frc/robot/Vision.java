package frc.robot;

import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

import org.opencv.core.*;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;

public class Vision {

    public static void init() {
        GripPipeline pipeline = new GripPipeline();
        Thread visionThread = new Thread(
                () -> {
                    // Get the UsbCamera from CameraServer
                    UsbCamera camera = CameraServer.startAutomaticCapture();
                    // Set the resolution
                    camera.setResolution(640, 480);

                    // Get a CvSink. This will capture Mats from the camera
                    CvSink cvSink = CameraServer.getVideo();
                    // Setup a CvSource. This will send images back to the Dashboard
                    CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);

                    // Mats are very memory expensive. Lets reuse this Mat.
                    Mat mat = new Mat();

                    // This cannot be 'true'. The program will never exit if it is. This
                    // lets the robot stop this thread when restarting robot code or
                    // deploying.
                    while (!Thread.interrupted()) {
                        // Tell the CvSink to grab a frame from the camera and put it
                        // in the source mat. If there is an error notify the output.
                        if (cvSink.grabFrame(mat) == 0) {
                            // Send the output the error.
                            outputStream.notifyError(cvSink.getError());
                            // skip the rest of the current iteration
                            continue;
                        }
                        // process image
                        pipeline.process(mat);

                        // get grip process output
                        ArrayList<MatOfPoint> contours = pipeline.convexHullsOutput();

                        // make bounding box by getting min/max pixels

                        Point first = contours.get(0).toArray()[0];
                        double maxX = first.x, minX = first.x;
                        double maxY = first.y, minY = first.y;

                        for (MatOfPoint contour : contours) {
                            for (int i = 0; i < contour.toArray().length; i++) {
                                Point point = contour.toArray()[i];

                                // update min/max
                                maxX = (point.x > maxX) ? point.x : maxX;
                                minX = (point.x < minX) ? point.x : minX;
                                maxY = (point.y > maxY) ? point.y : maxY;
                                minY = (point.y < minY) ? point.y : minY;

                            }
                        }

                        //draw bounding box
                        Imgproc.rectangle(
                                mat, new Point(minX, minY), new Point(maxX, maxY), new Scalar(255, 255, 255), 5);
                        
                        // Give the output stream a new image to display
                        outputStream.putFrame(mat);
                    }
                });
        visionThread.setDaemon(true);
        visionThread.start();
    }
}