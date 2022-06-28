package main.java.graph;

import java.util.*;

public class Graph<V> {
    private final HashSet<V> vertices;
    private final HashMap<V, HashSet<V>> adjacencyMap;
    private final HashMap<Edge<V>, Double> edgeWeights;

    public Graph() {
        vertices = new HashSet<>();
        adjacencyMap = new HashMap<>();
        edgeWeights = new HashMap<>();
    }

    public Collection<V> vertices() {
        return Collections.unmodifiableCollection(vertices);
    }

    public List<Edge<V>> edges() {
        ArrayList<Edge<V>> edges = new ArrayList<>();
        HashSet<V> nodesDone = new HashSet<>();

        vertices().forEach(v1 -> {
            neighbours(v1).forEach(v2 -> {
                if (!nodesDone.contains(v2))
                    edges.add(new Edge<>(v1, v2));
            });
            nodesDone.add(v1);
        });
        return edges;
    }

    public Iterable<V> neighbours(V v) {
        return adjacencyMap.get(v);
    }

    public void addVertex(V v) {
        if (vertices.add(v))
            adjacencyMap.put(v, new HashSet<>());
    }

    public double edgeWeight(V v1, V v2) {
        return edgeWeights.get(new Edge<>(v1, v2));
    }

    public void addEdge(V v1, V v2, double weight) {

        if (!adjacencyMap.containsKey(v1) || !adjacencyMap.containsKey(v2))
            return;
        System.out.println("Adding edge...");
        edgeWeights.put(new Edge<>(v1, v2), weight);
        adjacencyMap.get(v2).add(v1);
        adjacencyMap.get(v1).add(v2);
    }

    public boolean adjacent(V v1, V v2) {
        return adjacencyMap.get(v1).contains(v2);
    }

    public int degree(V v) {
        return adjacencyMap.get(v).size();
    }

    public int nVertices() {
        return vertices.size();
    }
}
