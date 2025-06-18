package wit.wh;

import wit.wh.Algorithms.AntColony.AntColonyQuasiOptimizationAlgorithm;
import wit.wh.Algorithms.AntColony.AntsParameters;
import wit.wh.Algorithms.HeldKarpAlgorithm;
import wit.wh.Algorithms.NearestNeighbourAlgorithm;
import wit.wh.Algorithms.QuasiOptimizationAlgorithm.QuasiOptimizationAlgorithm;
import wit.wh.Algorithms.RepetitiveNearestNeighbourAlgorithm;
import wit.wh.Algorithms.SAAlgorithm.SAAlgorithm;
import wit.wh.Algorithms.SAAlgorithm.SAParameters;
import wit.wh.pointUtils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TSPSolutionFactory {
    public static ArrayList<Point> createSolution(SolutionType type, ArrayList<Point> points) {
        TSPSolution solution = switch (type) {
            case HELD_KARP_ALGORITHM ->
                    new HeldKarpAlgorithm();
            case NEAREST_NEIGHBOUR_ALGORITHM ->
                    new NearestNeighbourAlgorithm();
            case REPETITIVE_NEAREST_NEIGHBOUR_ALGORITHM ->
                    new RepetitiveNearestNeighbourAlgorithm();
            case SA_ALGORITHM ->
                    new SAAlgorithm( new SAParameters() );
            case ANT_COLONY_OPTIMIZATION_ALGORITHM ->
                    new AntColonyQuasiOptimizationAlgorithm( new AntsParameters() );
            case QUASI_OPTIMIZATION_ALGORITHM ->
                    new QuasiOptimizationAlgorithm();
            default ->
                    throw new IllegalArgumentException("Unrecognized solution type");
        };

        return solution.getSolutionPoints(points);
    };

    public static Map< SolutionType, ArrayList<Point>> getAllSolutions(ArrayList<Point> points) {
        Map<SolutionType, ArrayList<Point>> solutions = new HashMap<>();
        for(SolutionType type : SolutionType.values())
            solutions.put(type, createSolution(type, points));
        return solutions;
    }
}

