package org.example.Algorithms;

import org.example.Point;

import java.util.ArrayList;

public class RepetitiveNearestNeighbourAlgorithm {
    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        double minDistance = Double.MAX_VALUE;
        ArrayList<Point> bestSolution = new ArrayList<>();
        for (Point p : points) {
            ArrayList<Point> solution = NearestNeighbourAlgorithm.getTSPSolution(points,p);
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
