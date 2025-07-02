package wit.wh.algorithms;

import wit.wh.algorithms.ACOSolution.ACOSolution;
import wit.wh.algorithms.ACOSolution.ACOParameters;
import wit.wh.algorithms.HeldKarpSolution.HeldKarpSolution;
import wit.wh.algorithms.NearestNeighbourSolution.NearestNeighbourSolution;
import wit.wh.algorithms.QuasiOptimizationSolution.QuasiOptimizationSolution;
import wit.wh.algorithms.NearestNeighbourSolution.RepetitiveNearestNeighbourSolution;
import wit.wh.algorithms.SASolution.SASolution;
import wit.wh.algorithms.SASolution.SAParameters;
import wit.wh.utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating TSP (Traveling Salesman Problem) solution instances
 * based on different algorithm types.
 */
public class TSPSolutionFactory {

    /**
     * Creates a TSP solution using the specified algorithm type with default parameters.
     *
     * @param type         the type of TSP solving algorithm to use
     * @param inputPoints  the list of points to solve TSP for
     * @return             an instance of {@link TSPSolution} using the selected algorithm
     * @throws IllegalArgumentException if the provided type is not supported
     */
    public static TSPSolution createSolution(SolutionType type, ArrayList<Point> inputPoints) {
        return switch (type) {
            case HELD_KARP_ALGORITHM ->
                    new HeldKarpSolution(inputPoints);
            case NEAREST_NEIGHBOUR_ALGORITHM ->
                    new NearestNeighbourSolution(inputPoints);
            case REPETITIVE_NEAREST_NEIGHBOUR_ALGORITHM ->
                    new RepetitiveNearestNeighbourSolution(inputPoints);
            case SA_ALGORITHM ->
                    new SASolution(inputPoints, new SAParameters());
            case ACO_ALGORITHM ->
                    new ACOSolution(inputPoints, new ACOParameters());
            case QUASI_OPTIMIZATION_ALGORITHM ->
                    new QuasiOptimizationSolution(inputPoints);
        };
    }

    /**
     * Creates a TSP solution using the specified algorithm type and custom parameters.
     *
     * @param type         the type of TSP solving algorithm to use
     * @param inputPoints  the list of points to solve TSP for
     * @param parameters   custom algorithm parameters
     * @return             an instance of {@link TSPSolution} using the selected algorithm and parameters
     * @throws IllegalArgumentException if the provided type is not supported for parameterized creation
     */
    public static TSPSolution createSolutionWithParams(SolutionType type, ArrayList<Point> inputPoints, AlgoritmParameters parameters) {
        return switch (type) {
            case SA_ALGORITHM ->
                    new SASolution(inputPoints, (SAParameters) parameters);
            case ACO_ALGORITHM ->
                    new ACOSolution(inputPoints, (ACOParameters) parameters);
            default ->
                    throw new IllegalArgumentException("Unrecognized solution type");
        };
    }

    /**
     * Generates TSP solutions using all available algorithms and returns them as a map.
     *
     * @param inputPoints  the list of points to solve TSP for
     * @return             a map containing the algorithm type as the key and the corresponding solution as the value
     */
    public static Map<SolutionType, ArrayList<Point>> getAllSolutions(ArrayList<Point> inputPoints) {
        Map<SolutionType, ArrayList<Point>> solutions = new HashMap<>();
        for (SolutionType type : SolutionType.values()) {
            solutions.put(type, createSolution(type, inputPoints).getSolutionPoints());
        }
        return solutions;
    }
}
