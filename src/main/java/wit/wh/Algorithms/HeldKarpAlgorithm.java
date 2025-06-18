package wit.wh.Algorithms;

import wit.wh.TSPSolution;
import wit.wh.pointUtils.Point;
import wit.wh.pointUtils.PointUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Implements the Held-Karp algorithm (dynamic programming) for solving the Traveling Salesman Problem (TSP).
 * Source: <a href="https://compgeek.co.in/held-karp-algorithm-for-tsp/">...</a>
 */
public class HeldKarpAlgorithm extends TSPSolution {

    private static final double INF = Integer.MAX_VALUE;

    /**
     * Solves the TSP using the Held-Karp algorithm.
     *
     * @param entryPointList List of points.
     * @return Optimal path as a list of points (including return to start).
     */
    public ArrayList<Point> getTSPSolution(ArrayList<Point> entryPointList) {
        int n = entryPointList.size();
        if(entryPointList.size() < 2)
            return entryPointList;

        int N = 1 << n;
        double[][] dp = initializeDPTable(N, n);
        int[][] parent = new int[N][n];
        double[][] dist = PointUtils.getDistanceArray(entryPointList);

        fillDPTable(dp, parent, dist, n, N);
        int lastCity = findLastCity(dp, dist, n, N);
        ArrayList<Point> solutionPath = reconstructPath(parent, N - 1, lastCity, entryPointList);
        solutionPath.add(entryPointList.get(0)); // Return to start

        return solutionPath;
    }

    /**
     * Initializes the DP table with infinite values, except the starting point.
     *
     * @param N Total number of subsets, 2^n
     * @param n Number of points
     * @return Initialized DP table
     */
    private double[][] initializeDPTable(int N, int n) {
        double[][] dp = new double[N][n];
        for (double[] row : dp) Arrays.fill(row, INF);
        dp[1][0] = 0; // Start from city 0
        return dp;
    }

    /**
     * Fills the DP and parent tables based on all subsets of points.
     *
     * @param dp     DP cost table
     * @param parent Table to reconstruct path
     * @param dist   Distance matrix
     * @param n      Number of points
     * @param N      Total number of subsets, 2^n
     */
    private void fillDPTable(double[][] dp, int[][] parent, double[][] dist, int n, int N) {
        for (int subset = 1; subset < N; subset++) {
            if ((subset & 1) == 0) continue;

            for (int j = 1; j < n; j++) {
                if ((subset & (1 << j)) == 0) continue;

                int prevSubset = subset ^ (1 << j);
                double minCost = INF;
                int bestK = -1;

                for (int k = 0; k < n; k++) {
                    if ((prevSubset & (1 << k)) != 0) {
                        double newCost = dp[prevSubset][k] + dist[k][j];
                        if (newCost < minCost) {
                            minCost = newCost;
                            bestK = k;
                        }
                    }
                }

                dp[subset][j] = minCost;
                parent[subset][j] = bestK;
            }
        }
    }

    /**
     * Finds the last point that provides the minimum cost to return to the starting point.
     *
     * @param dp   DP table
     * @param dist Distance matrix
     * @param n    Number of points
     * @param N    Total number of subsets, 2^n
     * @return Index of the last point in the optimal path
     */
    private int findLastCity(double[][] dp, double[][] dist, int n, int N) {
        int fullSet = N - 1;
        double minTourCost = INF;
        int lastCity = -1;

        for (int j = 1; j < n; j++) {
            double cost = dp[fullSet][j] + dist[j][0];
            if (cost < minTourCost) {
                minTourCost = cost;
                lastCity = j;
            }
        }

        return lastCity;
    }

    /**
     * Reconstructs the optimal path from the parent table.
     *
     * @param parent Parent table
     * @param subset Current subset
     * @param last   Last point index
     * @param points List of points
     * @return Ordered list of points representing the optimal path
     */
    private static ArrayList<Point> reconstructPath(int[][] parent, int subset, int last, ArrayList<Point> points) {
        ArrayList<Point> path = new ArrayList<>();
        while (last != 0) {
            path.add(points.get(last));
            int prevSubset = subset ^ (1 << last);
            last = parent[subset][last];
            subset = prevSubset;
        }
        path.add(points.get(0));
        Collections.reverse(path);
        return path;
    }
}