package wit.wh.algorithms.QuasiOptimizationAlgorithm;

import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Implementation of the Graham scan algorithm to compute the convex hull
 * of a set of 2D points in O(n log n) time.
 */
public class GrahamAlgorithm {

    /**
     * Computes the convex hull from a list of 2D points using the Graham scan algorithm.
     *
     * @param inputPoints The input list of points.
     * @return An {@link ArrayList} representing the convex hull in counter-clockwise order,
     *         where the last point connects back to the first.
     * @throws IllegalArgumentException if the point list is empty.
     */
    public static ArrayList<Point> getConvexHull(ArrayList<Point> inputPoints) {
        if(inputPoints.size() < 3)
            return inputPoints;

        ArrayList<Point> points = new ArrayList<Point>(inputPoints);

        //wybieram pierwszy wierzchołek, posiadający jak najmniejszą współrzędną y i x
        Point pivot = getLowestPoint(points);
        //sortuje wg kata względem pierwszego wierzchołka i osi OX rosnąco
        sortByAngle(points, pivot);

        Stack<Point> stack = buildConvexHull(points);

        return finalizeConvexHull(stack);
    }

    public static ArrayList<Point> getRoundConvexHull(ArrayList<Point> inputPoints)
    {
        ArrayList<Point> result = getConvexHull(inputPoints);
        return PathUtils.returnRoundPath(result);
    }

    /**
     * Finds the point with the lowest Y coordinate (and lowest X in case of tie).
     *
     * @param points The list of input points.
     * @return The point with the minimum Y value.
     * @throws IllegalArgumentException if the list is empty.
     */
    public static Point getLowestPoint(ArrayList<Point> points)
    {
        return points.stream()
                .min(PointUtils::min)
                .orElseThrow(() -> new IllegalArgumentException("Point list cannot be empty"));
    }

    /**
     * Sorts the points in-place by their polar angle with respect to a pivot point.
     * Points that are collinear with the pivot are sorted by distance to the pivot.
     *
     * @param points The list of points to sort.
     * @param pivot  The reference point for angle comparison.
     */
    private static void sortByAngle(ArrayList<Point> points, Point pivot) {
        points.sort((p1, p2) -> {
            //punkt startowy musi być na początku, więc szukamy go
            if (p1 == pivot) return -1;
            if (p2 == pivot) return 1;

            int newPointDirection = countPointDirection(pivot, p1, p2);

            if (newPointDirection == 0) { //Dla punktów współliniowych
                double dist1 = PointUtils.distance(pivot, p1);
                double dist2 = PointUtils.distance(pivot, p2);
                return Double.compare(dist1, dist2);
            }

            return -newPointDirection;
        });
    }

    /**
     * Constructs the convex hull using a stack-based approach.
     *
     * @param sortedPoints The list of points sorted by polar angle.
     * @return A stack containing the convex hull vertices in order.
     */
    private static Stack<Point> buildConvexHull(ArrayList<Point> sortedPoints) {
        Stack<Point> stack = new Stack<Point>();
        stack.push(sortedPoints.get(0)); // na stos wkładamy pierwszy punkt
        stack.push(sortedPoints.get(1)); // na stos wkładamy punkt, który będziemy sprawdzać

        for (int i = 2, size = sortedPoints.size(); i < size; i++) {
            Point next = sortedPoints.get(i);
            Point top = stack.pop();

            // usuwamy punkt, ponieważ idzie zgodnie z kierunkiem zegara
            while (!stack.isEmpty() && countPointDirection(top, next, stack.peek()) < 0)
                top = stack.pop();

            stack.push(top);
            stack.push(next);
        }
        return stack;
    }

    /**
     * Converts the stack of hull points to a closed, reversed list.
     *
     * @param stack The stack of points forming the convex hull.
     * @return An {@link ArrayList} representing the convex hull in proper order.
     */
    private static ArrayList<Point> finalizeConvexHull(Stack<Point> stack) {
        ArrayList<Point> result = new ArrayList<>(stack);
        result.add(result.get(0));
        result.remove(result.get(0));
        Collections.reverse(result);

        return result;
    }

    /**
     * Returns positive for counter-clockwise turn,
     * negative for clockwise, 0 if collinear.
     */
    public static int countPointDirection(Point a, Point b, Point r)
    {
        return (int)Math.signum((b.y - a.y) * (r.x - b.x) - (b.x - a.x) * (r.y - b.y));
    }

}