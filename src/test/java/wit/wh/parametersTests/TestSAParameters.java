package wit.wh.parametersTests;

import wit.wh.algorithms.SAAlgorithm.SAParameters;
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
    static final int[] pointCounts = {5, 7, 10, 12, MAX_POINT_COUNT};
    static final double[] startingTemperatures = {5, 10, 20, 25, 50};
    static final double[] coolingRates = {0.99, 0.995, 0.998, 0.999, 0.9995};
    static final int[] iterationsOptions = {100, 500, 1000, 2000, 5000};
    static final double[] stepRates = {0.1, 0.05, 0.01, 0.005, 0.001};
    static final double[] stoppingTemperatures = {0.1, 0.5, 0.1, 0.01, 0.001}; // stałe

    final static String fileTitle = "Points\tStartTemp\tStopTemp\tCoolingRate\tIterations\tStepRate\tInitialDistance\tResultDistance";

    public static void main(String[] args) {
        ArrayList<Point> randomPoints = PointUtils.generateRandomPoints(MAX_POINT_COUNT);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SATest.txt"))) {
            writeTitleToFile(writer);

            //int pointsCount = 10;
            for (int pointsCount : pointCounts) {
                ArrayList<Point> points = new ArrayList<>(randomPoints.subList(0, pointsCount));

                double startDistance = PathUtils.getRouteLength(points);

                for (double startTemp : startingTemperatures) {
                    for (double stopTemp : stoppingTemperatures) {
                        for (double cooling : coolingRates) {
                            for (int iterations : iterationsOptions) {
                                for (double stepRate : stepRates) {
                                    double totalDistance = 0;
                                    for (int run = 0; run < TEST_RUNS_PER_CONFIG; run++) {
                                        SAParameters params = new SAParameters(
                                                startTemp, stopTemp, iterations, cooling, stepRate
                                        );
                                        double distance = PathUtils.getRouteLength(
                                                TSPSolutionFactory.createSolutionWithParams(
                                                        SolutionType.SA_ALGORITHM, points, params)
                                        );
                                        totalDistance += distance;
                                    }
                                    double avgDistance = totalDistance / TEST_RUNS_PER_CONFIG;

                                    writeToFile(pointsCount, startTemp, stopTemp, cooling, iterations, stepRate, startDistance, avgDistance, writer);
                                }
                            }
                        }
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

    private static void writeToFile(int pointsCount, double startTemp, double stopTemp, double cooling, int iterations, double stepRate, double startDistance, double avgDistance, BufferedWriter writer) throws IOException {
        String row = pointsCount + "\t" +
                startTemp + '\t' +
                stopTemp + '\t' +
                cooling + '\t' +
                iterations + '\t' +
                stepRate + '\t' +
                startDistance + "\t" +
                avgDistance;

        writer.write(row);
        writer.newLine();
    }
}