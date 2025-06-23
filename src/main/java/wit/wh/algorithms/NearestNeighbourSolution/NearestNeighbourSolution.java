package wit.wh.algorithms.NearestNeighbourSolution;

import wit.wh.algorithms.TSPSolution;
import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Implementation of the Nearest Neighbour heuristic for solving the Traveling Salesman Problem (TSP).
 * <p>
 * The algorithm builds a route by starting at a given point and repeatedly visiting the nearest unvisited point.
 */
public class NearestNeighbourSolution extends TSPSolution {

    public NearestNeighbourSolution(ArrayList<Point> inputPoints) {
        super(inputPoints);
    }

    /**
     * Solves the TSP using the Nearest Neighbour algorithm, starting from the given start point.
     *
     * @param inputPoints     list of input points
     * @param startPoint the starting point for the algorithm.
     * @return an ordered list of inputPoints representing the TSP path.
     */
    protected static ArrayList<Point> getTSPSolutionBasedOnStartingPoint(ArrayList<Point> inputPoints, Point startPoint) {
        if (inputPoints == null || inputPoints.isEmpty()) {
            return new ArrayList<>();
        }
        return buildSolution(inputPoints, startPoint);
    }

    /**
     * Solves the TSP using the Nearest Neighbour algorithm, starting from the first point in the list.
     *
     * @return an ordered list of inputPoints representing the TSP path.
     */
    @Override
    protected ArrayList<Point> getTSPSolution() {
        if (inputPoints == null || inputPoints.isEmpty()) {
            return new ArrayList<>();
        }
        if(inputPoints.size() < PathUtils.MIN_POINTS_TO_RUN_ALGORITHM)
            return inputPoints;

        return buildSolution(inputPoints, inputPoints.get(0));
    }

    /**
     * Builds a TSP route using the Nearest Neighbour heuristic.
     *
     * @param inputPoints       the full list of available inputPoints.
     * @param startPoint   the point from which the algorithm starts.
     * @return an ordered list of inputPoints forming the TSP route, ending with a return to the start.
     */
    private static ArrayList<Point> buildSolution(ArrayList<Point> inputPoints, Point startPoint) {
        ArrayList<Point> route = new ArrayList<>();
        HashSet<Point> visited = new HashSet<>();

        Point current = startPoint;
        route.add(current);
        visited.add(current);

        while (route.size() < inputPoints.size()) {
            Point nearest = findNearestUnvisitedPoint(inputPoints, visited, current);
            if (nearest != null) {
                route.add(nearest);
                visited.add(nearest);
                current = nearest;
            }
        }

        closeRoute(route);
        return route;
    }

    /**
     * Finds the closest unvisited point to the current point.
     *
     * @param inputPoints  the list of all inputPoints.
     * @param visited the set of already visited inputPoints.
     * @param current the current point.
     * @return the nearest unvisited point, or null if all have been visited.
     */
    private static Point findNearestUnvisitedPoint(ArrayList<Point> inputPoints, HashSet<Point> visited, Point current) {
        Point nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Point point : inputPoints) {
            if (!visited.contains(point)) {
                double distance = current.distance(point);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = point;
                }
            }
        }

        return nearest;
    }

    /**
     * Closes the route by returning to the starting point.
     *
     * @param route the current TSP route.
     */
    private static void closeRoute(ArrayList<Point> route) {
        if (!route.isEmpty()) {
            route.add(route.get(0));
        }
    }
}