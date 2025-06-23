package wit.wh.algorithms;

import wit.wh.algorithms.AntColonyQuasiOptimizationAlgorithm.AntColonyQuasiOptimizationSolution;
import wit.wh.algorithms.AntColonyQuasiOptimizationAlgorithm.AntColonyParameters;
import wit.wh.algorithms.HeldKarpAlgorithm.HeldKarpSolution;
import wit.wh.algorithms.NearestNeighbourSolution.NearestNeighbourSolution;
import wit.wh.algorithms.QuasiOptimizationAlgorithm.QuasiOptimizationSolution;
import wit.wh.algorithms.NearestNeighbourSolution.RepetitiveNearestNeighbourSolution;
import wit.wh.algorithms.SAAlgorithm.SASolution;
import wit.wh.algorithms.SAAlgorithm.SAParameters;
import wit.wh.utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TSPSolutionFactory {
    public static TSPSolution createSolution(SolutionType type, ArrayList<Point> inputPoints) {
        TSPSolution solution = switch (type) {
            case HELD_KARP_ALGORITHM ->
                    new HeldKarpSolution(inputPoints);
            case NEAREST_NEIGHBOUR_ALGORITHM ->
                    new NearestNeighbourSolution(inputPoints);
            case REPETITIVE_NEAREST_NEIGHBOUR_ALGORITHM ->
                    new RepetitiveNearestNeighbourSolution(inputPoints);
            case SA_ALGORITHM ->
                    new SASolution(inputPoints, new SAParameters() );
            case ANT_COLONY_OPTIMIZATION_ALGORITHM ->
                    new AntColonyQuasiOptimizationSolution(inputPoints, new AntColonyParameters());
            case QUASI_OPTIMIZATION_ALGORITHM ->
                    new QuasiOptimizationSolution(inputPoints);
            default ->
                    throw new IllegalArgumentException("Unrecognized solution type");
        };
        return solution;
    };

    public static TSPSolution createSolutionWithParams(SolutionType type, ArrayList<Point> inputPoints, AlgoritmParameters parameters) {
        TSPSolution solution = switch (type) {
            case SA_ALGORITHM ->
                    new SASolution(inputPoints, (SAParameters) parameters);
            case ANT_COLONY_OPTIMIZATION_ALGORITHM ->
                    new AntColonyQuasiOptimizationSolution(inputPoints, (AntColonyParameters) parameters);
            default ->
                    throw new IllegalArgumentException("Unrecognized solution type");
        };
        return solution;
    };

    public static Map< SolutionType, ArrayList<Point>> getAllSolutions(ArrayList<Point> inputPoints) {
        Map<SolutionType, ArrayList<Point>> solutions = new HashMap<>();
        for(SolutionType type : SolutionType.values())
            solutions.put(type, createSolution(type, inputPoints).getSolutionPoints());
        return solutions;
    }
}

