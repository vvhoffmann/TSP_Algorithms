package wit.wh.algorithms.NearestNeighbourSolution;

import wit.wh.algorithms.TSPSolution;
import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.util.ArrayList;
/**
 * Implements the Repetitive Nearest Neighbour heuristic for solving the TSP.
 * <p>
 * This algorithm executes the Nearest Neighbour Algorithm for each possible starting point
 * and selects the shortest resulting route.
 */
public class RepetitiveNearestNeighbourSolution extends TSPSolution {

    public RepetitiveNearestNeighbourSolution(ArrayList<Point> inputPoints) {
        super(inputPoints);
    }

    /**
     * Generates the best TSP route using the Repetitive Nearest Neighbour heuristic.
     * <p>
     * For each point in the input list, the Nearest Neighbour Algorithm is applied
     * starting from that point. The shortest route among all these attempts is returned.
     *
     * @return the shortest TSP route found among all Nearest Neighbour runs.
     */
    @Override
    protected ArrayList<Point> getTSPSolution() {
        if (inputPoints == null)
            return new ArrayList<>();

        if(inputPoints.size() < PathUtils.MIN_POINTS_TO_RUN_ALGORITHM)
            return inputPoints;

        double minDistance = Double.MAX_VALUE;

        ArrayList<Point> bestSolution = new ArrayList<>();
        for (Point p : inputPoints) {
            ArrayList<Point> solution = NearestNeighbourSolution.getTSPSolutionBasedOnStartingPoint(inputPoints,p);
            if(getRouteLength(solution) < minDistance)
            {
                minDistance = getRouteLength(solution);
                bestSolution = solution;
            }
        }
        return bestSolution;
    }

    /**
     * Calculates the total length of the TSP route by summing the distances
     * between consecutive inputPoints, including the return to the starting point.
     *
     * @param inputPoints the ordered list of inputPoints forming a route.
     * @return the total length (distance) of the route.
     */
    private double getRouteLength(ArrayList<Point> inputPoints) {
        double route = 0;
        for (int i = 0; i < inputPoints.size(); i++) {
            if (i == inputPoints.size() - 1)  route += PointUtils.distance(inputPoints.get(i), inputPoints.get(0));
            else route += PointUtils.distance(inputPoints.get(i), inputPoints.get(i+1));
        }
        return route;
    }
}
