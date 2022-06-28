package main.java.graph.algorithms.pathfind;

public interface Heuristic<V> {
    double getCost(V v1, V v2);
}
