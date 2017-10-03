package com.neu.project.disastermanagement;

import com.neu.project.initialization.*;

/**
 *
 * @author rohan
 */
public class ParticleFitnessFunctioning {

    public static final double X_LOWER_LOCATION = 0;
    public static final double X_HIGHER_LOCATION = 100;
    public static final double Y_LOWER_LOCATION = 0;
    public static final double Y_HIGHER_LOCATION = 100;
    public static final double LOWER_VELOCITY = 1;
    public static final double HIGHER_VELOCITY = 2;

    // the smaller the tolerance, the more accurate the result, 
    // but the number of iteration is increased
    public static final double TOLERANCE = 1E-323;

    public static double generateFitnessValues(LocationCoordinate location) {

        double result = 0;
        double x = location.getLocation()[0]; // the "x" part of the location
        double y = location.getLocation()[1]; // the "y" part of the location

        double[] epicentre = new double[2];
        epicentre[0] = 240;
        epicentre[1] = 560;

        // equation to be minimized
        result = Math.sqrt(Math.pow((x - epicentre[0]), 2) + Math.pow((y - epicentre[1]), 2));
        return result;
    }
}
