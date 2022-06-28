package main.java.graph.algorithms.pathfind;

public interface PFObserver<V> {
    void init(PathFindAlgorithm<V> algo);
    void update();
    void done();
}
