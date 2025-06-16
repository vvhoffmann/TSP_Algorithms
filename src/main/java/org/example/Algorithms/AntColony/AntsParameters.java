package org.example.Algorithms.AntColony;

/*
 * source: https://github.com/RonitRay/Ant-Colony-Optimization/blob/master/src/AntColonyOptimization.java
 * https://www.baeldung.com/java-ant-colony-optimization#bd-introduction-2
 *
 *
 * private double c = 1.0;             //number of trails
 * private double alpha = 1;           //pheromone importance
 * private double beta = 5;            //distance priority
 * private double evaporation = 0.5;
 * private double Q = 500;             //pheromone left on trail per ant
 * private double antFactor = 0.8;     //no of ants per node
 * private double randomFactor = 0.01; //introducing randomness
 * private int maxIterations = 1000;
 */

public class AntsParameters {
    private double c = 1.0;
    private double alpha = 2;
    private double beta = 6;
    private double evaporation = 0.5;
    private double Q = 500;
    private double antFactor = 0.5;
    private double randomFactor = 0.01;  //introducing randomness

    private int maxIterations = 1000;

    public AntsParameters() {}

    public AntsParameters(double c, double alpha, double beta, double evaporation, double Q, double antFactor, double randomFactor, int maxIterations) {
        this.c = c;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporation = evaporation;
        this.Q = Q;
        this.antFactor = antFactor;
        this.randomFactor = randomFactor;
        this.maxIterations = maxIterations;
    }

    public double c() {
        return c;
    }

    public double alpha() {
        return alpha;
    }

    public double beta() {
        return beta;
    }

    public double evaporation() {
        return evaporation;
    }

    public double Q() {
        return Q;
    }

    public double antFactor() {
        return antFactor;
    }

    public double randomFactor() {
        return randomFactor;
    }

    public int maxIterations() {
        return maxIterations;
    }
}
