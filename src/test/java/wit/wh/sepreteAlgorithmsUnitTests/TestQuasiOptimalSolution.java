package wit.wh.sepreteAlgorithmsUnitTests;

import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class TestQuasiOptimalSolution {

    /**
     * Tests algorithm behavior when the same dataset is given, but shuffled.
     */
    @Test
    public void testIfSolutionIsSameWhenGivenPointsAreShuffle() {
        ArrayList<Point> inputPoints = PointUtils.generateRandomPoints(15);

        ArrayList<Point> solution1 = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        Collections.shuffle(inputPoints);
        ArrayList<Point> solution2 = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        Collections.shuffle(inputPoints);
        ArrayList<Point> solution3 = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        for(int i=0 ; i<solution1.size() ; ++i){
            assertEquals(solution1.get(i), solution2.get(i));
            assertEquals(solution1.get(i), solution3.get(i));
            assertEquals(solution2.get(i), solution3.get(i));
        }
    }

    /**
     * Tests behavior when specific dataset given.
     */
    @Test
    public void testSolutionWithSpecificDataset() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<Point>();
        inputPoints.add(new Point(75,20));
        inputPoints.add(new Point(63,20));
        inputPoints.add(new Point(58,51));
        inputPoints.add(new Point(4,95));
        inputPoints.add(new Point(23,77));
        inputPoints.add(new Point(90,1));
        inputPoints.add(new Point(96,7));
        inputPoints.add(new Point(76,11));
        inputPoints.add(new Point(95,67));
        inputPoints.add(new Point(71,45));

        assertEquals(10, inputPoints.size());

        ArrayList<Point> tspSolution = new ArrayList<>();

        tspSolution.add(new Point(76,11));
        tspSolution.add(new Point(90,1));
        tspSolution.add(new Point(96,7));
        tspSolution.add(new Point(95,67));
        tspSolution.add(new Point(4,95));
        tspSolution.add(new Point(23,77));
        tspSolution.add(new Point(58,51));
        tspSolution.add(new Point(71,45));
        tspSolution.add(new Point(75,20));
        tspSolution.add(new Point(63,20));
        tspSolution.add(new Point(76,11));

        assertEquals(11, tspSolution.size());

        //when
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(tspSolution.size(), result.size());

        for(int i=0 ; i< result.size();++i)
            assertEquals(tspSolution.get(i), result.get(i));
    }

    /**
     * Tests behavior when another specific dataset given.
     */
    @Test
    public void testSolutionWithSpecificDataset2() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<Point>();
        inputPoints.add(new Point(75,43));
        inputPoints.add(new Point(94,72));
        inputPoints.add(new Point(88,51));
        inputPoints.add(new Point(59,20));
        inputPoints.add(new Point(38,93));
        inputPoints.add(new Point(66,19));
        inputPoints.add(new Point(10,50));
        inputPoints.add(new Point(74,8));
        inputPoints.add(new Point(96,59));
        inputPoints.add(new Point(63,60));
        inputPoints.add(new Point(63,48));
        inputPoints.add(new Point(51,49));
        inputPoints.add(new Point(96,33));
        inputPoints.add(new Point(82,46));
        inputPoints.add(new Point(92,5));

        assertEquals(15, inputPoints.size());

        ArrayList<Point> tspSolution = new ArrayList<>();
        tspSolution.add(new Point(92,5));
        tspSolution.add(new Point(96,33));
        tspSolution.add(new Point(75,43));
        tspSolution.add(new Point(82,46));
        tspSolution.add(new Point(88,51));
        tspSolution.add(new Point(96,59));
        tspSolution.add(new Point(94,72));
        tspSolution.add(new Point(38,93));
        tspSolution.add(new Point(10,50));
        tspSolution.add(new Point(51,49));
        tspSolution.add(new Point(63,60));
        tspSolution.add(new Point(63,48));
        tspSolution.add(new Point(59,20));
        tspSolution.add(new Point(66,19));
        tspSolution.add(new Point(74,8));
        tspSolution.add(new Point(92,5));

        assertEquals(16, tspSolution.size());

        //when
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(inputPoints.size()+1, result.size());

        for(int i=0 ; i< tspSolution.size();++i)
            assertEquals(tspSolution.get(i), result.get(i));
    }

    /**
     * Creates a basic square with one point inside — a classic convex hull case.
     */
    @Test
    public void testConvexHullWithSingleInnerPoint() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        inputPoints.add(new Point(5, 5));
        inputPoints.add(new Point(10, 0));
        inputPoints.add(new Point(0, 10));
        inputPoints.add(new Point(10, 10));

        //when
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertNotNull("Returned path should not be null", result);
        assertEquals(inputPoints.size() + 1, result.size());
        assertTrue(result.containsAll(inputPoints));
        assertEquals(result.get(0), result.get(result.size() - 1));
    }

    /**
     * Tests behavior when only a single point is given.
     */
    @Test
    public void testSinglePointInput() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        inputPoints.add(new Point(5, 5));

        //when
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(1, result.size());
    }

    /**
     * Tests behavior when the input is empty.
     */
    @Test
    public void testEmptyInput() {
        //given
        ArrayList<Point> inputPoints = new ArrayList();

        //when
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(0, result.size());
    }

    /**
     * Tests with points that are colinear — convex hull will include all of them.
     */
    @Test
    public void testColinearPoints() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        inputPoints.add(new Point(0, 0));
        inputPoints.add(new Point(5, 5));
        inputPoints.add(new Point(10, 10));
        inputPoints.add(new Point(11, 11));
        inputPoints.add(new Point(12, 12));

        //when
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(6, result.size());
        assertTrue(result.containsAll(inputPoints));
    }

    /**
     * Test with larger number of randomly placed points.
     */
    @Test
    public void testRandomClusteredPoints() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            inputPoints.add(new Point(Math.random() * 100, Math.random() * 100));

        //when
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.QUASI_OPTIMIZATION_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(inputPoints.size() + 1, result.size());
        assertTrue("Result must include all input points", result.containsAll(inputPoints));
    }

}
