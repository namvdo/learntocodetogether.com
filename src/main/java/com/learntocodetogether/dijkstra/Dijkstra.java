package com.learntocodetogether.dijkstra;

import java.util.*;

/**
 * @author namvdo
 */
public class Dijkstra {
    public void calculateShortestPath(Graph graph, Node source) {
        Set<Node> unsettledNodes = new HashSet<>();
        Set<Node> settledNodes = new HashSet<>();
        for(final Node node : graph.nodes()) {
            node.setDistance(Integer.MAX_VALUE);
        }
        source.setDistance(0);
        unsettledNodes.add(source);
        while (!unsettledNodes.isEmpty()) {
            Node node = getNearestNode(unsettledNodes);
            unsettledNodes.remove(node);
            settledNodes.add(node);
            findMinNeighborDistance(unsettledNodes, settledNodes, node);
        }
    }

    public LinkedList<Node> getShortestPathFromSource(Node destination) {
        LinkedList<Node> path = new LinkedList<>();
        path.add(destination);
        while (destination.getPrev() != null) {
            destination = destination.getPrev();
            path.add(destination);
        }
        return path;
    }

    public int getTotalDistance(Node destination) {
        if (destination == null) throw new IllegalArgumentException();
        return destination.getDistance();
    }

    private void findMinNeighborDistance(Set<Node> unsettledNodes, Set<Node> settledNodes, Node node) {
        if (node == null) return;
        Map<Node, Integer> adjacentNodes = node.getAdjacentNodes();
        for(final Map.Entry<Node, Integer> entry : adjacentNodes.entrySet()) {
            Node neighbor = entry.getKey();
            int distance = entry.getValue();
            int sourceDistance = node.getDistance();
            if (!settledNodes.contains(neighbor)) {
                if (sourceDistance + distance < neighbor.getDistance()) {
                    neighbor.setDistance(sourceDistance + distance);
                    neighbor.setPrev(node);
                    unsettledNodes.add(neighbor);
                }
            }
        }
    }



    private Node getNearestNode(Set<Node> nodes) {
        Node node = null;
        int lowestDistance = Integer.MAX_VALUE;
        for(final var n  : nodes) {
            if (n.getDistance() < lowestDistance) {
                lowestDistance = n.getDistance();
                node = n;
            }
        }
        return node;
    }

}
