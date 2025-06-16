package org.example;

import org.example.pointUtils.Point;
import org.example.pointUtils.PointUtils;

import java.util.ArrayList;

public abstract class TSPSolution {
    protected ArrayList<Point> solutionPoints;
    protected double routeLength;

    protected abstract ArrayList<Point> getTSPSolution(ArrayList<Point> entryPointList) ;

    public ArrayList<Point> getSolutionPoints(ArrayList<Point> entryPointList) {
        if(solutionPoints == null)
            solutionPoints = getTSPSolution(entryPointList);
        return solutionPoints;
    }

    public double getRouteLength() {
        if(routeLength == 0)
            routeLength = calculateRouteLength();
        return routeLength;
    }

    private double calculateRouteLength() {
        double route = 0;
        for (int i = 0; i < solutionPoints.size(); i++) {
            if (i == solutionPoints.size() - 1)
                route += PointUtils.distance(solutionPoints.get(i), solutionPoints.get(0));
            else
                route += PointUtils.distance(solutionPoints.get(i), solutionPoints.get(i+1));
        }
        return route;
    }
}
