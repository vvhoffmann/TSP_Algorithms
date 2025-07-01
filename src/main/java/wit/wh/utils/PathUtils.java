package wit.wh.utils;

import wit.wh.algorithms.TSPSolution;

import java.util.ArrayList;
import java.util.Random;

/**
 * Utility class providing operations for manipulating paths of {@link Point} objects.
 */
public class PathUtils {

    public final static int MIN_POINTS_TO_RUN_ALGORITHM = 2;
    public final static Random RANDOM = new Random();

    /**
     * Converts a path into a round-trip path by appending the starting point to the end.
     * <p>
     * This is useful in TSP solutions where the path must return to the starting point.
     *
     * @param points the list of points representing the path; must contain at least one point
     * @return the same list of points with the starting point appended at the end
     * @throws IndexOutOfBoundsException if the input list is empty
     */
    public static ArrayList<Point> returnRoundPath(ArrayList<Point> points) {
        points.add(points.get(0));
        return points;
    }

    public static double getRouteLength(ArrayList<Point> points) {
        double distance = 0;
        if(!(points.get(0) == points.get(points.size() - 1))){
            Point starting = points.get(0);
            Point destination = points.get(points.size() - 1);
            distance = PointUtils.distance(starting, destination);
        }

        for (int index = 0; index < points.size()-1; index++) {
            Point starting = points.get(index);
            Point destination = points.get(index + 1);
            distance += PointUtils.distance(starting,destination);
        }

        return distance;
    }

    public static double getRouteLength(TSPSolution solution) {
        return PathUtils.getRouteLength(solution.getSolutionPoints());
    }
}