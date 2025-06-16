package org.example.Algorithms.QuasiOptimizationAlgorithm;

import org.example.TSPSolution;
import org.example.pointUtils.Point;
import org.example.pointUtils.PointUtils;

import java.util.ArrayList;


public class QuasiOptimizationAlgorithm extends TSPSolution {
    private ArrayList<Point> points;
    private ArrayList<Point> convexHull;
    private ArrayList<Point> resultPoints = new ArrayList<>();

    @Override
    public ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        this.points = points;
        calculateConvexHull(points);
        resultPoints = insertNotAssignedPointsIntoPath(getNotAssignedPointsList());

        return resultPoints;
    }

    private void calculateConvexHull(ArrayList<Point> points) {
        this.convexHull = GrahamAlgorithm.getConvexHull(points);
        resultPoints.addAll(convexHull);
    }

    private ArrayList<Point> getNotAssignedPointsList() {
        ArrayList<Point> notAssignedPoints = new ArrayList<>(points);
        notAssignedPoints.removeAll(convexHull);

        return notAssignedPoints;
    }

    //zwraca numer indexu w tablicy pod który należy dodać punkt
    //np. Jeśli chcemy wstawić punkt P, pomiedzy C i D, zwrócony zostanie index punktu D
    private ArrayList<Point> insertNotAssignedPointsIntoPath(ArrayList<Point> insidePoints) {
        while (!insidePoints.isEmpty()) {
            int selectedPointIndex = 0;
            int bestInsertIndex = Integer.MIN_VALUE;
            double minimalDistance = Double.MAX_VALUE;

            for (int p = 0; p < insidePoints.size(); ++p) {
                Point pointToInsert = insidePoints.get(p);
                InsertionResult insertionResult = findBestInsertionForPoint(pointToInsert);

                if (minimalDistance > insertionResult.distance) {
                    minimalDistance = insertionResult.distance;
                    bestInsertIndex = insertionResult.insertIndex;
                    selectedPointIndex = p;
                }
            }
            Point selectedPoint = insidePoints.remove(selectedPointIndex);
            addTargetPointIntoResult(bestInsertIndex, selectedPoint);
        }
        return resultPoints;
    }

    private InsertionResult findBestInsertionForPoint(Point pointToInsert) {
        double bestDistance = Double.MAX_VALUE;
        int bestInsertIndex = -1;

        for (int p = 0; p < resultPoints.size(); ++p) {
            //result point future location : segmentStart - pointToInsert - segmentEnd
            int previousIndex = (p == 0) ? resultPoints.size() - 1 : p - 1;
            Point segmentStart = resultPoints.get(previousIndex);
            Point segmentEnd = resultPoints.get(p);

            Point projectionPoint = PointUtils.projection(segmentStart, segmentEnd, pointToInsert);
            double segmentLength = PointUtils.distance(segmentStart, segmentEnd);
            double aToProjectionLength = PointUtils.distance(segmentStart, projectionPoint);
            double bToProjectionLength = PointUtils.distance(segmentEnd, projectionPoint);
            double pointToProjectionLength = PointUtils.distance(projectionPoint, pointToInsert);

            int insertionIndex = p;

            //if (segmentLength * segmentLength <= AP * AP + BP * BP) pointToProjectionLength = (AP + BP) / 2;
            //punkt, którego projekcja nie przypada na odcinku segmentLength
            if (aToProjectionLength + bToProjectionLength > segmentLength) {
                // Krótsze ramie może być takie samo dla 2 sąsiednich odcinków, więc należy sprawdzić, który z nich będzie lepszy dla punktu
                // IF index od segmentStart -1
                // ELSE index od segmentEnd +1
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

    private double estimateOffSegmentPenalty(Point pointToInsert, Point segmentStart, Point segmentEnd) {
        return Math.min(2 * PointUtils.distance(segmentStart, pointToInsert), 2 * PointUtils.distance(segmentEnd, pointToInsert));
    }

    private int findBetterProjectionAlternative(Point pointToInsert, Point segmentStart, Point segmentEnd, double pointToProjetionLength, int currentIndex, int previousIndex) {
        double distanceFromPointToStart = PointUtils.distance(segmentStart, pointToInsert);
        double distanceFromPointToEnd = PointUtils.distance(segmentEnd, pointToInsert);

        if (distanceFromPointToStart < distanceFromPointToEnd) {
            int candidateIndex = (previousIndex == 0) ? resultPoints.size() - 1 : previousIndex - 1;
            Point candidateStart = resultPoints.get(candidateIndex);
            Point projectionPoint = PointUtils.projection(candidateStart, segmentStart, pointToInsert);
            if (PointUtils.distance(pointToInsert, projectionPoint) > pointToProjetionLength)
                return candidateIndex;
        } else {
            int candidateIndex = (currentIndex == resultPoints.size() - 1) ? 0 : currentIndex + 1;
            Point candidateEnd = resultPoints.get(candidateIndex);
            Point projectionPoint = PointUtils.projection(segmentEnd, candidateEnd, pointToInsert);
            if (PointUtils.distance(pointToInsert, projectionPoint) > pointToProjetionLength)
                return candidateIndex;
        }

        return currentIndex;
    }

    private void addTargetPointIntoResult(int index, Point point) {
        resultPoints.add(index, point);
    }

    // Helper class for returning both index and distance
    private static class InsertionResult {
        int insertIndex;
        double distance;

        InsertionResult(int insertIndex, double distance) {
            this.insertIndex = insertIndex;
            this.distance = distance;
        }
    }
}