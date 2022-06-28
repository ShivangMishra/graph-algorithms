package main.java.pathfinder;

import main.java.graph.algorithms.pathfind.AStar;
import main.java.pathfinding.world.NavGrid;
import main.java.pathfinding.world.Node;

import javax.swing.*;

public class Pathfinder extends JFrame {

    public static void main(String[] args) {
        new Pathfinder();
    }

    public Pathfinder() {
        NavGrid navGrid = new NavGrid(50, 50);
        navGrid.fillRandom(0.2);
        //Dijkstra <Node> p = new Dijkstra<>();
//        AStar<Node> p = new AStar<>((v1,v2)->Math.sqrt(navGrid.euclideanDistanceSquared(v1,v2)));
        AStar<Node> p = new AStar<>((v1,v2)->0);
        PathFindingVisualizer pfv = new PathFindingVisualizer(navGrid);
        System.out.println("made pfv");
        p.addObserver(pfv);
        System.out.println("added o");
        p.findPath(navGrid, navGrid.startNode, navGrid.endNode);
        setContentPane(pfv);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
