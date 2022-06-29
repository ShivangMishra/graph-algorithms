package main.java.graph.algorithms.pathfind;

import main.java.graph.Graph;

import java.util.Collection;
import java.util.List;

public interface PathFindAlgorithm<V> {
    void findPath(Graph<V> graph, V source, V destination);

    boolean isRunning();

    Collection<V> getOpen();

    Collection<V> getClosed();

    List<V> path();

    V current();

    List<V> currentPath();
}
