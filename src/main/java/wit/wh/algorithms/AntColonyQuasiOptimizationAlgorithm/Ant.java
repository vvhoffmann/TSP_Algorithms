package wit.wh.algorithms.AntColonyQuasiOptimizationAlgorithm;

import java.util.Arrays;

/**
 * Represents a single ant used in the Ant Colony Optimization algorithm.
 * <p>
 * Each ant builds a tour by visiting cities, and tracks its visited cities and the order of the tour.
 * </p>
 */
public class Ant {

    /** Total number of cities in the tour. */
    protected int trailSize;

    /** Ordered list of cities visited by this ant. */
    protected int[] trail;

    /** Flags indicating whether each city has been visited. */
    protected boolean[] visited;

    /**
     * Constructs a new Ant that will visit a fixed number of cities.
     *
     * @param tourSize the number of cities in the tour
     */
    public Ant(int tourSize) {
        this.trailSize = tourSize;
        this.trail = new int[tourSize];
        this.visited = new boolean[tourSize];
    }

    protected void visitCity(int currentIndex, int city) 
    {
        trail[currentIndex + 1] = city; //add to trail
        visited[city] = true;           //update flag
    }

    /**
     * Checks whether the specified city has been visited by the ant.
     *
     * @param city the city index to check
     * @return {@code true} if the city has been visited, {@code false} otherwise
     */
    protected boolean hasVisited(int city) {
        return visited[city];
    }

    /**
     * Calculates the total length of the tour this ant has completed.
     * The tour is considered circular, ending back at the starting city.
     *
     * @param graph a 2D array representing the distances between cities
     * @return the total distance of the completed tour
     */
    protected double getTrailLength(double[][] graph) {
        double length = graph[trail[trailSize - 1]][trail[0]];

        for(int i= 1; i < trailSize; i++)
            length += graph[trail[i-1]][trail[i]];

        return length;
    }

    /**
     * Resets the visited cities for reuse in a new tour.
     */
    protected void clearVisited() {
        Arrays.fill(visited, false);
    }
}