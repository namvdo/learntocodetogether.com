package com.learntocodetogether.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author namvdo
 */
public class Node {
    public Node(String label) {
        this.label = label;
    }
    private final String label;
    private final Map<Node, Integer> adjacentNodes = new HashMap<>();
    private int distance;
    private Node prev;





    public void addAdjacentNode(Node neighbor, int distance) {
        this.adjacentNodes.put(neighbor, distance);
    }

    public void addAdjacentNodes(Map<Node, Integer> neighbors) {
        this.adjacentNodes.putAll(neighbors);
    }

    public String getLabel() {
        return label;
    }

    public int getDistance() {
        return distance;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node node) {
        this.prev = node;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


    @Override
    public String toString() {
        return "[" + this.label + "," + this.distance + "]";
    }
}
