package wit.wh.algorithms;

public enum SolutionType {
    HELD_KARP_ALGORITHM("Algorytm Helda Karpa"),
    NEAREST_NEIGHBOUR_ALGORITHM("Algorytm Najbliższego Sąsiada"),
    REPETITIVE_NEAREST_NEIGHBOUR_ALGORITHM("Algorytm RNN"),
    SA_ALGORITHM("Algorytm SA"),
    ACO_ALGORITHM("Algorytm mrówkowy"),
    QUASI_OPTIMIZATION_ALGORITHM("Algorytm Quasi-Optymalny");

    final String name;

    SolutionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
