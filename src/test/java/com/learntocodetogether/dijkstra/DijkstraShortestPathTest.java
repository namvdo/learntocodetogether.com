package com.learntocodetogether.dijkstra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author namvdo
 */
public class DijkstraShortestPathTest {
    static final Dijkstra dijkstra = new Dijkstra();
    static Graph graph;
    static List<Node> nodes = new ArrayList<>();
    @BeforeAll
    static void setup() {
        graph = new Graph();
        nodes.add(new Node("A"));
        nodes.add(new Node("B"));
        nodes.add(new Node("C"));
        nodes.add(new Node("D"));
        nodes.add(new Node("E"));
        nodes.add(new Node("F"));
        nodes.add(new Node("G"));


        nodes.get(0).addAdjacentNode(nodes.get(1), 10);
        nodes.get(0).addAdjacentNode(nodes.get(2), 15);

        nodes.get(1).addAdjacentNode(nodes.get(3), 12);
        nodes.get(1).addAdjacentNode(nodes.get(5), 15);

        nodes.get(2).addAdjacentNode(nodes.get(4), 10);

        nodes.get(3).addAdjacentNode(nodes.get(4), 2);
        nodes.get(3).addAdjacentNode(nodes.get(5), 1);

        nodes.get(5).addAdjacentNode(nodes.get(4), 5);
        nodes.get(5).addAdjacentNode(nodes.get(6), 3);
        graph.addNode(nodes);
    }
    @Test
    void testGetShortestPath() {
        Node nodeA = nodes.get(0);
        Node nodeE = nodes.get(4);
        Node nodeF = nodes.get(5);
        Node nodeG = nodes.get(6);
        dijkstra.calculateShortestPath(graph, nodeA);
        assertEquals(24, dijkstra.getTotalDistance(nodeE));
        assertEquals(23, dijkstra.getTotalDistance(nodeF));
        assertEquals(26, dijkstra.getTotalDistance(nodeG));
    }
}
