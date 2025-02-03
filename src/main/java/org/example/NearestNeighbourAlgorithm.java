package org.example;

import java.util.ArrayList;
import java.util.HashSet;

public class NearestNeighbourAlgorithm {

    // Metoda zwracająca rozwiązanie TSP.
    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        if (points == null || points.isEmpty()) {
            return new ArrayList<>(); // Zwróć pustą listę, jeśli punkty nie zostały zainicjalizowane.
        }
        ArrayList<Point> solution = new ArrayList<>();
        HashSet<Point> visited = new HashSet<>();
        Point current = GrahamAlgorithm.getMinY(points); // Start od pierwszego punktu.
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
