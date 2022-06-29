package main.java.graph.algorithms.pathfind;

import main.java.graph.Graph;

import javax.swing.Timer;
import java.util.*;

public class AStar<V> implements PathFindAlgorithm<V> {

    private final PriorityQueue<V> open;
    private final HashMap<V, V> parent;
    private final HashSet<V> closed;
    private final HashMap<V, Double> g;
    private final HashMap<V, Double> f;
    private final Heuristic<V> heuristic;

    private V current;
    private final ArrayList<PFObserver<V>> observers;

    public AStar(Heuristic<V> heuristic) {
        parent = new HashMap<>();
        f = new HashMap<>();
        g = new HashMap<>();
        open = new PriorityQueue<>(Comparator.comparing(f::get));
        closed = new HashSet<>();

        observers = new ArrayList<>();
        this.heuristic = heuristic;
    }


    public PriorityQueue<V> getOpen() {
        return open;
    }

    public HashSet<V> getClosed() {
        return closed;
    }

    @Override
    public void findPath(Graph<V> graph, V source, V destination) {
        open.add(source);
        g.put(source, 0.0);
        t = new Timer(25, e -> {
            if (!open.isEmpty()) {
                V current = open.poll();
                this.current = current;
                closed.add(current);
                if (current.equals(destination)) {
                    path(source, destination);
                    stop();
                }

                for (V child : graph.neighbours(current)) {
                    if (closed.contains(child)) {
                        continue;
                    }
                    double vg = g.get(current) + graph.edgeWeight(current, child);
                    double vh = heuristic.getCost(current, destination);
                    double vf = vg + vh;
                    if (vg <= g.getOrDefault(child, Double.POSITIVE_INFINITY)) {
                        g.put(child, vg);
                        f.put(child, vf);
                        parent.put(child, current);
                    }
                    if (!open.contains(child)) open.add(child);
                }
                observers.forEach(PFObserver::update);
            }
        });
        t.start();
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

    public void addObserver(PFObserver<V> o) {
        observers.add(o);
        o.init(this);
    }

    Timer t;

    private void stop() {
        observers.forEach(PFObserver::done);
        t.stop();
    }


    public List<V> path() {
        return path;
    }

    @Override
    public V current() {
        return current;
    }

    @Override
    public List<V> currentPath() {
        LinkedList<V> currentPath = new LinkedList<>();
        V temp = current;
        currentPath.push(temp);
        while (temp != null) {
            if(!parent.containsKey(temp))
                break;
            temp = parent.get(temp);
            currentPath.addFirst(temp);
        }
        return currentPath;
    }
}
