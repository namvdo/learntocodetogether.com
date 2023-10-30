package com.learntocodetogether;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Integer> inDegrees = new HashMap<>();
    private final Map<String, Set<String>> adjList = new HashMap<>();

    public void addEdge(String from, String to) {
        adjList.computeIfAbsent(from, k -> new HashSet<>()).add(to);
        inDegrees.merge(to, 1, Integer::sum);
    }



    public Integer getInDegree(String node) {
        return inDegrees.get(node);
    }

    public Set<String> getNeighbors(String node) {
        return adjList.get(node);
    }

    public void setIndegree(String node, int val) {
        this.inDegrees.put(node, val);
    }

    public void setAdj(String node, Set<String> adj) {
        this.adjList.put(node, adj);
    }

    public Map<String, Integer> getInDegrees() {
        return inDegrees;
    }

    public Map<String, Set<String>> getAdjList() {
        return adjList;
    }

    public boolean containsAnyEdge() {
        for (Map.Entry<String, Integer> in : inDegrees.entrySet()) {
            if (in.getValue() != 0) return true;
        }
        return false;
    }
}
