package wit.wh.algorithms.SAAlgorithm;

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
    private Path currentPath;

    /**
     * Constructs the Simulated Annealing algorithm instance with the given ((SAParameters) parameters).
     * @param inputPoints the list of points representing the initial path
     * @param parameters algorithm configuration settings
     */
    public SASolution(ArrayList<Point> inputPoints, SAParameters parameters) {
        super(inputPoints, parameters);
    }

    /**
     * Solves the TSP using Simulated Annealing and returns the optimized list of points.
     *
     * @return optimized list of points forming a near-optimal TSP path
     */
    public ArrayList<Point> getTSPSolution() {
        if (inputPoints == null)
            return new ArrayList<>();

        if(inputPoints.size() < PathUtils.MIN_POINTS_TO_RUN_ALGORITHM)
            return inputPoints;

        initializePath(inputPoints);
        logStartingInfo();

        double bestDistance = currentPath.getDistance();
        double temperature = ((SAParameters) ((SAParameters) parameters)).startingTemperature();

        for (int i = 0; i < ((SAParameters) parameters).numberOfIterations(); i++) {
            if (temperature <= ((SAParameters) parameters).stoppingTemperature()) {
                break;
            }

            performAnnealingStep(temperature);
            temperature *= ((SAParameters) parameters).coolingRate();
            bestDistance = Math.min(bestDistance, currentPath.getDistance());
        }

        logFinalInfo(bestDistance);
        return PathUtils.returnRoundPath(currentPath.getPoints());
    }

    /**
     * Initializes the path with the given list of points.
     *
     * @param points the initial list of points
     */
    private void initializePath(ArrayList<Point> points) {
        this.currentPath = new Path(new ArrayList<>(points));
    }

    /**
     * Performs a single iteration of the annealing process.
     *
     * @param temperature current temperature of the system
     */
    private void performAnnealingStep(double temperature) {
        currentPath.swapRandomPoints();
        double newDistance = currentPath.getDistance();
        double oldDistance = currentPath.getDistance(); // we can use cached or store prev

        if (shouldAcceptNewSolution(oldDistance, newDistance, temperature)) {
            // Accept new path (implicitly done)
        } else {
            currentPath.revertSwap();
        }
    }

    /**
     * Determines whether the new solution should be accepted.
     * Accepts if it is better or with a probability based on temperature if worse.
     *
     * @param oldDistance previous distance
     * @param newDistance newly computed distance
     * @param temperature current temperature
     * @return true if the new solution should be accepted
     */
    private boolean shouldAcceptNewSolution(double oldDistance, double newDistance, double temperature) {
        if (newDistance < oldDistance) {
            return true;
        }
        double probability = Math.exp((oldDistance - newDistance) / temperature);
        return probability > Math.random() * ((SAParameters) parameters).stepRate();
    }

    /**
     * Logs initial algorithm configuration and starting distance.
     */
    private void logStartingInfo() {
        System.out.printf(
                "Starting Simulated Annealing with temperature: %.2f, iterations: %d, cooling rate: %.2f%n",
                ((SAParameters) parameters).startingTemperature(),
                parameters.numberOfIterations(),
                ((SAParameters) parameters).coolingRate()
        );
        System.out.printf("Initial path distance: %.2f%n", currentPath.getDistance());
    }

    /**
     * Logs the final distance after the algorithm finishes.
     *
     * @param bestDistance final optimized path distance
     */
    private void logFinalInfo(double bestDistance) {
        System.out.printf("Final path distance: %.2f%n", bestDistance);
    }
}
