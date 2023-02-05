package com.learntocodetogether.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author namvdo
 */
public record Graph(Set<Node> nodes) {

    public Graph() {
        this(new HashSet<>());
    }
    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void addNode(List<Node> node) {
        this.nodes.addAll(node);
    }
}
