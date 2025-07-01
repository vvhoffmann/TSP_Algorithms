package wit.wh.algorithms.SAAlgorithm;

import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;

import java.util.ArrayList;

/**
 * Represents a path consisting of a list of points.
 * Provides functionality to randomly swap points and calculate the total distance of the path.
 */
public class Path {

    private ArrayList<Point> points;
    private ArrayList<Point> previousPoints;

    /**
     * Constructs a new Path with the given list of points.
     *
     * @param points the list of points forming the path
     * @throws NullPointerException if the points list is null
     */
    public Path(ArrayList<Point> points) {
        this.points = new ArrayList<>(points);
        this.previousPoints = new ArrayList<>(points);
    }

    public Path(Path otherPath) {
        this.points = new ArrayList<>(otherPath.getPoints());
        this.previousPoints = new ArrayList<>(otherPath.getPoints());
    }

    /**
     * Randomly swaps two points in the path.
     * The previous state is saved and can be restored using {@link #revertSwap()}.
     */
    public void swapRandomPoints() {
        int indexA = generateRandomIndex();
        int indexB = generateRandomIndex();
        Point a = points.get(indexA);
        Point b = points.get(indexB);
        points.set(indexA, b);
        points.set(indexB, a);
    }

    /**
     * Reverts the last swap operation, restoring the path to its previous state.
     */
    public void revertSwap() {
        points = previousPoints;
    }

    /**
     * Generates a random index within the bounds of the points list.
     *
     * @return a random index between 0 (inclusive) and points.size() (exclusive)
     */
    private int generateRandomIndex() {
        return (int) (Math.random() * points.size());
    }

    /**
     * Returns the point at the specified index in the path.
     *
     * @param index the index of the desired point
     * @return the point at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Point getPoint(int index) {
        return points.get(index);
    }

    /**
     * Calculates the total distance of the path.
     * The path is treated as circular, so the last point connects back to the first.
     *
     * @return the total distance of the path
     */
    public double getDistance() {
        return PathUtils.getRouteLength(points);
    }

    /**
     * Returns the list of points forming the path.
     *
     * @return the list of points
     */
    public ArrayList<Point> getPoints() {
        return points;
    }
}