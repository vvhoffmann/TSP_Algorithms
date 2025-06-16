import org.example.Algorithms.QuasiOptimizationAlgorithm.GrahamAlgorithm;
import org.example.pointUtils.Point;
import org.example.pointUtils.PointUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TestGrahamAlgorithm {
    private int convexHullCheck(Point A, Point B, Point C)
    {
        int newPointDirection = (int)Math.signum((B.x-C.x)*(A.y-C.y)-(A.x-C.x)*(B.y-C.y));
        if (newPointDirection == 0) { //Dla punktów współliniowych
            if (B.x == C.x)
                return B.y < C.y ? -1 : 1; //bliższy punkt najpierw
            else
                return B.x < C.x ? -1 : 1;
        }else
            return newPointDirection* (-1);
    }

    @Test
    public void convexHullFromRandomPointsTest() {
        ArrayList<Point> points = PointUtils.createRandomPoints(100);
        ArrayList<Point> convexHull = GrahamAlgorithm.getConvexHull(points);

        for(int i=0 ; i<convexHull.size() ; ++i){
            Point A = convexHull.get(i);
            Point B = convexHull.get((i+1)%convexHull.size());
            Point C = convexHull.get((i+2)%convexHull.size());

            Assert.assertEquals(convexHullCheck(A,B,C),1);
        }
    }

    @Test
    public void convexHullTest() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(71,45));
        points.add(new Point(75,20));
        points.add(new Point(63,20));
        points.add(new Point(58,51));
        points.add(new Point(4,95));
        points.add(new Point(23,77));
        points.add(new Point(90,1));
        points.add(new Point(96,7));
        points.add(new Point(76,11));
        points.add(new Point(95,67));

        Assert.assertEquals(points.size(), 10);

        ArrayList<Point> convexHull = GrahamAlgorithm.getConvexHull(points);

        ArrayList<Point> solution = new ArrayList<>();
        solution.add(new Point(90,1));
        solution.add(new Point(96,7));
        solution.add(new Point(95,67));
        solution.add(new Point(4,95));
        solution.add(new Point(63,20));

        int convexHullSize = convexHull.size();
        Assert.assertEquals(convexHullSize,solution.size());

        for(int i=0 ; i<convexHullSize;++i)
        {
            Assert.assertEquals(convexHull.get(i), solution.get(i));

            Point A = convexHull.get(i);
            Point B = convexHull.get((i+1)%convexHullSize);
            Point C = convexHull.get((i+2)%convexHullSize);

            Assert.assertEquals(convexHullCheck(A,B,C),1);
        }

        /*ArrayList<Point> tspSolution = new ArrayList<>();
        tspSolution.add(new Point(90,1));
        tspSolution.add(new Point(76,11));
        tspSolution.add(new Point(75,20));
        tspSolution.add(new Point(63,20));
        tspSolution.add(new Point(71,45));
        tspSolution.add(new Point(58,51));
        tspSolution.add(new Point(23,77));
        tspSolution.add(new Point(4,95));
        tspSolution.add(new Point(95,67));
        tspSolution.add(new Point(96,7));*/
    }

    @Test
    public void timeMeasure()
    {
        ArrayList<Point> points = PointUtils.createRandomPoints(100);

        long millisActualTime = System.currentTimeMillis(); // początkowy czas w milisekundach.
        ArrayList<Point> convexHull = GrahamAlgorithm.getConvexHull(points);
        long executionTime = System.currentTimeMillis() - millisActualTime; // czas wykonania programu w milisekundach.
        System.out.println("Czas wykonania dla 100 punktów: "+executionTime+" ms");

        points = PointUtils.createRandomPoints(500);

        millisActualTime = System.currentTimeMillis(); // początkowy czas w milisekundach.
        convexHull = GrahamAlgorithm.getConvexHull(points);
        executionTime = System.currentTimeMillis() - millisActualTime; // czas wykonania programu w milisekundach.
        System.out.println("Czas wykonania dla 500 punktów: "+executionTime+" ms");
    }
}
