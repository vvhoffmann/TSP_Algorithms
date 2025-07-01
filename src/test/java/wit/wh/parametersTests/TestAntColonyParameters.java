package wit.wh.parametersTests;

import wit.wh.algorithms.ACOAlgorithm.ACOParameters;
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
    static final int TEST_RUNS_PER_CONFIG = 3;
    static final int MAX_POINT_COUNT = 15;
    static final int[] pointCounts = {5, 7, 10, 12, MAX_POINT_COUNT};

    // Zakres testowanych parametrów
    static final double[] initialPheromoneLevels = {0.01, 1.0, 10.0};
    static final double[] alphas = {0.2, 0.4, 0.6, 0.8, 1};
    static final double[] betas = {2, 4, 6, 8, 10, 20};
    static final double[] evaporations = {0.1, 0.3, 0.4, 0.5, 0.7};
    static final double[] Qs = {100, 300, 500};
    static final double[] antFactors = {0.3, 0.5, 0.8};
    static final double[] randomFactors = {0.0, 0.01, 0.05};
    static final int[] iterationsOptions = {200, 500, 1000};

    static final String fileTitle = "Points\tInitialPheromone Levels\tAlpha\tBeta\tEvaporation\tQ\tAntFactor\tRandomFactor\tIterations\t\tInitialDistanceResultDistance";

    public static void main(String[] args) {
        ArrayList<Point> randomPoints = PointUtils.generateRandomPoints(MAX_POINT_COUNT);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ACOTest.txt"))) {
            writeTitleToFile(writer);

            for (int pointsCount : pointCounts) {
                System.out.println("Testing with points: " + pointsCount);
                ArrayList<Point> points = new ArrayList<>(randomPoints.subList(0, pointsCount));

                for (Point point : points) {
                    System.out.println("inputPoints.add(new Point(" + point.x + ", " + point.y + "));");
                }

                double startDistance = PathUtils.getRouteLength(points);

                for (double initialPheromoneLevel : initialPheromoneLevels) {

                    for (double alpha : alphas) {
                        for (double beta : betas) {
                            for (double evaporation : evaporations) {
                                for (double Q : Qs) {
                                    for (double antFactor : antFactors) {
                                        for (double randomFactor : randomFactors) {
                                            for (int iterations : iterationsOptions) {

                                                double totalDistance = 0;
                                                for (int run = 0; run < TEST_RUNS_PER_CONFIG; run++) {
                                                    ACOParameters params = new ACOParameters(initialPheromoneLevel, alpha, beta, evaporation, Q, antFactor, randomFactor, iterations);

                                                    double distance = PathUtils.getRouteLength(
                                                            TSPSolutionFactory.createSolutionWithParams(
                                                                    SolutionType.ACO_ALGORITHM,
                                                                    points,
                                                                    params
                                                            )
                                                    );
                                                    totalDistance += distance;
                                                }
                                                double avgDistance = totalDistance / TEST_RUNS_PER_CONFIG;

                                                writeToFile(pointsCount, initialPheromoneLevel, alpha, beta, evaporation, Q, antFactor, randomFactor, iterations, startDistance, avgDistance, writer);
                                            }
                                        }
                                    }
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

    private static void writeToFile(int pointsCount, double initialPheromoneLevel, double alpha, double beta, double evaporation, double Q, double antFactor, double randomFactor, int iterations, double startDistance, double avgDistance, BufferedWriter writer) throws IOException {
        String row = pointsCount + "\t" +
                initialPheromoneLevel + "\t" +
                alpha + "\t" +
                beta + "\t" +
                evaporation + "\t" +
                Q + "\t" +
                antFactor + "\t" +
                randomFactor + "\t" +
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
