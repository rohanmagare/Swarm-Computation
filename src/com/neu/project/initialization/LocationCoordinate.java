package com.neu.project.initialization;

/**
 *
 * @author rohan
 */
public class LocationCoordinate {

    private double[] location;

    public LocationCoordinate(double[] loc) {

        this.location = loc;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

}
