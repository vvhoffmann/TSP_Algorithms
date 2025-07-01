package wit.wh.algorithms.QuasiOptimizationAlgorithm;

import wit.wh.algorithms.TSPSolution;
import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.util.ArrayList;

/**
 * A heuristic TSP algorithm that builds the solution based on the convex hull and incrementally
 * inserts remaining inputPoints in the most optimal location within the existing path.
 */
public class QuasiOptimizationSolution extends TSPSolution {
    private ArrayList<Point> convexHull;
    private ArrayList<Point> resultPath;

    public QuasiOptimizationSolution(ArrayList<Point> inputPoints) {
        super(inputPoints);
    }

    /**
     * Solves the TSP using a convex hull based insertion heuristic.
     *
     * @return an ordered list of inputPoints representing the TSP path.
     */
    @Override
    protected ArrayList<Point> getTSPSolution() {
        if (inputPoints == null)
            return new ArrayList<>();

        if(inputPoints.size() < PathUtils.MIN_POINTS_TO_RUN_ALGORITHM)
            return inputPoints;

        resultPath = new ArrayList<>();

        buildConvexHull(inputPoints);
        insertNotAssignedPointsIntoPath(getNotAssignedPointsList());

        return PathUtils.returnRoundPath(resultPath);
    }

    /**
     * Computes the convex hull from the input inputPoints and initializes the result path.
     *
     * @param inputPoints the input point list.
     */
    private void buildConvexHull(ArrayList<Point> inputPoints) {
        this.convexHull = GrahamAlgorithm.getConvexHull(inputPoints);
        resultPath.addAll(convexHull);
    }

    /**
     * Identifies inputPoints that are not part of the convex hull.
     *
     * @return a list of inputPoints not included in the convex hull.
     */
    private ArrayList<Point> getNotAssignedPointsList() {
        ArrayList<Point> notAssignedPoints = new ArrayList<>(inputPoints);
        notAssignedPoints.removeAll(convexHull);

        return notAssignedPoints;
    }

    /**
     * Inserts each non-hull point into the path at the most optimal position.
     *
     * @param inputPointsToInsert list of inputPoints to be inserted.
     */
    private void insertNotAssignedPointsIntoPath(ArrayList<Point> inputPointsToInsert) {
        while (!inputPointsToInsert.isEmpty()) {
            int selectedPointIndex = 0;
            int bestInsertIndex = Integer.MIN_VALUE;
            double minimalDistance = Double.MAX_VALUE;

            for (int p = 0; p < inputPointsToInsert.size(); ++p) {
                Point pointToInsert = inputPointsToInsert.get(p);
                InsertionResult insertionResult = findBestInsertionForPoint(pointToInsert);

                if (minimalDistance > insertionResult.distance) {
                    minimalDistance = insertionResult.distance;
                    bestInsertIndex = insertionResult.insertIndex;
                    selectedPointIndex = p;
                }
            }
            Point selectedPoint = inputPointsToInsert.remove(selectedPointIndex);
            addTargetPointIntoResult(bestInsertIndex, selectedPoint);
        }
    }

    /**
     * Finds the best index in the current path to insert the given point based on projection distance.
     *
     * @param pointToInsert the point to evaluate.
     * @return an {@link InsertionResult} containing the best index and associated distance.
     */
    private InsertionResult findBestInsertionForPoint(Point pointToInsert) {
        double bestDistance = Double.MAX_VALUE;
        int bestInsertIndex = -1;

        for (int p = 0; p < resultPath.size(); ++p) {
            //result point future location : segmentStart - pointToInsert - segmentEnd
            int previousIndex = (p == 0) ? resultPath.size() - 1 : p - 1;
            Point segmentStart = resultPath.get(previousIndex);
            Point segmentEnd = resultPath.get(p);

            Point projectionPoint = PointUtils.projection(segmentStart, segmentEnd, pointToInsert);
            double segmentLength = PointUtils.distance(segmentStart, segmentEnd);
            double aToProjectionLength = PointUtils.distance(segmentStart, projectionPoint);
            double bToProjectionLength = PointUtils.distance(segmentEnd, projectionPoint);
            double pointToProjectionLength = PointUtils.distance(projectionPoint, pointToInsert);

            int insertionIndex = p;

            if (aToProjectionLength + bToProjectionLength > segmentLength) {
                insertionIndex = findBetterProjectionAlternative(pointToInsert, segmentStart, segmentEnd, pointToProjectionLength, p, previousIndex);
                pointToProjectionLength = estimateOffSegmentPenalty(pointToInsert, segmentStart, segmentEnd);
            }

            if(pointToProjectionLength < bestDistance) {
                bestDistance = pointToProjectionLength;
                bestInsertIndex = insertionIndex;
            }
        }
        return new InsertionResult(bestInsertIndex, bestDistance);
    }


    /**
     * Chooses a better insertion index when the projection does not fall directly on the segment.
     *
     * @param pointToInsert the point being evaluated.
     * @param segmentStart the start of the current segment.
     * @param segmentEnd the end of the current segment.
     * @param pointToProjetionLength distance to original projection.
     * @param currentIndex current index in the loop.
     * @param previousIndex previous index in the path.
     * @return an improved insertion index.
     */
    private int findBetterProjectionAlternative(Point pointToInsert, Point segmentStart, Point segmentEnd, double pointToProjetionLength, int currentIndex, int previousIndex) {
        double distanceFromPointToStart = PointUtils.distance(segmentStart, pointToInsert);
        double distanceFromPointToEnd = PointUtils.distance(segmentEnd, pointToInsert);

        if (distanceFromPointToStart < distanceFromPointToEnd) {
            int candidateIndex = (previousIndex == 0) ? resultPath.size() - 1 : previousIndex - 1;
            Point candidateStart = resultPath.get(candidateIndex);
            Point candidateProjectionPoint = PointUtils.projection(candidateStart, segmentStart, pointToInsert);
            if (PointUtils.distance(pointToInsert, candidateProjectionPoint) > pointToProjetionLength)
                return candidateIndex;
        } else {
            int candidateIndex = (currentIndex == resultPath.size() - 1) ? 0 : currentIndex + 1;
            Point candidateEnd = resultPath.get(candidateIndex);
            Point candidateProjectionPoint = PointUtils.projection(segmentEnd, candidateEnd, pointToInsert);
            if (PointUtils.distance(pointToInsert, candidateProjectionPoint) > pointToProjetionLength)
                return candidateIndex;
        }

        return currentIndex;
    }

    /**
     * Estimates a penalty distance when a projection falls outside the segment.
     *
     * @param pointToInsert the point to insert.
     * @param segmentStart segment start.
     * @param segmentEnd segment end.
     * @return estimated penalty distance.
     */
    private double estimateOffSegmentPenalty(Point pointToInsert, Point segmentStart, Point segmentEnd) {
        return Math.min(2 * PointUtils.distance(segmentStart, pointToInsert), 2 * PointUtils.distance(segmentEnd, pointToInsert));
    }

    /**
     * Inserts point into the resultPath
     *
     * @param index index at which point need to be inserted
     * @param point point to insert into resultPath
     */
    private void addTargetPointIntoResult(int index, Point point) {
        resultPath.add(index, point);
    }

    /**
     * Represents the result of finding an insertion point, containing index and projected distance.
     */
    private static class InsertionResult {
        int insertIndex;
        double distance;

        InsertionResult(int insertIndex, double distance) {
            this.insertIndex = insertIndex;
            this.distance = distance;
        }
    }
}