package org.example.Algorithms.QuasiOptimalAlgorithm;

import org.example.Point;

import java.util.ArrayList;


public class QuasiOptimalAlgorithm {
    private final ArrayList<Point> points;
    private final ArrayList<Point> convexHull;
    private ArrayList<Point> resultPoints = new ArrayList<>();

    public QuasiOptimalAlgorithm(ArrayList<Point> points) {
        this.points = points;
        this.convexHull = GrahamAlgorithm.convexHullFinder(points);
        resultPoints.addAll(convexHull);

        System.out.println("Graham Scan Convex Hull:");
        for (Point point : convexHull) System.out.print(points.indexOf(point) + ":" + point + ",  ");
        System.out.println();
    }

    public ArrayList<Point> getTSPSolution() {
        resultPoints = findPlaceForPoints(createPointsInsideArray());

        System.out.println(resultPoints);
        return resultPoints;
    }

    private ArrayList<Point> createPointsInsideArray() {
        ArrayList<Point> pointsInside = new ArrayList<>(points);
        pointsInside.removeAll(convexHull);

        return pointsInside;
    }

    //zwraca numer indexu w tablicy pod który należy dodać punkt
    //np. Jeśli chcemy wstawić punkt N, pomiedzy C i D, zwrócony zostanie index punktu D
    private ArrayList<Point> findPlaceForPoints(ArrayList<Point> insidePoints) {
        while (!insidePoints.isEmpty()) {
            int indP = 0;
            int indB = -1;
            double d_min = -1;
            for (int p = 0; p < insidePoints.size(); ++p) {
                Point pointP = insidePoints.get(p);
                for (int r = 0; r < resultPoints.size(); ++r) {
                    int tmpA = r == 0 ? resultPoints.size() - 1 : r - 1; //result future location : pointA pointP pointB
                    Point pointA = resultPoints.get(tmpA);
                    Point pointB = resultPoints.get(r);

                    Point projectionPoint = Point.projection(pointA, pointB, pointP);
                    double APro = Point.distance(pointA, projectionPoint);
                    double BPro = Point.distance(pointB, projectionPoint);
                    double AP = Point.distance(pointA, pointP);
                    double BP = Point.distance(pointB, pointP);
                    double AB = Point.distance(pointA, pointB);
                    double PPro = Point.distance(projectionPoint, pointP); // shortest way from pointP to segment, length

                    int tmpR = r;
                    //if (AB * AB <= AP * AP + BP * BP) PPro = (AP + BP) / 2; //ten fragment pochodzi z notatek Pani Tatiany, jednak algorytm nie działa wtedy poprawnie i nie rozumiem za bardzo
                    //zamiast tego
                    if (APro + BPro > AB) { //punkt, którego projekcja nie przypada na odcinku AB
                        int tmpIndex;
                        //krótsze ramie może być takie samo dla 2 sąsiednich odcinków, więc należy sprawdzić, który z nich będzie lepszy dla punktu
                        if (AP < BP) { //index od pointA -1
                            tmpIndex = tmpA == 0 ? resultPoints.size() - 1 : tmpA - 1;
                            if(PPro< Point.distance(pointP, Point.projection( resultPoints.get(tmpIndex),pointA, pointP)))
                                tmpR = tmpIndex;
                        } else { //index od pointB +1
                            tmpIndex = (r == resultPoints.size() - 1) ? 0 : r + 1;
                            if(PPro< Point.distance(pointP, Point.projection( pointB, resultPoints.get(tmpIndex), pointP)))
                                tmpR = tmpIndex;
                        }
                        PPro = Math.min(AP, BP);
                    }

                    if (d_min < 0 || (d_min > PPro )) {
                        d_min = PPro;
                        indP = p;
                        indB = tmpR;
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

}