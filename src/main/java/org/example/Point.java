package org.example;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Point extends Point2D.Double {
    public Point(double x, double y) {
        super(x, y);
    }

    public static ArrayList<Point> createRandomPoints(int size) {
        ArrayList<Point> arrayList = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; ++i) {
            Point point = new Point(random.nextInt(100), random.nextInt(100));
            if (arrayList.contains(point)) --i;
            else arrayList.add(point);
        }
        getReadyPoints(arrayList);
        System.out.println(" ");
        return arrayList;
    }

    private static ArrayList<Point> getReadyPoints(ArrayList<Point> arrayList) {
//        arrayList.add(new Point(75, 43));
//        arrayList.add(new Point(94, 72));
//        arrayList.add(new Point(88, 51));
//        arrayList.add(new Point(59, 20));
//        arrayList.add(new Point(38, 93));
//        arrayList.add(new Point(66, 19));
//        arrayList.add(new Point(10, 50));
//        arrayList.add(new Point(74, 8));
//        arrayList.add(new Point(96, 59));
//        arrayList.add(new Point(63, 60));
//        arrayList.add(new Point(63, 48));
//        arrayList.add(new Point(51, 49));
//        arrayList.add(new Point(96, 33));
//        arrayList.add(new Point(82, 46));
//        arrayList.add(new Point(92, 5));
//        arrayList.add(new Point(63, 20));
//        arrayList.add(new Point(4, 95));
//        arrayList.add(new Point(95, 67));
//        arrayList.add(new Point(71, 45));
//        arrayList.add(new Point(58, 51));
//        arrayList.add(new Point(23, 77));
//        arrayList.add(new Point(96, 7));
//        arrayList.add(new Point(90, 1));
//        arrayList.add(new Point(76, 11));
//        arrayList.add(new Point(75, 20));

        return arrayList;
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

    public double distance(Point b) {
        return Math.hypot(this.x - b.x, this.y - b.y);
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

    @Override
    public String toString() {
        return " [" + x + ", " + y + ']';
    }
}