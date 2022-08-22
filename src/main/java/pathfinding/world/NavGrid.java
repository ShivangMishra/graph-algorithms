package main.java.pathfinding.world;

import main.java.graph.Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * A lightweight grid to play with our pathfinding algorithms.
 * Creating a complete Graph with 1000 or more vertices is slow due to the large number of edges.
 * NavGrid keeps track of all the nodes in a matrix and calculates the neighbours on the fly
 * instead of storing them in the form of vertices and edges.
 * As neighbours and edgeWeight methods are defined correctly in NavGrid,
 * the pathfinding algorithms don't care about its internal working !!
 */
public class NavGrid extends Graph<Node> {
    private final byte[][] grid;
    //    private final Node[] nodes;
    public final Node startNode;
    public final Node endNode;
    private boolean diagonallyTraversable;

    public NavGrid(int rows, int columns) {
        grid = new byte[rows][columns];
        startNode = new Node(0, 0);
        endNode = new Node(nRows() - 1, nColumns() - 1);
//        nodes = new Node[nRows() * nColumns()];
//
//        for (int row = 0, counter = 0; row < nRows(); row++) {
//            for (int column = 0; column < nColumns(); column++) {
//                nodes[counter++] = (new Node(row, column));
//            }
//        }
    }

    @Override
    public double edgeWeight(Node v1, Node v2) {
        return v1.row == v2.row || v1.column == v2.column ? 1 : 1.4;
    }

    public void fillRandom(double probability) {
        for (int row = 0; row < nRows(); row++) {
            for (int col = 0; col < nColumns(); col++) {
                if (Math.random() <= probability) {
                    grid[row][col] = 1;
                }
            }
        }
        grid[startNode.row][startNode.column] = 0;
        grid[endNode.row][endNode.column] = 0;
    }

//    public Node[] nodes() {
//        return nodes;
//    }

    public Set<Node> neighbours(Node node) {
        int row = node.row;
        int column = node.column;
        HashSet<Node> neighbours = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                if (row + i >= nRows() || row + i < 0 || column + j >= nColumns() || column + j < 0)
                    continue;
                if (!isDiagonallyTraversable() && i != 0 && j != 0) {
                    continue;
                }
                if (isNodeWalkable(row + i, column + j))
                    neighbours.add(new Node(row + i, column + j));
            }
        }
        return neighbours;
    }

    public void setDiagonallyTraversable(boolean canMoveDiagonally) {
        this.diagonallyTraversable = canMoveDiagonally;
    }

    public boolean isDiagonallyTraversable() {
        return diagonallyTraversable;
    }

    /* TODO: 6/28/2022 Make this method a way to convert our NavGrid(a pseudo graph) into a fully functional graph.*/
    public Graph<Node> toGraph() {
        System.out.println("to graph...");
        Graph<Node> graph = new Graph<>();
        for (int row = 0; row < nRows(); row++) {
            for (int col = 0; col < nColumns(); col++) {
                graph.addVertex(new Node(row, col));
            }
        }
        System.out.println("added vertices...");
        for (int row = 0; row < nRows(); row++) {
            for (int col = 0; col < nColumns(); col++) {
                Node node = new Node(row, col);
                for (Node n : neighbours(node)) {
                    double weight = node.row == n.row || node.column == n.column ? 1 : 1.4;
                    graph.addEdge(node, n, weight);
                }
            }
        }
        System.out.println("added edges...");
        return graph;
    }

    public boolean isNodeWalkable(int row, int col) {
        return grid[row][col] != 1;
    }

    public int nRows() {
        return grid.length;
    }

    public int nColumns() {
        return grid[0].length;
    }

    public double euclideanDistanceSquared(Node n1, Node n2) {
        return (n1.row - n2.row) * (n1.row - n2.row) + (n1.column - n2.column) * (n1.column - n2.column);
    }

    public double manhattanDistance(Node n1, Node n2) {
        return Math.abs(n1.row - n2.row) + Math.abs(n1.column - n2.column);
    }
}
