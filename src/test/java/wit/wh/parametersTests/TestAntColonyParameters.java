package wit.wh.parametersTests;

import wit.wh.algorithms.ACOSolution.ACOParameters;
import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestAntColonyParameters {
    static final int RUNS_PER_CONFIG = 3;
    static final int MAX_POINT_COUNT = 15;
    static final int[] pointCounts = {5, 10, MAX_POINT_COUNT};

    // Zakres testowanych parametrów
    static final double[] alphas = {0.5, 1, 2.5, 5};
    static final double[] betas = {5.0, 4.0, 2.5, 1.0};
    static final double[] evaporations = {0.1, 0.3, 0.5, 0.7};
    static final double Q = 100;
    static final double randomFactor = 0.01;
    static final int[] iterationsOptions = {100, 250, 500};

    static final String fileTitle = "Points\tAlpha\tBeta\tEvaporation\tIterations\tInitialDistance\tResultDistance";

    public static void main(String[] args) {
        ArrayList<Point> randomPoints = PointUtils.generateRandomPoints(MAX_POINT_COUNT);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ACOTest.txt"))) {
            writeTitleToFile(writer);

            for (int pointsCount : pointCounts) {
                ArrayList<Point> points = new ArrayList<>(randomPoints.subList(0, pointsCount));

                double startDistance = PathUtils.getRouteLength(points);

                for (double alpha : alphas) {
                    for (double beta : betas) {
                        for (double evaporation : evaporations) {
                            for (int iterations : iterationsOptions) {
                                double totalDistance = 0;
                                for (int run = 0; run < RUNS_PER_CONFIG; run++) {
                                    ACOParameters params = new ACOParameters(alpha, beta, evaporation, Q, randomFactor, iterations);

                                    double distance = PathUtils.getRouteLength(
                                            TSPSolutionFactory.createSolutionWithParams(
                                                    SolutionType.ACO_ALGORITHM,
                                                    points,
                                                    params
                                            )
                                    );
                                    totalDistance += distance;
                                }
                                double avgDistance = totalDistance / RUNS_PER_CONFIG;

                                writeToFile(pointsCount, alpha, beta, evaporation, iterations, startDistance, avgDistance, writer);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(int pointsCount, double alpha, double beta, double evaporation, int iterations, double startDistance, double avgDistance, BufferedWriter writer) throws IOException {
        String row = pointsCount + "\t" +
                alpha + "\t" +
                beta + "\t" +
                evaporation + "\t" +
                iterations + "\t" +
                startDistance + "\t" +
                avgDistance;

        writer.write(row);
        writer.newLine();
    }

    private static void writeTitleToFile(BufferedWriter writer) throws IOException {
        writer.write(fileTitle);
        writer.newLine();
    }

}
