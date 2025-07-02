package wit.wh.algorithms.SASolution;

import wit.wh.algorithms.AlgoritmParameters;

/**
 * Represents the configuration parameters for the Simulated Annealing algorithm.
 * <p>
 * These parameters control the behavior of the annealing process, including temperature
 * bounds, iteration count, cooling rate, and the probability of accepting worse solutions.
 * </p>
 */
public class SAParameters extends AlgoritmParameters {

    /** Initial temperature for the algorithm. Higher values allow more exploration. */
    private double startingTemperature = 10;

    /** Final temperature at which the algorithm stops. Lower values mean more exploitation. */
    private double stoppingTemperature = 0.1;

    /** Number of iterations the algorithm will perform. */
    //private int numberOfIterations = 500;

    /** Rate at which the temperature decreases after each iteration. */
    private double coolingRate = 0.995;

    /** A scaling factor affecting the probability of accepting worse solutions. */
    private double stepRate = 0.01;

    /**
     * Constructs a set of Simulated Annealing parameters with custom values.
     *
     * @param startingTemperature   the starting temperature
     * @param stoppingTemperature   the stopping temperature
     * @param numberOfIterations    number of iterations to perform
     * @param coolingRate           the cooling rate to apply per iteration
     * @param stepRate              the probability step rate for accepting worse solutions
     */
    public SAParameters(double startingTemperature, double stoppingTemperature,
                        int numberOfIterations, double coolingRate, double stepRate) {
        super(numberOfIterations);
        this.startingTemperature = startingTemperature;
        this.stoppingTemperature = stoppingTemperature;
        this.coolingRate = coolingRate;
        this.stepRate = stepRate;
    }

    /**
     * Constructs a default set of parameters with preset values.
     */
    public SAParameters() {
        super(500);
    }

    /**
     * Returns the starting temperature.
     *
     * @return starting temperature
     */
    public double startingTemperature() {
        return startingTemperature;
    }

    /**
     * Returns the stopping temperature.
     *
     * @return stopping temperature
     */
    public double stoppingTemperature() {
        return stoppingTemperature;
    }

    /**
     * Returns the cooling rate.
     *
     * @return cooling rate
     */
    public double coolingRate() {
        return coolingRate;
    }

    /**
     * Returns the step rate used for probability calculation in accepting worse solutions.
     *
     * @return step rate
     */
    public double stepRate() {
        return stepRate;
    }
}