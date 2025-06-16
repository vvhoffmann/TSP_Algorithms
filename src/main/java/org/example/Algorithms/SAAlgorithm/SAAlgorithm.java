package org.example.Algorithms.SAAlgorithm;

import org.example.TSPSolution;
import org.example.pointUtils.Point;

import java.util.ArrayList;

//source:
// https://www.baeldung.com/java-simulated-annealing-for-traveling-salesman#bd-overview-1

public class SAAlgorithm extends TSPSolution {

    private Path currentPath;
    private ArrayList<Point> points;
    private SAParameters parameters;
    
    public SAAlgorithm(SAParameters parameters) {
        this.parameters = parameters;
    }

    public ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        initializePath(points);
        logStartingInfo();
        double bestDistance = currentPath.getDistance();
        System.out.println("Initial distance of path: " + bestDistance);
        
        double currentTemperature = parameters.startingTemperature();

        for (int i = 0; i < parameters.numberOfIterations(); ++i) {
            if (currentTemperature > parameters.stoppingTemperature()) {
                currentPath.swapPoints();
                double currentDistance = currentPath.getDistance();
                if (currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                } else if (Math.exp((bestDistance - currentDistance) / currentTemperature) < Math.random() * parameters.stepRate()) {
                    currentPath.revertSwap();
                }
                currentTemperature *= parameters.coolingRate();
            }

        }
        logFinalInfo(bestDistance);
        return currentPath.getPoints();
    }

    private void initializePath(ArrayList<Point> points) {
        this.points = points;
        this.currentPath = new Path(points);
    }

    private boolean shouldRevertSwap(double bestDistance, double newDistance, double temperature) {
        double acceptanceProbability = Math.exp((bestDistance - newDistance) / temperature);
        return newDistance >= bestDistance && acceptanceProbability < Math.random() * parameters.stepRate();
    }

    private void logStartingInfo() {
        System.out.printf(
                "Starting Simulated Annealing with temperature: %.2f, iterations: %d, cooling rate: %.2f%n",
                parameters.startingTemperature(),
                parameters.numberOfIterations(),
                parameters.coolingRate()
        );
        System.out.printf("Initial path distance: %.2f%n", currentPath.getDistance());
    }

    private void logFinalInfo(double bestDistance) {
        System.out.printf("Final path distance: %.2f%n", bestDistance);
    }
    
}