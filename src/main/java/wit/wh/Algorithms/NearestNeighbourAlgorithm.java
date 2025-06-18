package wit.wh.Algorithms;

import wit.wh.TSPSolution;
import wit.wh.pointUtils.Point;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Implementation of the Nearest Neighbour heuristic for solving the Traveling Salesman Problem (TSP).
 * <p>
 * The algorithm builds a route by starting at a given point and repeatedly visiting the nearest unvisited point.
 */
public class NearestNeighbourAlgorithm extends TSPSolution {

    /**
     * Solves the TSP using the Nearest Neighbour algorithm, starting from the given start point.
     *
     * @param points     list of points representing cities or nodes.
     * @param startPoint the starting point for the algorithm.
     * @return an ordered list of points representing the TSP path.
     */
    protected static ArrayList<Point> getTSPSolutionBasedOnStartingPoint(ArrayList<Point> points, Point startPoint) {
        if (points == null || points.isEmpty()) {
            return new ArrayList<>();
        }
        return buildSolution(points, startPoint);
    }

    /**
     * Solves the TSP using the Nearest Neighbour algorithm, starting from the first point in the list.
     *
     * @param points list of points representing cities or nodes.
     * @return an ordered list of points representing the TSP path.
     */
    @Override
    protected ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        if (points == null || points.isEmpty()) {
            return new ArrayList<>();
        }
        return buildSolution(points, points.get(0));
    }

    /**
     * Builds a TSP route using the Nearest Neighbour heuristic.
     *
     * @param points       the full list of available points.
     * @param startPoint   the point from which the algorithm starts.
     * @return an ordered list of points forming the TSP route, ending with a return to the start.
     */
    private static ArrayList<Point> buildSolution(ArrayList<Point> points, Point startPoint) {
        ArrayList<Point> route = new ArrayList<>();
        HashSet<Point> visited = new HashSet<>();

        Point current = startPoint;
        route.add(current);
        visited.add(current);

        while (route.size() < points.size()) {
            Point nearest = findNearestUnvisitedPoint(points, visited, current);
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
     * @param points  the list of all points.
     * @param visited the set of already visited points.
     * @param current the current point.
     * @return the nearest unvisited point, or null if all have been visited.
     */
    private static Point findNearestUnvisitedPoint(ArrayList<Point> points, HashSet<Point> visited, Point current) {
        Point nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Point point : points) {
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