package wit.wh.pointUtils;

import java.util.ArrayList;
import java.util.Random;

public class PointUtils {
    public static double[][] getDistanceArray(ArrayList<Point> points) {
        int n = points.size();
        double[][] dist = new double[n][n]; // Macierz odległości
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                dist[i][j] = distance(points.get(i), points.get(j));

        return dist;
    }

    public static long doubleDiv(double a, double b) {
        return (long) (a / b);
    }

    public static double doubleMod(double a, double b) {
        return a - doubleDiv(a, b) * b;
    }

    public static double angle(Point a, Point b) {
        return doubleMod(Math.atan2(b.y - a.y, b.x - a.x) + Math.PI, Math.PI);
    }

    public static ArrayList<Point> createRandomPoints(int size) {
        ArrayList<Point> points = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; ++i) {
            Point point = new Point(random.nextInt(25), random.nextInt(25));
            if (points.contains(point)) --i;
            else points.add(point);
        }
        System.out.println(" ");
        return points;
    }

    public static ArrayList<Point> getReadyPoints() {
        ArrayList<Point> points = new ArrayList<>();
//        points.add(new Point(75, 43));
//        points.add(new Point(94, 72));
//        points.add(new Point(88, 51));
//        points.add(new Point(59, 20));
//        points.add(new Point(38, 93));
//        points.add(new Point(66, 19));
//        points.add(new Point(10, 50));
//        points.add(new Point(74, 8));
//        points.add(new Point(96, 59));
//        points.add(new Point(63, 60));
//        points.add(new Point(63, 48));
//        points.add(new Point(51, 49));
//        points.add(new Point(96, 33));
//        points.add(new Point(82, 46));
//        points.add(new Point(92, 5));
//        points.add(new Point(63, 20));
//        points.add(new Point(4, 95));
//        points.add(new Point(95, 67));
//        points.add(new Point(71, 45));
//        points.add(new Point(58, 51));
//        points.add(new Point(23, 77));
//        points.add(new Point(96, 7));
//        points.add(new Point(90, 1));
//        points.add(new Point(76, 11));
//        points.add(new Point(75, 20));
//        points.add(new Point(13, 22));
//        points.add(new Point(14, 27));
//        points.add(new Point(20, 27));
//        points.add(new Point(20, 14));
//        points.add(new Point(51.57, 47));

//        points.add(new Point(75,43));
//        points.add(new Point(94,72));
//        points.add(new Point(88,51));
//        points.add(new Point(59,20));
//        points.add(new Point(38,93));
//        points.add(new Point(66,19));
//        points.add(new Point(10,50));
//        points.add(new Point(74,8));
//        points.add(new Point(96,59));
//        points.add(new Point(63,60));
//        points.add(new Point(63,48));
//        points.add(new Point(51,49));
//        points.add(new Point(96,33));
//        points.add(new Point(82,46));
//        points.add(new Point(92,5));

        points.add(new Point(75,20));
        points.add(new Point(63,20));
        points.add(new Point(58,51));
        points.add(new Point(4,95));
        points.add(new Point(23,77));
        points.add(new Point(90,1));
        points.add(new Point(96,7));
        points.add(new Point(76,11));
        points.add(new Point(95,67));
        points.add(new Point(71,45));
        return points;
    }

    public static double calculateShortestWayToPoint(Point a, Point b, Point p) {
        return Math.min(Math.min(distance(a, p), distance(b, p)), distance(projection(a, b, p), p));
    }

    public static Point projection(Point a, Point b, Point c) {
        double BAy = b.y - a.y, BAx = b.x - a.x, CAy = c.y - a.y, CAx = c.x - a.x;
        double BAy2 = BAy * BAy, BAx2 = BAx * BAx, BA2 = BAy2 + BAx2;
        return new Point
                (
                        (a.x * BAy2 + CAy * BAx * BAy + c.x * BAx2) / BA2,
                        (a.y * BAx2 + CAx * BAy * BAx + c.y * BAy2) / BA2
                );

    }

    public static boolean less(Point a, Point b) {
        return ((a.y < b.y) || ((a.y == b.y) && (a.x < b.x)));
    }

    public static int min(Point a, Point b) {
        if (less(a, b)) return -1;
        if (less(b, a)) return +1;
        return 0;
    }

    public static double distance(Point a, Point b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }
}
