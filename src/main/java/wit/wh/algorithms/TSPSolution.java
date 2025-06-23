package wit.wh.algorithms;

import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.util.ArrayList;

public abstract class TSPSolution {
    protected final AlgoritmParameters parameters;
    protected ArrayList<Point> solutionPoints;
    public ArrayList<Point> inputPoints;

    protected double routeLength;

    public TSPSolution(ArrayList<Point> inputPoints, AlgoritmParameters parameters) {
        this.inputPoints = inputPoints;
        this.parameters = parameters;
        this.solutionPoints = setSolutionPoints();
        this.routeLength = calculateRouteLength();
    }

    public TSPSolution(ArrayList<Point> inputPoints) {
        this.inputPoints = inputPoints;
        this.parameters = null;
        this.solutionPoints = setSolutionPoints();
        this.routeLength = calculateRouteLength();
    }

    protected abstract ArrayList<Point> getTSPSolution() ;

    protected ArrayList<Point> setSolutionPoints() {
        if (inputPoints == null)
            return new ArrayList<>();

        if(inputPoints.size() < PathUtils.MIN_POINTS_TO_RUN_ALGORITHM)
            return inputPoints;

        return getTSPSolution();
    }

    protected double calculateRouteLength() {
        double route = 0;
        for (int i = 0; i < solutionPoints.size(); i++) {
            if (i == solutionPoints.size() - 1)
                route += PointUtils.distance(solutionPoints.get(i), solutionPoints.get(0));
            else
                route += PointUtils.distance(solutionPoints.get(i), solutionPoints.get(i+1));
        }
        return route;
    }

    public ArrayList<Point> getSolutionPoints() {
        return solutionPoints;
    }

    public double getRouteLength() {
        return routeLength;
    }

    public AlgoritmParameters parameters() {
        return parameters;
    }
}
