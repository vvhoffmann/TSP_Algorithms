package org.example.Algorithms;

import org.example.Point;

import java.util.ArrayList;
import java.util.HashSet;

import static org.example.Algorithms.QuasiOptimalAlgorithm.GrahamAlgorithm.getMinY;

public class NearestNeighbourAlgorithm {

    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points, Point startPoint) {
        if (points == null || points.isEmpty())
            return new ArrayList<>(); // Zwróć pustą listę, jeśli punkty nie zostały zainicjalizowane.

        return getSolutionPoints(points, startPoint);
    }

    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        if (points == null || points.isEmpty())
            return new ArrayList<>(); // Zwróć pustą listę, jeśli punkty nie zostały zainicjalizowane.

        return getSolutionPoints(points, points.get(0));
    }

    private static ArrayList<Point> getSolutionPoints(ArrayList<Point> points, Point current) {
        ArrayList<Point> solution = new ArrayList<>();
        HashSet<Point> visited = new HashSet<>();
        solution.add(current);
        visited.add(current);

        while (solution.size() < points.size()) {
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

            if (nearest != null) {
                solution.add(nearest);
                visited.add(nearest);
                current = nearest;
            }
        }

        // Powrót do punktu początkowego, aby zamknąć trasę.
        solution.add(solution.get(0));

        System.out.println(solution);
        return solution;
    }

}
