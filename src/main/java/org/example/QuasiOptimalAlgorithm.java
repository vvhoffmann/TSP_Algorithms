package org.example;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class QuasiOptimalAlgorithm {
    private final ArrayList<Point> points;
    private final ArrayList<Point> convexHull;
    private ArrayList<Point> resultPoints = new ArrayList<Point>();

    private Map<Integer, Point> pointsOrder = new LinkedHashMap<>();

    public QuasiOptimalAlgorithm(ArrayList<Point> points) {
        this.points = points;
        this.convexHull = GrahamAlgorithm.convexHullFinder(points);
        resultPoints.addAll(convexHull);

        System.out.println("Graham Scan Convex Hull:");
        for (int i = 0; i < convexHull.size(); ++i) {
            System.out.print(points.indexOf(convexHull.get(i)) + ":" + convexHull.get(i) + ",  ");
        }
        System.out.println();
    }

    public ArrayList<Point> getTSPSolution() {
        resultPoints = findPlaceForPoints(createPointsInsideArray());

        System.out.println(resultPoints);
        return resultPoints;
    }

    private ArrayList<Point> createPointsInsideArray() {
        ArrayList<Point> pointsInside = new ArrayList<Point>(points);
        pointsInside.removeAll(convexHull);

        return pointsInside;
    }

    //zwraca numer indexu w tablicy pod który należy dodać punkt
    //np. Jeśli chcemy wstawić punkt N, pomiedzy C i D, zwrócony zostanie index punktu D
    private ArrayList<Point> findPlaceForPoints(ArrayList<Point> insidePoints) {
        while (!insidePoints.isEmpty()) {
            int indP = 0;
            int indA = -1;
            int indB = -1;
            double d_min = -1;
            for (int p = 0; p < insidePoints.size(); ++p) {
                Point point = insidePoints.get(p);
                for (int r = 0; r < resultPoints.size(); ++r) {
                    int tmpA = r == 0 ? resultPoints.size() - 1 : r - 1; //result final location
                    //a point b
                    Point a = resultPoints.get(tmpA);
                    Point b = resultPoints.get(r);

                    Point projectionPoint = Point.projection(a, b, point);
                    double APro = Point.distance(a, projectionPoint);
                    double BPro = Point.distance(b, projectionPoint);
                    double AP = Point.distance(a, point);
                    double BP = Point.distance(b, point);
                    double AB = Point.distance(a, b);
                    double PPro = Point.distance(projectionPoint, point); // shortest way from point to segment lenght

                    double distance = Math.min(APro, BPro);

                    if (AB * AB <= AP * AP + BP * BP) PPro = (AP + BP) / 2; //przypadki spoza zakresu AB

                    if (d_min < 0 || (d_min > PPro)) {
                        d_min = PPro;
                        indP = p;
                        indA = tmpA;
                        indB = r;
                    }
                }
            }
            Point targetPoint = insidePoints.get(indP);
            addPointtoTheRoad(indB, targetPoint);
            insidePoints.remove(indP);
        }
        return resultPoints;
    }

    private void addPointtoTheRoad(int index, Point point) {
        pointsOrder.put(points.indexOf(point), point);
        System.out.println("Dodaje Punkt " + points.indexOf(point) + " [" + point.x + ", " + point.y +
                "] pod index " + index +
                " pomiedzy punkt " + points.indexOf(resultPoints.get(index == 0 ? resultPoints.size() - 1 : index - 1)) +
                " i " + points.indexOf(resultPoints.get(index)));
        resultPoints.add(index, point);

        System.out.println(resultPoints);
    }

    public ArrayList<Point> getConvexHull() {
        return convexHull;
    }

    public void printPointsOrder() {
        System.out.println(pointsOrder);
    }
}