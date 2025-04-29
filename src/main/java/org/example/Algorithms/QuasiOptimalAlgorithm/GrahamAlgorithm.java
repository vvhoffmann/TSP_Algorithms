package org.example.Algorithms.QuasiOptimalAlgorithm;

import org.example.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class GrahamAlgorithm {

    public static Point getMinY(ArrayList<Point> points)
    {
        return points.stream().min(Point::min).get();
    }

    public static int countPointDirection(Point a, Point b, Point r)
    {
        return (int)Math.signum((b.y - a.y) * (r.x - b.x) - (b.x - a.x) * (r.y - b.y));
    }

    private static void sortByAngle(ArrayList<Point> points, Point firstPoint) {
        points.sort((b, c) -> {
            //punkt startowy musi być na początku, więc szukamy go
            if (b == firstPoint) return -1;
            if (c == firstPoint) return 1;

            int newPointDirection = countPointDirection(firstPoint, b, c);

            if (newPointDirection == 0) { //Dla punktów współliniowych
                if (b.x == c.x)
                    return b.y < c.y ? -1 : 1; //bliższy punkt najpierw
                else
                    return b.x < c.x ? -1 : 1;
            } else
                return newPointDirection * -1;
        });
    }

    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points)
    {
        Point minYPoint = getMinY(points);
        //sortuje wg kata względem pierwszego wierzchołka i osi OX rosnąco
        sortByAngle(points, minYPoint);
        return points;
    }

    public static ArrayList<Point> convexHullFinder(ArrayList<Point> sourcePoints) {
        ArrayList<Point> points = new ArrayList<Point>();
        points.addAll(sourcePoints);
        Stack<Point> stack = new Stack<Point>();

        //wybieram pierwszy wierzchołek, posiadający jak najmniejszą współrzędną y i x
        Point minYPoint = getMinY(points);
        //sortuje wg kata względem pierwszego wierzchołka i osi OX rosnąco
        sortByAngle(points, minYPoint);

        stack.push(points.get(0)); // na stos wkładamy pierwszy punkt
        stack.push(points.get(1)); // na stos wkładamy punkt, który będziemy sprawdzać

        for (int i = 2, size = points.size(); i < size; i++) {
            Point next = points.get(i);
            Point p = stack.pop();

            while (stack.peek() != null && countPointDirection(p, next, stack.peek()) < 0) {
                p = stack.pop(); // usuwamy punkt, ponieważ idzie zgodnie z kierunkiem zegara
            }

            stack.push(p);
            stack.push(next);
        }

        ArrayList<Point> result = new ArrayList<>(stack);
        result.add(result.get(0));
        result.remove(result.get(0));
        Collections.reverse(result);

        return result;
    }
}