package wit.wh.algorithms;

import wit.wh.utils.PathUtils;
import wit.wh.utils.Point;
import wit.wh.utils.PointUtils;

import java.util.ArrayList;

/**
 * Abstract base class representing a solution to the Traveling Salesman Problem (TSP).
 * <p>
 * Subclasses must implement the {@code getTSPSolution()} method to define
 * a specific algorithm for solving the TSP.
 * </p>
 */
public abstract class TSPSolution {
    /**
     * The parameters used for algorithm configuration (optional, can be null).
     */
    protected AlgoritmParameters parameters = null;

    /**
     * The computed list of points forming the TSP solution path.
     */
    protected ArrayList<Point> solutionPoints;

    /**
     * The original list of input points provided to the algorithm.
     */
    public ArrayList<Point> inputPoints;

    /**
     * The total route length (distance) of the computed solution path.
     */
    protected double routeLength;

    /**
     * Constructs a TSP solution with custom algorithm parameters.
     *
     * @param inputPoints the input list of points representing cities
     * @param parameters  the parameters used to configure the solving algorithm
     */
    public TSPSolution(ArrayList<Point> inputPoints, AlgoritmParameters parameters) {
        this.inputPoints = inputPoints;
        this.parameters = parameters;
        this.solutionPoints = setSolutionPoints();
        this.routeLength = calculateRouteLength();
    }

    /**
     * Constructs a TSP solution without any algorithm parameters.
     *
     * @param inputPoints the input list of points representing cities
     */
    public TSPSolution(ArrayList<Point> inputPoints) {
        this.inputPoints = inputPoints;
        this.solutionPoints = setSolutionPoints();
        this.routeLength = calculateRouteLength();
    }

    /**
     * Abstract method that must be implemented by subclasses to provide
     * a specific TSP-solving algorithm.
     *
     * @return the ordered list of points representing the computed TSP path
     */
    protected abstract ArrayList<Point> getTSPSolution();

    /**
     * Initializes the solution points by either returning an empty list,
     * the input points (if not enough to run the algorithm), or computing
     * the TSP solution.
     *
     * @return the computed or fallback list of solution points
     */
    protected ArrayList<Point> setSolutionPoints() {
        if (inputPoints == null)
            return new ArrayList<>();

        if (inputPoints.size() < PathUtils.MIN_POINTS_TO_RUN_ALGORITHM)
            return inputPoints;

        return getTSPSolution();
    }

    /**
     * Calculates the total distance of the computed TSP route.
     *
     * @return the total route length
     */
    protected double calculateRouteLength() {
        double route = 0;
        for (int i = 0; i < solutionPoints.size(); i++) {
            if (i == solutionPoints.size() - 1)
                route += PointUtils.distance(solutionPoints.get(i), solutionPoints.get(0));
            else
                route += PointUtils.distance(solutionPoints.get(i), solutionPoints.get(i + 1));
        }
        return route;
    }

    /**
     * Returns the computed list of solution points forming the TSP path.
     *
     * @return the TSP solution as an ordered list of points
     */
    public ArrayList<Point> getSolutionPoints() {
        return solutionPoints;
    }

    /**
     * Returns the total length of the computed route.
     *
     * @return the total route length
     */
    public double getRouteLength() {
        return routeLength;
    }

    /**
     * Returns the algorithm parameters used for this solution (if any).
     *
     * @return the algorithm parameters or null if none were used
     */
    public AlgoritmParameters parameters() {
        return parameters;
    }
}
