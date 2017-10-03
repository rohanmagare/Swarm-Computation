package com.neu.project.initialization;

/**
 *
 * @author rohan
 */
public class VelocityCoordinate {

    private double[] velocity;

    public VelocityCoordinate(double[] vel) {

        this.velocity = vel;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

}
