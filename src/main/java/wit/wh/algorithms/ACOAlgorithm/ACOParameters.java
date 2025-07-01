package wit.wh.algorithms.ACOAlgorithm;

import wit.wh.algorithms.AlgoritmParameters;

/**
 * Represents the configuration parameters for the Ant Colony Optimization (ACO) algorithm.
 * <p>
 * These parameters define the behavior of the colony, including pheromone influence,
 * distance preference, evaporation rate, and ant distribution.
 * </p>
 *
 * <p>
 * Source inspiration:
 * <ul>
 *     <li><a href="https://github.com/RonitRay/Ant-Colony-Optimization">...</a></li>
 *     <li>https://www.baeldung.com/java-ant-colony-optimization</li>
 * </ul>
 * </p>
 */
public class ACOParameters extends AlgoritmParameters {

    /** Constant scaling factor for pheromone trail matrix. */
    private double initialPheromoneLevel = 1.0;

    /** Importance of pheromone concentration (α). */
    private double alpha = 1;

    /** Importance of heuristic desirability / distance (β). */
    private double beta = 5;

    /** Pheromone evaporation rate per iteration. */
    private double evaporation = 0.5;

    /** Amount of pheromone deposited by each ant. */
    private double Q = 500;

    /** Proportion of ants to nodes in the graph. */
    private double antFactor = 0.8;

    /** Degree of randomness in ant decision-making. */
    private double randomFactor = 0.01;

    /** Maximum number of iterations the algorithm will execute. */
    //private int numberOfIterations = 500;

    /**
     * Constructs a default set of parameters for the ACO algorithm.
     */
    public ACOParameters() {
        super(500);
    }

    /**
     * Constructs a custom configuration for the ACO algorithm.
     *
     * @param initialPheromoneLevel             constant scaling factor for trails
     * @param alpha         pheromone importance
     * @param beta          distance/heuristic importance
     * @param evaporation   rate at which pheromones evaporate
     * @param Q             amount of pheromone deposited
     * @param antFactor     number of ants relative to problem size
     * @param randomFactor  randomness in path selection
     * @param maxIterations maximum number of iterations
     */
    public ACOParameters(double initialPheromoneLevel, double alpha, double beta, double evaporation, double Q,
                         double antFactor, double randomFactor, int maxIterations) {
        super(maxIterations);
        this.initialPheromoneLevel = initialPheromoneLevel;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporation = evaporation;
        this.Q = Q;
        this.antFactor = antFactor;
        this.randomFactor = randomFactor;
    }

    /**
     * Returns the constant scaling factor used for trail initialization.
     *
     * @return the trail constant
     */
    public double initialPheromoneLevel() {
        return initialPheromoneLevel;
    }

    /**
     * Returns the importance of pheromone in path selection (α).
     *
     * @return pheromone importance
     */
    public double alpha() {
        return alpha;
    }

    /**
     * Returns the importance of distance (heuristic value) in path selection (β).
     *
     * @return heuristic importance
     */
    public double beta() {
        return beta;
    }

    /**
     * Returns the rate of pheromone evaporation.
     *
     * @return evaporation rate
     */
    public double evaporation() {
        return evaporation;
    }

    /**
     * Returns the amount of pheromone left on a trail by an ant.
     *
     * @return pheromone deposit amount
     */
    public double Q() {
        return Q;
    }

    /**
     * Returns the ratio of ants to nodes.
     *
     * @return ant population factor
     */
    public double antFactor() {
        return antFactor;
    }

    /**
     * Returns the random factor used to diversify ant path choices.
     *
     * @return randomness factor
     */
    public double randomFactor() {
        return randomFactor;
    }

    /**
     * Returns the maximum number of iterations for the algorithm.
     *
     * @return maximum iterations
     */
}