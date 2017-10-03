package com.neu.project.initialization;

import com.neu.project.disastermanagement.ParticleFitnessFunctioning;

/**
 *
 * @author rohan
 */
public class Sensor {

    private double fitnessValue;
    private VelocityCoordinate velocity;
    private LocationCoordinate location;

    public Sensor() {

    }

    public Sensor(double fitnessValue, VelocityCoordinate velocity, LocationCoordinate location) {
        super();
        this.fitnessValue = fitnessValue;
        this.velocity = velocity;
        this.location = location;
    }

    public VelocityCoordinate getVelocity() {
        return velocity;
    }

    public void setVelocity(VelocityCoordinate velocity) {
        this.velocity = velocity;
    }

    public LocationCoordinate getLocation() {
        return location;
    }

    public void setLocation(LocationCoordinate location) {
        this.location = location;
    }

    public double getFitnessValue() {
        fitnessValue = ParticleFitnessFunctioning.generateFitnessValues(location);
        return fitnessValue;
    }
}
