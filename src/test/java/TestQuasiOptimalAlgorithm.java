import org.example.Point;
import org.example.Algorithms.QuasiOptimalAlgorithm.QuasiOptimalAlgorithm;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class TestQuasiOptimalAlgorithm {

    @Test
    public void solutionFromRandomPointsTest() {
        ArrayList<Point> points = Point.createRandomPoints(100);
        QuasiOptimalAlgorithm quasiOptimalAlgorithm = new QuasiOptimalAlgorithm(points);
        ArrayList<Point> solution1 = quasiOptimalAlgorithm.getTSPSolution();

        Collections.shuffle(points);
        ArrayList<Point> solution2 = quasiOptimalAlgorithm.getTSPSolution();

        Collections.shuffle(points);
        ArrayList<Point> solution3 = quasiOptimalAlgorithm.getTSPSolution();

        for(int i=0 ; i<solution1.size() ; ++i){
            Assert.assertEquals(solution1.get(i), solution2.get(i));
            Assert.assertEquals(solution1.get(i), solution3.get(i));
            Assert.assertEquals(solution2.get(i), solution3.get(i));
        }
    }

    @Test
    public void solutionTest1() {
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

        QuasiOptimalAlgorithm quasiOptimalAlgorithm = new QuasiOptimalAlgorithm(points);
        ArrayList<Point> result = quasiOptimalAlgorithm.getTSPSolution();

        ArrayList<Point> tspSolution = new ArrayList<>();

        tspSolution.add(new Point(75,20));
        tspSolution.add(new Point(76,11));
        tspSolution.add(new Point(90,1));
        tspSolution.add(new Point(96,7));
        tspSolution.add(new Point(95,67));
        tspSolution.add(new Point(4,95));
        tspSolution.add(new Point(23,77));
        tspSolution.add(new Point(58,51));
        tspSolution.add(new Point(71,45));
        tspSolution.add(new Point(63,20));

        int convexHullSize = result.size();
        Assert.assertEquals(convexHullSize,tspSolution.size());

        for(int i=0 ; i<convexHullSize;++i)
        {
            Assert.assertEquals(result.get(i), tspSolution.get(i));
        }
    }

    @Test
    public void solutionTest2() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(75,43));
        points.add(new Point(94,72));
        points.add(new Point(88,51));
        points.add(new Point(59,20));
        points.add(new Point(38,93));
        points.add(new Point(66,19));
        points.add(new Point(10,50));
        points.add(new Point(74,8));
        points.add(new Point(96,59));
        points.add(new Point(63,60));
        points.add(new Point(63,48));
        points.add(new Point(51,49));
        points.add(new Point(96,33));
        points.add(new Point(82,46));
        points.add(new Point(92,5));

        Assert.assertEquals(points.size(), 15);

        QuasiOptimalAlgorithm quasiOptimalAlgorithm = new QuasiOptimalAlgorithm(points);
        ArrayList<Point> result = quasiOptimalAlgorithm.getTSPSolution();

        ArrayList<Point> tspSolution = new ArrayList<>();

        tspSolution.add(new Point(92,5));
        tspSolution.add(new Point(96,33));
        tspSolution.add(new Point(82,46));
        tspSolution.add(new Point(63,48));
        tspSolution.add(new Point(51,49));
        tspSolution.add(new Point(63,60));
        tspSolution.add(new Point(75,43));
        tspSolution.add(new Point(88,51));
        tspSolution.add(new Point(96,59));
        tspSolution.add(new Point(94,72));
        tspSolution.add(new Point(38,93));
        tspSolution.add(new Point(10,50));
        tspSolution.add(new Point(59,20));
        tspSolution.add(new Point(66,19));
        tspSolution.add(new Point(74,8));


        int convexHullSize = result.size();
        Assert.assertEquals(convexHullSize,tspSolution.size());

        for(int i=0 ; i<convexHullSize;++i)
        {
            Assert.assertEquals(result.get(i), tspSolution.get(i));
        }
    }

}
