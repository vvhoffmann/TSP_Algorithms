package org.example.Algorithms;

import org.example.Point;

import java.util.ArrayList;
import java.util.HashSet;

import static org.example.Algorithms.QuasiOptimalAlgorithm.GrahamAlgorithm.getMinY;

public class NearestNeighbourAlgorithm {

    // Metoda zwracająca rozwiązanie TSP.
    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points, Point startPoint) {
        if (points == null || points.isEmpty()) {
            return new ArrayList<>(); // Zwróć pustą listę, jeśli punkty nie zostały zainicjalizowane.
        }
        ArrayList<Point> solution = new ArrayList<>();
        HashSet<Point> visited = new HashSet<>();
        Point current = startPoint;//.getMinY(points); // Start od pierwszego punktu.
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

    // Metoda zwracająca rozwiązanie TSP.
    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        if (points == null || points.isEmpty()) {
            return new ArrayList<>(); // Zwróć pustą listę, jeśli punkty nie zostały zainicjalizowane.
        }
        ArrayList<Point> solution = new ArrayList<>();
        HashSet<Point> visited = new HashSet<>();
        Point current = getMinY(points); // Start od pierwszego punktu.
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

    public static ArrayList<Point> getTSPSolutionKK(ArrayList<Point> points) {
        double minDistance = Double.MAX_VALUE;
        ArrayList<Point> bestSolution = new ArrayList<>();
        for (Point p : points) {
            ArrayList<Point> solution = getTSPSolution(points,p);
            if(getRouteLength(solution) < minDistance)
            {
                minDistance = getRouteLength(solution);
                bestSolution = solution;
            }
        }
        return bestSolution;
    }

    public static double getRouteLength(ArrayList<Point> points) {
        double route = 0;
        for (int i = 0; i < points.size(); i++) {
            if (i == points.size() - 1)  route += Point.distance(points.get(i), points.get(0));
            else route += Point.distance(points.get(i), points.get(i+1));
        }
        return route;
    }

}
