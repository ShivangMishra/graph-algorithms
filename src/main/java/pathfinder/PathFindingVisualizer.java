package main.java.pathfinder;

import main.java.graph.algorithms.pathfind.PFObserver;
import main.java.graph.algorithms.pathfind.PathFindAlgorithm;
import main.java.pathfinding.world.NavGrid;
import main.java.pathfinding.world.Node;

import javax.swing.*;
import java.awt.*;

public class PathFindingVisualizer extends JPanel implements PFObserver<Node> {
    private final NavGrid navGrid;

    private int cellWidth, cellHeight;
    private static final int OFF = 10;
    private PathFindAlgorithm<Node> algo;

    public PathFindingVisualizer(NavGrid navGrid) {
        setPreferredSize(new Dimension(600, 600));
        cellHeight = getPreferredSize().height / navGrid.nRows();
        cellWidth = getPreferredSize().width / navGrid.nColumns();
        setPreferredSize(new Dimension(600 + 2 * OFF, 600 + 2 * OFF));
//        cellHeight = 50;
//        cellWidth = 50;
        setBackground(Color.LIGHT_GRAY);
        this.navGrid = navGrid;
    }

    public void paintComponent(Graphics g) {
//        System.out.println("update");
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        for (Node node : algo.getOpen()) {
            g.fillRect(OFF + node.column * cellWidth, OFF + node.row * cellHeight, cellWidth, cellHeight);
        }
        g.setColor(Color.RED);
        for (Node node : algo.getClosed()) {
            g.fillRect(OFF + node.column * cellWidth, OFF + node.row * cellHeight, cellWidth, cellHeight);
        }

        g.setColor(Color.YELLOW);
        if (algo.path() != null) {
            g.setColor(Color.YELLOW);
            System.out.println("Drawing path");
            for (var node : algo.path()) {
                g.fillRect(OFF + node.column * cellWidth, OFF + node.row * cellHeight, cellWidth, cellHeight);
            }
        } else {
            g.setColor(Color.CYAN);
            for (var node : algo.currentPath()) {
                System.out.println(node.row + "," +  node.column);
                g.fillRect(OFF + node.column * cellWidth, OFF + node.row * cellHeight, cellWidth, cellHeight);
            }
        }
        Node node;

        if ((node = algo.current()) != null) {
            g.setColor(Color.YELLOW);
            g.fillRect(OFF + node.column * cellWidth, OFF + node.row * cellHeight, cellWidth, cellHeight);
        }
        g.setColor(Color.BLACK);
        for (int row = 0; row < navGrid.nRows(); row++) {
            for (int col = 0; col < navGrid.nColumns(); col++) {
                g.drawRect(OFF + col * cellWidth, OFF + row * cellHeight, cellWidth, cellHeight);
//                g.drawString(row + ","+ col, OFF + col * cellWidth, 2*OFF + row * cellHeight);
                if (!navGrid.isNodeWalkable(row, col)) {
                    g.fillRect(OFF + col * cellWidth, OFF + row * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }

    @Override
    public void init(PathFindAlgorithm<Node> algo) {
        this.algo = algo;
    }

    @Override
    public synchronized void update() {
//        System.out.println("update");
        repaint();
    }

    @Override
    public void done() {
        System.out.println("DONE");
        repaint();
    }
}
