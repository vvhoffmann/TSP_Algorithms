package org.example.Algorithms.SAAlgorithm;

import org.example.pointUtils.Point;
import org.example.pointUtils.PointUtils;

import java.util.ArrayList;

public class Path {

    private ArrayList<Point> points;
    private ArrayList<Point> previouspoints = new ArrayList<>();

    public Path(ArrayList<Point> points) {
        this.points = points;
    }

    public void swapPoints() {
        int a = generateRandomIndex();
        int b = generateRandomIndex();
        previouspoints = new ArrayList<>(points);
        Point x = points.get(a);
        Point y = points.get(b);
        points.set(a, y);
        points.set(b, x);
    }

    public void revertSwap() {
        points = previouspoints;
    }

    private int generateRandomIndex() {
        return (int) (Math.random() * points.size());
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public double getDistance() {
        double distance = 0;
        for (int index = 0; index < points.size(); index++) {
            Point starting = getPoint(index);
            Point destination;
            if (index + 1 < points.size()) {
                destination = getPoint(index + 1);
            } else {
                destination = getPoint(0);
            }
            distance += PointUtils.distance(starting,destination);
        }
        return distance;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}