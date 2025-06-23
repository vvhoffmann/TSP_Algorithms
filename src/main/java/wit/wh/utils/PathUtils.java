package wit.wh.utils;

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
}