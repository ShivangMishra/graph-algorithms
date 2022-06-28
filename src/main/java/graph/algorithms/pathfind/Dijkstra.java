package main.java.graph.algorithms.pathfind;

import main.java.graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.Timer;

public class Dijkstra<V> implements PathFindAlgorithm<V> {

    private final PriorityQueue<V> open;
    private final HashSet<V> closed;
    private final HashMap<V, Double> g;
    private final HashMap<V, V> parent;

    private final ArrayList<PFObserver<V>> observers;

    public Dijkstra() {
        closed = new HashSet<>();
        g = new HashMap<>();
        open = new PriorityQueue<>(Comparator.comparing(g::get));
        parent = new HashMap<>();
        observers = new ArrayList<>();
    }

    public PriorityQueue<V> getOpen() {
        return open;
    }

    public HashSet<V> getClosed() {
        return closed;
    }

    public void addObserver(PFObserver<V> o) {
        observers.add(o);
        o.init(this);
    }

    @Override
    public void findPath(Graph<V> graph, V source, V destination) {
//        graph.vertices().forEach(vertex -> {
//            if (vertex.equals(source)) {
//                distance.put(vertex, 0.0);
//                return;
//            }
//            distance.put(vertex, Double.POSITIVE_INFINITY);
//            parent.put(vertex, null);
//            //p.add(vertex);
//        });
        System.out.println("finding path...");
        g.put(source, 0.0);
        open.add(source);
        t = new Timer(25, e -> {
            if (open.isEmpty()) {
                stop();
            }

            V u = open.poll();
            closed.add(u);
            assert u != null;
            if (u.equals(destination)) {
                path(source,destination);
                stop();
            }
            graph.neighbours(u).forEach(v -> {
                if (!closed.contains(v)) {
                    double tempDistance = g.get(u) + graph.edgeWeight(u, v);
                    if (tempDistance < g.getOrDefault(v, Double.POSITIVE_INFINITY)) {
                        g.put(v, tempDistance);
                        parent.put(v, u);
                        open.add(v);
                    }
                }
            });
            for (var obs : observers) obs.update();
        });
        t.start();
            /*while (!open.isEmpty()) {
                V u = open.poll();
                closed.add(u);
                if (u.equals(destination)) {
                    break;
                }
                graph.neighbours(u).forEach(v -> {
                    if (!closed.contains(v)) {
                        double tempDistance = g.get(u) + graph.edgeWeight(u, v);
                        if (tempDistance < g.getOrDefault(v, Double.POSITIVE_INFINITY)) {
                            g.put(v, tempDistance);
                            parent.put(v, u);
                            open.add(v);
                        }
                    }
                });

            }*/

        System.out.println("Creating path...");
    }

    Timer t;

    private void stop() {
        observers.forEach(PFObserver::done);
        t.stop();
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    public LinkedList<V> path;

    private void path(V source, V destination) {
        V current = destination;
        path = new LinkedList<>();
        path.push(current);
        while (!current.equals(source)) {
            current = parent.get(current);
            path.addFirst(current);
        }
    }

    public List<V> path() {
        return path;
    }

    @Override
    public V current() {
        return null;
    }
}
