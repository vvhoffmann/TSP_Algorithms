package wit.wh.algorithms;

public enum SolutionType {
    HELD_KARP_ALGORITHM("Held Karp Solution"),
    NEAREST_NEIGHBOUR_ALGORITHM("Nearest Neighbour Solution"),
    REPETITIVE_NEAREST_NEIGHBOUR_ALGORITHM("Repetitive Nearest Neighbour Solution"),
    SA_ALGORITHM("SA Solution"),
    ANT_COLONY_OPTIMIZATION_ALGORITHM("Ant Colony Optimization Solution"),
    QUASI_OPTIMIZATION_ALGORITHM("Quasi Optimization Solution");

    final String name;

    SolutionType(String name) {
        this.name = name;
    }
}
