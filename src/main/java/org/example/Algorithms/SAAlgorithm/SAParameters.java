package org.example.Algorithms.SAAlgorithm;

public class SAParameters {
    private double startingTemperature = 10;
    private double stoppingTemperature = 0.1;
    private int numberOfIterations =500;
    private double coolingRate = 0.995;
    private double stepRate = 0.01;


    public SAParameters(double startingTemperature, double stoppingTemperature, int numberOfIterations, double coolingRate, double stepRate) {
        this.startingTemperature = startingTemperature;
        this.stoppingTemperature = stoppingTemperature;
        this.numberOfIterations = numberOfIterations;
        this.coolingRate = coolingRate;
        this.stepRate = stepRate;
    }

    public SAParameters(){}

    public double startingTemperature() {
        return startingTemperature;
    }

    public double stoppingTemperature() {
        return stoppingTemperature;
    }

    public int numberOfIterations() {
        return numberOfIterations;
    }

    public double coolingRate() {
        return coolingRate;
    }

    public double stepRate() {
        return stepRate;
    }
}
