package wit.wh.algorithms;

public abstract class AlgoritmParameters {
    private int numberOfIterations;

    public AlgoritmParameters(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public AlgoritmParameters() {}

    /**
     * Returns the total number of iterations.
     *
     * @return number of iterations
     */
    public int numberOfIterations() {
        return numberOfIterations;
    }
}
