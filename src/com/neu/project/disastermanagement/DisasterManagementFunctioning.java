package com.neu.project.disastermanagement;

import com.neu.project.graphics.DisplayGraphicsForSwarm;
import com.neu.project.initialization.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author rohan
 */
public class DisasterManagementFunctioning {

    int TOTAL_SENSORS = 35;
    int ITERATIONS = 100;
    double CONS_VAL1 = 2.0;
    double CONS_VAL2 = 2.0;
    double INERTIA_MAX = 1.0;
    double INERTIA_MIN = 0.0;
    private ArrayList<Sensor> sensorSwarm = new ArrayList<>();
    private double[] sensorPBest = new double[TOTAL_SENSORS];
    private ArrayList<LocationCoordinate> sensorPBestLocation = new ArrayList<>();
    private double sensorGlobalBest;
    private LocationCoordinate sensorGlobalBestLocation;
    private double[] sensorFitnessList = new double[TOTAL_SENSORS];
    DisplayGraphicsForSwarm drawSwarm = new DisplayGraphicsForSwarm();
    Random generator = new Random();

    /*
    
    start the swarm 
    
     */
    public void startFunctioning() {

        int iteration = 0;
        double inertia;
        double tolerance = 9999;
        initiateGraphicsForSwarm();
        setSensorSpace();
        modifySensorFitness();

        for (int i = 0; i < TOTAL_SENSORS; i++) {

            sensorPBest[i] = sensorFitnessList[i];
            sensorPBestLocation.add(sensorSwarm.get(i).getLocation());
        }

        while (iteration < ITERATIONS && tolerance > ParticleFitnessFunctioning.TOLERANCE) {

            // this loop is for personal best of sensors and their locations
            for (int i = 0; i < TOTAL_SENSORS; i++) {
                if (sensorFitnessList[i] < sensorPBest[i]) {
                    sensorPBest[i] = sensorFitnessList[i];
                    sensorPBestLocation.set(i, sensorSwarm.get(i).getLocation());
                }
            }

            // this condition sets the global best sensor and its locations
            int bestParticleIndex = getSensorMinimumPosition(sensorFitnessList);
            if (iteration == 0 || sensorFitnessList[bestParticleIndex] < sensorGlobalBest) {

                sensorGlobalBest = sensorFitnessList[bestParticleIndex];
                sensorGlobalBestLocation = sensorSwarm.get(bestParticleIndex).getLocation();
            }

            // calculate the inertia or the weights
            inertia = INERTIA_MAX - (((double) iteration) / ITERATIONS) * (INERTIA_MAX - INERTIA_MIN);

            // calculate new velocities and locations for each particle
            for (int i = 0; i < TOTAL_SENSORS; i++) {

                double r1 = generator.nextDouble();
                double r2 = generator.nextDouble();
                Sensor s = sensorSwarm.get(i);

                // calculate velocity for one particle
                double[] newVel = new double[2];
                newVel[0] = (inertia * s.getVelocity().getVelocity()[0])
                        + (r1 * CONS_VAL1) * (sensorPBestLocation.get(i).getLocation()[0] - s.getLocation().getLocation()[0])
                        + (r2 * CONS_VAL2) * (sensorGlobalBestLocation.getLocation()[0] - s.getLocation().getLocation()[0]);
                newVel[1] = (inertia * s.getVelocity().getVelocity()[1])
                        + (r1 * CONS_VAL1) * (sensorPBestLocation.get(i).getLocation()[1] - s.getLocation().getLocation()[1])
                        + (r2 * CONS_VAL2) * (sensorGlobalBestLocation.getLocation()[1] - s.getLocation().getLocation()[1]);
                VelocityCoordinate vel = new VelocityCoordinate(newVel);
                s.setVelocity(vel);

                // calculate location for same particle
                double[] newLoc = new double[2];
                newLoc[0] = s.getLocation().getLocation()[0] + newVel[0];
                newLoc[1] = s.getLocation().getLocation()[1] + newVel[1];
                LocationCoordinate loc = new LocationCoordinate(newLoc);
                s.setLocation(loc);
            }

            drawSwarm.drawGraphics(sensorSwarm);

            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(DisasterManagementFunctioning.class.getName()).log(Level.SEVERE, null, ex);
            }

            // calculate new tolerance to minimize the function
            tolerance = ParticleFitnessFunctioning.generateFitnessValues(sensorGlobalBestLocation);
            iteration++;

            // modify the sensor's fitness function with new values
            modifySensorFitness();

        }

        System.out.println("     Global X coordiante: " + sensorGlobalBestLocation.getLocation()[0]);
        System.out.println("     Global Y coordinate: " + sensorGlobalBestLocation.getLocation()[1]);
    }

    /*
    
    instantiate the sensor space
    
     */
    public void setSensorSpace() {

        Sensor s;
        for (int i = 0; i < TOTAL_SENSORS; i++) {

            s = new Sensor();

            // set random location
            double[] loc = new double[2];
            loc[0] = ParticleFitnessFunctioning.X_LOWER_LOCATION + generator.nextDouble() * (ParticleFitnessFunctioning.X_HIGHER_LOCATION - ParticleFitnessFunctioning.X_LOWER_LOCATION);
            loc[1] = ParticleFitnessFunctioning.Y_LOWER_LOCATION + generator.nextDouble() * (ParticleFitnessFunctioning.Y_HIGHER_LOCATION - ParticleFitnessFunctioning.Y_LOWER_LOCATION);
            LocationCoordinate location = new LocationCoordinate(loc);

            // set random velocity
            double[] vel = new double[2];
            vel[0] = ParticleFitnessFunctioning.LOWER_VELOCITY + generator.nextDouble() * (ParticleFitnessFunctioning.HIGHER_VELOCITY - ParticleFitnessFunctioning.LOWER_VELOCITY);
            vel[1] = ParticleFitnessFunctioning.LOWER_VELOCITY + generator.nextDouble() * (ParticleFitnessFunctioning.HIGHER_VELOCITY - ParticleFitnessFunctioning.LOWER_VELOCITY);
            VelocityCoordinate velocity = new VelocityCoordinate(vel);

            s.setLocation(location);
            s.setVelocity(velocity);
            sensorSwarm.add(s);

        }
    }

    /*
    
    modify the fitness values of each sensor
    
     */
    public void modifySensorFitness() {
        for (int i = 0; i < TOTAL_SENSORS; i++) {
            sensorFitnessList[i] = sensorSwarm.get(i).getFitnessValue();
        }
    }

    /*
    
     method to get the index of the sensor with minimum distance from the target
   
     */
    public static int getSensorMinimumPosition(double[] list) {
        int pos = 0;
        double minValue = list[0];

        for (int i = 0; i < list.length; i++) {
            if (list[i] < minValue) {
                pos = i;
                minValue = list[i];
            }
        }

        return pos;
    }

    /**
     * This method initiates the graphics parameters for the swarm
     */
    public void initiateGraphicsForSwarm() {

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(drawSwarm);
        frame.setBackground(Color.BLACK);
    }

}
