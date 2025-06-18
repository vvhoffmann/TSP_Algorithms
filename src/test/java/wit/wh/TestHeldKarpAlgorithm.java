package wit.wh;

import wit.wh.Algorithms.HeldKarpAlgorithm;
import wit.wh.pointUtils.Point;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestHeldKarpAlgorithm {

    HeldKarpAlgorithm heldKarpAlgorithm = new HeldKarpAlgorithm();
    Random random = new Random(42); // Seeded for reproducibility

    /**
     * Generate a grid of random points.
     */
    private ArrayList<Point> generateRandomPoints(int count, int bound) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(bound);
            int y = random.nextInt(bound);
            points.add(new Point(x, y));
        }
        return points;
    }

    @Test
    public void testBasicSquarePath() {
        ArrayList<Point> points = new ArrayList<>(List.of(
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 1)
        ));

        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);

        assertEquals(points.get(0), result.get(0));
        assertEquals(points.get(0), result.get(result.size() - 1));
        assertEquals(points.size() + 1, result.size());

        for (Point p : points) {
            assertTrue("Path should include point: ", result.contains(p));
        }
    }

    @Test
    public void testSinglePoint() {
        ArrayList<Point> points = new ArrayList<>(List.of(new Point(0, 0)));

        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);

        assertEquals(1, result.size());
        assertEquals(points.get(0), result.get(0));
    }

    @Test
    public void testTwoPoints() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        ArrayList<Point> points = new ArrayList<>(List.of(p1, p2));

        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);

        assertEquals(3, result.size());
        assertEquals(p1, result.get(0));
        assertEquals(p1, result.get(2));
        assertTrue(result.contains(p2));
    }

    @Test
    public void testEmptyList() {
        ArrayList<Point> points = new ArrayList<>();
        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);
        assertTrue( "Empty input should return an empty or trivial result.", result.isEmpty() || result.size() <= 1);
    }

    @Test
    public void testNoDuplicatesExceptStartEnd() {
        ArrayList<Point> points = new ArrayList<>(List.of(
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 1)
        ));

        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);

        Set<Point> seen = new HashSet<>();
        for (int i = 1; i < result.size() - 1; i++) {
            assertTrue("Point should not be repeated in the middle of the path.", seen.add(result.get(i)));
        }
    }

    @Test
    public void testTourFormsCycle() {
        ArrayList<Point> points = new ArrayList<>(List.of(
                new Point(5, 5),
                new Point(6, 5),
                new Point(6, 6),
                new Point(5, 6)
        ));

        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);

        assertEquals(result.get(0), result.get(result.size() - 1));
    }

    @Test
    public void testSymmetricDistance() {
        ArrayList<Point> points = new ArrayList<>(List.of(
                new Point(0, 0),   // A
                new Point(2, 0),   // B
                new Point(2, 2),   // C
                new Point(0, 2)    // D
        ));

        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);

        // Known optimal path: A → B → C → D → A = 8.0
        double totalDist = 0;
        for (int i = 0; i < result.size() - 1; i++) {
            totalDist += result.get(i).distance(result.get(i + 1));
        }

        assertEquals(8.0, totalDist, 1e-6);
    }


    @Test
    public void test10PointsTour() {
        int count = 10;
        ArrayList<Point> points = generateRandomPoints(count, 100);
        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);

        assertEquals(count + 1, result.size());
        assertEquals(result.get(0), result.get(result.size() - 1));

        Set<Point> visited = new HashSet<>(result.subList(0, result.size() - 1));
        assertEquals(count, visited.size());

        for (Point p : points) {
            assertTrue("Tour must include original point: " + p, result.contains(p));
        }
    }

    @Test
    public void test15PointsTour() {
        final int count = 15;
        final int bound = 50;
        ArrayList<Point> points = generateRandomPoints(count, bound);
        long start = System.currentTimeMillis();
        ArrayList<Point> result = heldKarpAlgorithm.getTSPSolution(points);
        long duration = System.currentTimeMillis() - start;

        assertEquals(count + 1, result.size());
        assertEquals(result.get(0), result.get(result.size() - 1));
        assertTrue("Computation should finish within 10 seconds.", duration < 10_000);

        Set<Point> visited = new HashSet<>(result.subList(0, result.size() - 1));
        assertEquals(count, visited.size());
    }
}
