package org.example.Algorithms;

import org.example.TSPSolution;
import org.example.pointUtils.Point;
import org.example.pointUtils.PointUtils;

import java.util.ArrayList;

public class RepetitiveNearestNeighbourAlgorithm extends TSPSolution {
    protected ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        double minDistance = Double.MAX_VALUE;
        ArrayList<Point> bestSolution = new ArrayList<>();
        for (Point p : points) {
            ArrayList<Point> solution = NearestNeighbourAlgorithm.getTSPSolutionBasedOnStartingPoint(points,p);
            if(getRouteLength(solution) < minDistance)
            {
                minDistance = getRouteLength(solution);
                bestSolution = solution;
            }
        }
        return bestSolution;
    }

    private double getRouteLength(ArrayList<Point> points) {
        double route = 0;
        for (int i = 0; i < points.size(); i++) {
            if (i == points.size() - 1)  route += PointUtils.distance(points.get(i), points.get(0));
            else route += PointUtils.distance(points.get(i), points.get(i+1));
        }
        return route;
    }
}
