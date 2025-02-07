package org.example.Algorithms.SAAlgorithm;

import org.example.Point;

import java.util.ArrayList;

//source: https://www.baeldung.com/java-simulated-annealing-for-traveling-salesman#bd-overview-1

public class SAAlgorithm {
    private static double startingTemperature = 10;
    private static int numberOfIterations =500;
    private static double coolingRate = 0.995;

    private static Travel travel;

    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        System.out.println("Starting SA with temperature: " + startingTemperature + ", # of iterations: " + numberOfIterations + " and colling rate: " + coolingRate);
        travel = new Travel(points);
        double t = startingTemperature;
        double bestDistance = travel.getDistance();
        System.out.println("Initial distance of travel: " + bestDistance);
        Travel currentSolution = travel;

        for (int i = 0; i < numberOfIterations; i++) {
            if (t > 1) {
                currentSolution.swapPoints();
                double currentDistance = currentSolution.getDistance();
                if (currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()*100) {
                    currentSolution.revertSwap();
                }
                t *= coolingRate;
            }

        }
        System.out.println("Final distance of travel: " + bestDistance);
        return currentSolution.getPoints();
    }


}