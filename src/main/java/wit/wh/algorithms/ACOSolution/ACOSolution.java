package wit.wh.algorithms.ACOSolution;

import wit.wh.algorithms.SolutionType;
import wit.wh.algorithms.TSPSolution;
import wit.wh.algorithms.TSPSolutionFactory;
import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.util.*;

/**
 * Ant Colony Optimization algorithm (ACO) for solving the Traveling Salesman Problem (TSP).
 * Inspired by:
 * <ul>
 *   <li><a href="https://github.com/RonitRay/Ant-Colony-Optimization">...</a></li>
 *   <li>https://www.baeldung.com/java-ant-colony-optimization</li>
 * </ul>
 */
public class ACOSolution extends TSPSolution {

    private int numberOfPoints;
    private int numberOfAnts;
    private double[][] graph;
    private double[][] trails;
    private List<Ant> ants;
    private double[] probabilities;

    private int currentIndex;
    private int[] bestTourOrder;
    private double bestTourLength;

    /**
     * Constructor with algorithm parameters.
     *
     * @param inputPoints     list of input points
     * @param parameters      parameters for ant colony optimization
     */
    public ACOSolution(ArrayList<Point> inputPoints, ACOParameters parameters) {
        super(inputPoints, parameters);
    }

    /**
     * Executes the ACO algorithm to solve the TSP.
     *
     * @return optimized route as list of points
     */
    @Override
    public ArrayList<Point> getTSPSolution() {
        initialize(inputPoints);
        int[] result = solve();

        return PathUtils.returnRoundPath(buildPointList(result));
    }
    
    /**
     * Initializes algorithm structures.
     */
    private void initialize(ArrayList<Point> inputPoints) {
        this.inputPoints = inputPoints;
        this.numberOfPoints = inputPoints.size();
        this.numberOfAnts = Math.max(inputPoints.size(), 10);
        this.graph = generateDistanceMatrix(inputPoints);
        this.trails = new double[numberOfPoints][numberOfPoints];
        this.probabilities = new double[numberOfPoints];
        this.ants = new ArrayList<>();

        for (int i = 0; i < numberOfAnts; i++)
            ants.add(new Ant(numberOfPoints));
    }

    /**
     * Generates the distance matrix between each pair of points.
     */
    private double[][] generateDistanceMatrix(ArrayList<Point> points) {
        int n = points.size();
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            Point pi = points.get(i);
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (i == j) ? 0 : PointUtils.distance(pi, points.get(j));
            }
        }
        return matrix;
    }

    /**
     * Converts a tour index array to a list of points.
     */
    private ArrayList<Point> buildPointList(int[] result) {
        ArrayList<Point> solution = new ArrayList<>();
        for (int cityIndex : result) {
            solution.add(inputPoints.get(cityIndex));
        }
        return solution;
    }

    /**
     * Main solver method for TSP using ACO.
     */
    public int[] solve() {
        resetAnts();
        initializeTrailsWithPheromoneLevel(inputPoints);

        for (int i = 0; i < parameters().numberOfIterations(); i++) {
            moveAnts();
            updatePheromones();
            updateBestSolution();
        }

        return bestTourOrder;
    }

    /**
     * Resets all ants and chooses random starting cities.
     */
    private void resetAnts() {
        currentIndex = 0;
        for (Ant ant : ants) {
            ant.clearVisited();
            ant.visitCity(-1, PathUtils.RANDOM.nextInt(numberOfPoints));
        }
    }

    /**
     * Initializes all pheromone trails to starting pheromone level by NN algorithm
     */
    private void initializeTrailsWithPheromoneLevel(ArrayList<Point> points) {
        double cnn = getCnn(points);
        double startingPheromoneLevel = 1.0 / (numberOfPoints*cnn);

        for (int i = 0; i < numberOfPoints; i++)
            Arrays.fill(trails[i], startingPheromoneLevel);

    }

    private static double getCnn(ArrayList<Point> points) {
        return PathUtils.getRouteLength(TSPSolutionFactory.createSolution(SolutionType.NEAREST_NEIGHBOUR_ALGORITHM, points));
    }

    /**
     * Moves each ant step-by-step through the graph.
     */
    private void moveAnts() {
        for (; currentIndex < numberOfPoints - 1; currentIndex++) {
            for (Ant ant : ants) {
                int nextCity = selectNextCity(ant);
                ant.visitCity(currentIndex, nextCity);
            }
        }
    }

    /**
     * Selects the next city for the ant to visit.
     */
    private int selectNextCity(Ant ant) {
        if (PathUtils.RANDOM.nextDouble() < ((ACOParameters) parameters()).randomFactor()) {
            return getRandomUnvisitedCity(ant);
        }

        calculateTransitionProbabilities(ant);
        double r = PathUtils.RANDOM.nextDouble();
        double total = 0;

        for (int i = 0; i < numberOfPoints; i++) {
            total += probabilities[i];
            if (total >= r) {
                return i;
            }
        }

        throw new IllegalStateException("No next city could be selected.");
    }

    /**
     * Selects a random unvisited city for the ant.
     */
    private int getRandomUnvisitedCity(Ant ant) {
        List<Integer> unvisited = new ArrayList<>();
        for (int i = 0; i < numberOfPoints; i++) {
            if (!ant.hasVisited(i)) {
                unvisited.add(i);
            }
        }
        return unvisited.get(PathUtils.RANDOM.nextInt(unvisited.size()));
    }

    /**
     * Calculates probabilities for transitioning to each unvisited city.
     */
    private void calculateTransitionProbabilities(Ant ant) {
        int currentCity = ant.trail[currentIndex];
        double sum = 0.0;

        for (int j = 0; j < numberOfPoints; j++) {
            if (!ant.hasVisited(j)) {
                double pheromone = Math.pow(trails[currentCity][j], ((ACOParameters) parameters()).alpha());
                double distance = Math.pow(1.0 / graph[currentCity][j], ((ACOParameters) parameters()).beta());
                probabilities[j] = pheromone * distance;
                sum += probabilities[j];
            } else {
                probabilities[j] = 0.0;
            }
        }

        if (sum > 0) {
            for (int i = 0; i < numberOfPoints; i++) {
                probabilities[i] /= sum;
            }
        }
    }

    /**
     * Updates pheromone trails after all ants complete their tours.
     */
    private void updatePheromones() {
        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < numberOfPoints; j++) {
                trails[i][j] *= ((ACOParameters) parameters()).evaporation();
            }
        }

        for (Ant ant : ants) {
            double contribution = ((ACOParameters) parameters()).Q() / ant.getTrailLength(graph);
            for (int i = 0; i < numberOfPoints - 1; i++) {
                trails[ant.trail[i]][ant.trail[i + 1]] += contribution;
            }
            trails[ant.trail[numberOfPoints - 1]][ant.trail[0]] += contribution;
        }
    }

    /**
     * Updates the best tour if a better one is found.
     */
    private void updateBestSolution() {
        for (Ant ant : ants) {
            double length = ant.getTrailLength(graph);
            if (bestTourOrder == null || length < bestTourLength) {
                bestTourLength = length;
                bestTourOrder = ant.trail.clone();
            }
        }
    }
}