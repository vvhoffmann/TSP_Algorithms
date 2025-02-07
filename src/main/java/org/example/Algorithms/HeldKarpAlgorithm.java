package org.example.Algorithms;

import org.example.Point;

import java.util.ArrayList;

public class HeldKarpAlgorithm {
    static final int INF = 999999;

    public static ArrayList<Point> getTSPSolution(ArrayList<Point> points) {
        int n = points.size();
        double[][] dist = getDistanceMatrix(points);

        // Liczba podzbiorów (2^n)
        int totalSubsets = 1 << n;

        // minDist table to store the minimum cost of visiting a subset of cities and ending at a specific city
        double[][] minDist = new double[totalSubsets][n];

// Initialize the DP table with infinity
        for (int i = 0; i < totalSubsets; i++) {
            for (int j = 0; j < n; j++) {
                minDist[i][j] = INF;
            }
        }

// Starting point, cost is 0 to start from the first city
        minDist[1][0] = 0;

// Iterate over all subsets of cities
        for (int mask = 1; mask < totalSubsets; mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) != 0) {
                    for (int v = 0; v < n; v++) {
                        if ((mask & (1 << v)) == 0) {
                            int newMask = mask | (1 << v);
                            minDist[newMask][v] = Math.min(minDist[newMask][v], minDist[mask][u] + dist[u][v]);
                        }
                    }
                }
            }
        }



// Find the minimum cost to complete the tour
        double minCost = INF;
        for (int i = 1; i < n; i++) {
            minCost = Math.min(minCost, minDist[totalSubsets - 1][i] + dist[i][0]);
        }

        //System.out.println("Minimum cost: " + minCost);
        //return minCost;

        return points;

    }

    private static double[][] getDistanceMatrix(ArrayList<Point> points) {
        double[][] dist = new double[points.size()][points.size()];
        for (int i = 0; i < points.size(); i++)
            for (int j = 0; j < points.size(); j++)
                dist[i][j] = Point.distance(points.get(i), points.get(j));

        return dist;
    }
}

