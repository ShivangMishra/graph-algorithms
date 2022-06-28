package main.java.graph;

import java.util.Objects;

public class Edge<V> {
    public final V v1, v2;

    public Edge(V v1, V v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (!Objects.equals(v1, edge.v1)) return false;
        return Objects.equals(v2, edge.v2);
    }

    @Override
    public int hashCode() {
        return ((v1 == null) ? 0 : v1.hashCode()) ^ ((v2 == null) ? 0 : v2.hashCode());
    }
}
