package wit.wh.sepreteAlgorithmsUnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.Point;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestACOSolution {
    /**
     * Test that null inputPoints returns an empty list.
     */
    @Test
    void testNullInput() {
        //given
        ArrayList<Point> inputPoints = null;

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertNotNull(result);
        Assertions.assertTrue(result.isEmpty(), "Expected empty list for null inputPoints");
    }

    /**
     * Test that empty inputPoints list returns an empty path.
     */
    @Test
    void testEmptyInput() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        assertNotNull(result);
        Assertions.assertTrue(result.isEmpty(), "Expected empty list for empty inputPoints");
    }

    /**
     * Test that a single-point inputPoints returns a valid round trip (point repeated).
     */
    @Test
    void testSinglePointInput() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        Point p = new Point(5, 5);
        inputPoints.add(p);

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(p, result.get(0));
    }

    /**
     * Test that the route is a valid round trip (starts and ends at the same point).
     */
    @Test
    void testThreePointsInput() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        inputPoints.add(new Point(0, 0));
        inputPoints.add(new Point(0, 1));
        inputPoints.add(new Point(1, 1));

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        Assertions.assertEquals(result.get(0), result.get(result.size() - 1));
    }

    /**
     * Test that all inputPoints points are visited exactly once (excluding return to start).
     */
    @Test
    void testIfRoundRouteCreated() {
        //given
        ArrayList<Point> inputPoints = new ArrayList<>();
        inputPoints.add(new Point(0, 0));
        inputPoints.add(new Point(0, 1));
        inputPoints.add(new Point(1, 0));
        inputPoints.add(new Point(1, 1));

        //when
        List<Point> result = TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints).getSolutionPoints();
        long distinctCount = result.subList(0, result.size() - 1).stream().distinct().count();

        //then
        Assertions.assertEquals(inputPoints.size() + 1, result.size());
        Assertions.assertTrue(result.containsAll(inputPoints));
        Assertions.assertEquals(inputPoints.size(), distinctCount);
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
        ArrayList<Point> result = TSPSolutionFactory.createSolution(SolutionType.ACO_ALGORITHM, inputPoints).getSolutionPoints();

        //then
        Assertions.assertEquals(inputPoints.size() + 1, result.size());
        Assertions.assertTrue(result.containsAll(inputPoints), "Result must include all input points");
    }
}
