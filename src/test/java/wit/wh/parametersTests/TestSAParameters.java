package wit.wh.parametersTests;

import wit.wh.algorithms.SASolution.SAParameters;
import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestSAParameters {
    // liczba powtórzeń dla średniej
    static final int TEST_RUNS_PER_CONFIG = 3;
    // Definiujemy zakres parametrów do testów
    static final int MAX_POINT_COUNT = 15;
    static final int[] pointCounts = {5, 10, MAX_POINT_COUNT};
    static final int[] iterationsOptions = {3000, 500, 1000, 2000, 5000};
    static final double[] coolingRates = {0.9, 0.95 , 0.99};
    static final double stepRate = 0.8;
    static final double startingTemp = 40;
    static final double stoppingTemp = 0.001;


    final static String fileTitle = "Points\tIterations\tCoolingRate\tInitialDistance\tResultDistance";

    public static void main(String[] args) {
        ArrayList<Point> randomPoints = PointUtils.generateRandomPoints(MAX_POINT_COUNT);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SATest.txt"))) {
            writeTitleToFile(writer);

            for (int pointsCount : pointCounts) {
                ArrayList<Point> points = new ArrayList<>(randomPoints.subList(0, pointsCount));

                double startDistance = PathUtils.getRouteLength(points);
                for (int iterations : iterationsOptions) {
                    for (double coolingRate : coolingRates) {
                        double totalDistance = 0;
                        for (int run = 0; run < TEST_RUNS_PER_CONFIG; run++) {
                            SAParameters params = new SAParameters(
                                    startingTemp, stoppingTemp, iterations, coolingRate, stepRate
                            );
                            double distance = PathUtils.getRouteLength(
                                    TSPSolutionFactory.createSolutionWithParams(
                                            SolutionType.SA_ALGORITHM, points, params)
                            );
                            totalDistance += distance;
                        }
                        double avgDistance = totalDistance / TEST_RUNS_PER_CONFIG;

                        writeToFile(pointsCount, iterations, coolingRate, startDistance, avgDistance, writer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeTitleToFile(BufferedWriter writer) throws IOException {
        writer.write(fileTitle);
        writer.newLine();
    }

    private static void writeToFile(int pointsCount, int iterations, double coolingRate, double startDistance, double avgDistance, BufferedWriter writer) throws IOException {
        String row = pointsCount + "\t" +
                iterations + '\t' +
                coolingRate + '\t' +
                startDistance + "\t" +
                avgDistance;

        writer.write(row);
        writer.newLine();
    }
}