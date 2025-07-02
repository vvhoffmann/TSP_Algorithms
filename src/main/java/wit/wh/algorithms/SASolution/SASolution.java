package wit.wh.algorithms.SASolution;

import wit.wh.algorithms.TSPSolution;
import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;

import java.util.ArrayList;

/**
 * Simulated Annealing implementation for solving the Traveling Salesman Problem (TSP).
 * <p>
 * This algorithm probabilistically accepts worse solutions as it explores the search space,
 * gradually reducing the probability of doing so as the "temperature" decreases.
 * </p>
 */
public class SASolution extends TSPSolution {

    /**
     * The current path being evaluated.
     */
    private Path currentPath;

    /**
     * The shortest distance found so far.
     */
    private double bestDistance;

    /**
     * The path that yields the shortest distance found so far.
     */
    private Path bestPath;

    /**
     * Constructs the Simulated Annealing algorithm instance with the given ((SAParameters) parameters).
     *
     * @param inputPoints the list of points representing the initial path
     * @param parameters  algorithm configuration settings
     */
    public SASolution(ArrayList<Point> inputPoints, SAParameters parameters) {
        super(inputPoints, parameters);
    }

    /**
     * Solves the TSP using Simulated Annealing and returns the optimized list of points.
     * The process starts with an initial path and repeatedly attempts to improve it
     * by randomly swapping points and accepting worse solutions with a probability
     * that decreases over time (as temperature cools).
     *
     * @return optimized list of points forming a near-optimal TSP path
     */
    public ArrayList<Point> getTSPSolution() {
        initializePath();
        double currentTemperature = ((SAParameters) parameters).startingTemperature();

        for (int i = 0; i < parameters.numberOfIterations(); ++i) {
            if (isAboveStoppingTemperature(currentTemperature)) {
                performAnnealingStep(currentTemperature);
                currentTemperature = getReducedTemperature(currentTemperature);
            }
        }

        return PathUtils.returnRoundPath(bestPath.getPoints());
    }

    /**
     * Calculates the reduced temperature for the next iteration
     * by applying the cooling rate defined in the SAParameters.
     *
     * @param currentTemperature the current temperature before reduction
     * @return the temperature after applying the cooling rate
     */
    private double getReducedTemperature(double currentTemperature) {
        currentTemperature *= ((SAParameters) parameters).coolingRate();
        return currentTemperature;
    }

    /**
     * Checks whether the current temperature is still above the stopping threshold
     * defined in the SAParameters.
     *
     * @param currentTemperature the current temperature
     * @return {@code true} if the temperature is above the stopping threshold,
     *         {@code false} otherwise
     */
    private boolean isAboveStoppingTemperature(double currentTemperature) {
        return currentTemperature > ((SAParameters) parameters).stoppingTemperature();
    }

    /**
     * Initializes the current and best paths using the provided input points,
     * and sets the initial best distance.
     */
    private void initializePath() {
        this.currentPath = new Path(new ArrayList<>(inputPoints));
        this.bestPath = new Path(new ArrayList<>(inputPoints));
        bestDistance = currentPath.getDistance();
    }

    /**
     * Performs one step of the Simulated Annealing algorithm by:
     * <ul>
     *     <li>Evaluating the distance after a swap.</li>
     *     <li>Accepting it if it's better than the current best.</li>
     *     <li>Otherwise, possibly reverting based on acceptance probability.</li>
     * </ul>
     *
     * @param currentTemperature the current temperature in the annealing schedule
     */
    private void performAnnealingStep(double currentTemperature) {
        currentPath.swapRandomPoints();

        double newDistance = currentPath.getDistance();

        if (newDistance < bestDistance){
            bestDistance = newDistance;
            bestPath = new Path(currentPath);
        }
        else if (shouldRevertSwap(bestDistance, newDistance, currentTemperature))
            currentPath.revertSwap();
    }

    /**
     * Determines whether to reject a worse solution based on simulated annealing acceptance probability.
     *
     * @param bestDistance the best distance found so far
     * @param newDistance the new (worse) distance after a swap
     * @param temperature the current temperature
     * @return true if the swap should be reverted, false if it should be accepted
     */
    private boolean shouldRevertSwap(double bestDistance, double newDistance, double temperature) {
        double acceptanceProbability = Math.exp((bestDistance - newDistance) / temperature);
        return acceptanceProbability < Math.random() * ((SAParameters) parameters).stepRate();
    }
}
