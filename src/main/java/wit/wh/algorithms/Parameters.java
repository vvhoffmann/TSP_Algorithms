package wit.wh.algorithms;

public abstract class Parameters {
    private int numberOfIterations;

    public Parameters(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public Parameters() {}

    /**
     * Returns the total number of iterations.
     *
     * @return number of iterations
     */
    public int numberOfIterations() {
        return numberOfIterations;
    }
}
