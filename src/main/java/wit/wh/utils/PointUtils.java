package wit.wh.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Utility class providing static helper methods for operations on 2D {@link Point} objects.
 * Includes distance calculations, geometric transformations, and random point generation.
 */
public class PointUtils {

    /**
     * Creates a distance matrix for a list of points.
     *
     * @param points the list of points
     * @return a 2D array where element [i][j] represents the distance between points i and j
     */
    public static double[][] getDistanceArray(ArrayList<Point> points) {
        int n = points.size();
        double[][] dist = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                dist[i][j] = distance(points.get(i), points.get(j));
        return dist;
    }

    /**
     * Returns the integer result of dividing two doubles.
     *
     * @param a dividend
     * @param b divisor
     * @return long integer part of the division result
     */
    public static long doubleDiv(double a, double b) {
        return (long) (a / b);
    }

    /**
     * Computes the modulus (remainder) of dividing two doubles.
     *
     * @param a dividend
     * @param b divisor
     * @return result of a modulo b
     */
    public static double doubleMod(double a, double b) {
        return a - doubleDiv(a, b) * b;
    }

    /**
     * Generates a list of random, non-repeating points within a 25x25 grid.
     *
     * @param size the number of unique points to generate
     * @return a list of random points
     */
    public static ArrayList<Point> generateRandomPoints(int size) {
        ArrayList<Point> points = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; ++i) {
            Point point = new Point(random.nextInt(25), random.nextInt(25));
            if (points.contains(point)) --i;
            else points.add(point);
        }
        System.out.println(" ");
        return points;
    }

    /**
     * Returns a predefined set of 10 points for testing or demonstration purposes.
     *
     * @return a list of predefined points
     */
    public static ArrayList<Point> getReadyPoints() {
        ArrayList<Point> inputPoints = new ArrayList<>();
//        inputPoints.add(new Point(4, 95));
//        inputPoints.add(new Point(23, 77));
//        inputPoints.add(new Point(90, 1));
//        inputPoints.add(new Point(96, 7));
//        inputPoints.add(new Point(76, 11));
        inputPoints.add(new Point(95, 67));
        inputPoints.add(new Point(71, 45));
        inputPoints.add(new Point(75, 20));
        inputPoints.add(new Point(63, 34));
        inputPoints.add(new Point(58, 51));
        inputPoints.add(new Point(50.0, 30.0));
        inputPoints.add(new Point(44.0, 22.0));
        inputPoints.add(new Point(35.0, 40.0));
        inputPoints.add(new Point(20.0, 5.0));
        inputPoints.add(new Point(22.0, 52.0));

        return inputPoints;
    }

    /**
     * Projects point {@code c} onto the line defined by points {@code a} and {@code b}.
     *
     * @param a start point of the line
     * @param b end point of the line
     * @param c point to project
     * @return the projection of point {@code c} on the line AB
     */
    public static Point projection(Point a, Point b, Point c) {
        double BAy = b.y - a.y, BAx = b.x - a.x, CAy = c.y - a.y, CAx = c.x - a.x;
        double BAy2 = BAy * BAy, BAx2 = BAx * BAx, BA2 = BAy2 + BAx2;
        return new Point(
                (a.x * BAy2 + CAy * BAx * BAy + c.x * BAx2) / BA2,
                (a.y * BAx2 + CAx * BAy * BAx + c.y * BAy2) / BA2
        );
    }

    /**
     * Compares two points based on their y-coordinates, and x-coordinates if y is equal.
     *
     * @param a first point
     * @param b second point
     * @return true if {@code a} is lexicographically less than {@code b}
     */
    public static boolean less(Point a, Point b) {
        return ((a.y < b.y) || ((a.y == b.y) && (a.x < b.x)));
    }

    /**
     * Compares two points and returns -1, 0, or 1.
     *
     * @param a first point
     * @param b second point
     * @return -1 if a &lt; b, 1 if a &gt; b, 0 if equal
     */
    public static int min(Point a, Point b) {
        if (less(a, b)) return -1;
        if (less(b, a)) return +1;
        return 0;
    }

    /**
     * Calculates the Euclidean distance between two points.
     *
     * @param a first point
     * @param b second point
     * @return the Euclidean distance
     */
    public static double distance(Point a, Point b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }
}
