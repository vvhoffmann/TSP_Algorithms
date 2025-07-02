package wit.wh.algorithms.ACOSolution;

import wit.wh.algorithms.Parameters;

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
public class ACOParameters extends Parameters {

    /** Importance of pheromone concentration (α). */
    private double alpha = 1.0;

    /** Importance of heuristic desirability / distance (β). */
    private double beta = 5.0;

    /** Pheromone evaporation rate per iteration. */
    private double evaporation = 0.1;

    /** Amount of pheromone deposited by each ant. */
    private double Q = 100;

    /** Degree of randomness in ant decision-making. */
    private double randomFactor = 0.01;

    /**
     * Constructs a default set of parameters for the ACO algorithm.
     */
    public ACOParameters() {
        super(100);
    }

    /**
     * Constructs a custom configuration for the ACO algorithm.
     *
     * @param alpha         pheromone importance
     * @param beta          distance/heuristic importance
     * @param evaporation   rate at which pheromones evaporate
     * @param Q             amount of pheromone deposited
     * @param randomFactor  randomness in path selection
     * @param maxIterations maximum number of iterations
     */
    public ACOParameters(double alpha, double beta, double evaporation, double Q, double randomFactor, int maxIterations) {
        super(maxIterations);
        this.alpha = alpha;
        this.beta = beta;
        this.evaporation = evaporation;
        this.Q = Q;
        this.randomFactor = randomFactor;
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
    public double randomFactor() {
        return randomFactor;
    }

    /**
     * Returns the maximum number of iterations for the algorithm.
     *
     * @return maximum iterations
     */
}