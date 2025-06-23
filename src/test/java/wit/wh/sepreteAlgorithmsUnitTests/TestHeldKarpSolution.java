package wit.wh.sepreteAlgorithmsUnitTests;

import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.Point;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestHeldKarpSolution {

    /**
     * Test that null inputPoints returns an empty list.
     */
    @org.junit.jupiter.api.Test
    void testNullInput() {
        //given
        ArrayList<Point> inputPoints = null;

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.HELD_KARP_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertTrue("Expected empty list for null inputPoints", result.isEmpty());
    }

    /**
     * Test that empty inputPoints list returns an empty path.
     */
    @org.junit.jupiter.api.Test
    void testEmptyInput() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.HELD_KARP_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertNotNull(result);
        assertTrue("Expected empty list for empty inputPoints", result.isEmpty());
    }

    /**
     * Test that a single-point inputPoints returns ONE POINT Route.
     */
    @org.junit.jupiter.api.Test
    void testSinglePointInput() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        Point p = new Point(5, 5);
        inputPoints.add(p);

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.HELD_KARP_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(1, result.size());
        assertEquals(p, result.get(0));
    }

    /**
     * Test that the route is a valid round trip (starts and ends at the same point).
     */
    @org.junit.jupiter.api.Test
    void testThreePointsInput() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        inputPoints.add(new Point(0, 0));
        inputPoints.add(new Point(0, 1));
        inputPoints.add(new Point(1, 1));

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.HELD_KARP_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(result.get(0), result.get(result.size() - 1));
    }

    /**
     * Test that all inputPoints points are visited exactly once (excluding return to start).
     */
    @org.junit.jupiter.api.Test
    void testIfRoundRouteCreated() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        inputPoints.add(new Point(0, 0));
        inputPoints.add(new Point(0, 1));
        inputPoints.add(new Point(1, 0));
        inputPoints.add(new Point(1, 1));

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.HELD_KARP_ALGORITHM, inputPoints).getSolutionPoints();
        long distinctCount = result.subList(0, result.size() - 1).stream().distinct().count();

        //then
        assertEquals(inputPoints.size() + 1, result.size());
        assertTrue(result.containsAll(inputPoints));
        assertEquals(inputPoints.size(), distinctCount);
    }

    /**
     * Test with 15 randomly placed points.
     */
    @org.junit.jupiter.api.Test
    public void testRandom15ClusteredPoints() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        for (int i = 0; i < 15; i++)
            inputPoints.add(new Point(Math.random() * 100, Math.random() * 100));

        //when
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.HELD_KARP_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertEquals(inputPoints.size() + 1, result.size());
        assertTrue("Result must include all input points", result.containsAll(inputPoints));
    }
}
