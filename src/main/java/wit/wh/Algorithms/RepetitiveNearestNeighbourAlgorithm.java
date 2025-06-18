package wit.wh.Algorithms;

import wit.wh.TSPSolution;
import wit.wh.pointUtils.Point;
import wit.wh.pointUtils.PointUtils;

import java.util.ArrayList;
/**
 * Implements the Repetitive Nearest Neighbour heuristic for solving the TSP.
 * <p>
 * This algorithm executes the Nearest Neighbour Algorithm for each possible starting point
 * and selects the shortest resulting route.
 */
public class RepetitiveNearestNeighbourAlgorithm extends TSPSolution {

    /**
     * Generates the best TSP route using the Repetitive Nearest Neighbour heuristic.
     * <p>
     * For each point in the input list, the Nearest Neighbour Algorithm is applied
     * starting from that point. The shortest route among all these attempts is returned.
     *
     * @param points the list of input points.
     * @return the shortest TSP route found among all Nearest Neighbour runs.
     */
    @Override
    protected ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        double minDistance = Double.MAX_VALUE;

        ArrayList<Point> bestSolution = new ArrayList<>();
        for (Point p : points) {
            ArrayList<Point> solution = NearestNeighbourAlgorithm.getTSPSolutionBasedOnStartingPoint(points,p);
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
     * between consecutive points, including the return to the starting point.
     *
     * @param points the ordered list of points forming a route.
     * @return the total length (distance) of the route.
     */
    private double getRouteLength(ArrayList<Point> points) {
        double route = 0;
        for (int i = 0; i < points.size(); i++) {
            if (i == points.size() - 1)  route += PointUtils.distance(points.get(i), points.get(0));
            else route += PointUtils.distance(points.get(i), points.get(i+1));
        }
        return route;
    }
}
