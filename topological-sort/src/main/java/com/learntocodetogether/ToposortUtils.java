package com.learntocodetogether;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class ToposortUtils {
    public static Graph createGraph(List<TableOrdering> tableOrderings) {
        Graph graph = new Graph();
        for (TableOrdering tableOrdering : tableOrderings) {
            graph.setIndegree(tableOrdering.getNode(), 0);
            graph.setAdj(tableOrdering.getNode(), new HashSet<>());
        }
        for (TableOrdering tableOrdering : tableOrderings) {
            String node = tableOrdering.getNode();
            List<String> dependencies = tableOrdering.getDependencies();
            for (String dependency : dependencies) {
                graph.addEdge(dependency, node);
            }
        }
        return graph;
    }

    public static List<TableOrdering> findLinearOrdering(List<TableOrdering> tableOrderings) {
        Graph graph = createGraph(tableOrderings);
        List<TableOrdering> results = new ArrayList<>();
        Map<String, TableOrdering> tableNameToTable = mapTableByName(tableOrderings);
        Queue<TableOrdering> nodesWithNoIncomingEdges = getNodesWithNoIncomingEdges(graph, tableNameToTable);
        while (!nodesWithNoIncomingEdges.isEmpty()) {
            TableOrdering node = nodesWithNoIncomingEdges.poll();
            results.add(node);
            Set<String> neighbors = graph.getNeighbors(node.getNode());
            for (String neighbor : neighbors) {
                int indegree = graph.getInDegree(neighbor);
                int newIndegree = indegree - 1;
                graph.setIndegree(neighbor, newIndegree);
                if (newIndegree == 0) {
                    TableOrdering table = tableNameToTable.get(neighbor);
                    nodesWithNoIncomingEdges.add(table);
                }
            }
        }
        if (graph.containsAnyEdge()) {
            throw new IllegalArgumentException("Graph contains at least one cycle");
        } else {
            return results;
        }
    }


    static Map<String, TableOrdering> mapTableByName(List<TableOrdering> tableOrderings) {
        return tableOrderings.stream().collect(Collectors.toMap(TableOrdering::getNode, e -> e));
    }

    static Queue<TableOrdering> getNodesWithNoIncomingEdges(Graph graph, Map<String, TableOrdering> tableNameToTable) {
        Queue<TableOrdering> nodesWithNoIncomingEdges = new ArrayDeque<>();
        for (Map.Entry<String, Integer> e : graph.getInDegrees().entrySet()) {
            if (e.getValue() == 0) {
                nodesWithNoIncomingEdges.add(tableNameToTable.get(e.getKey()));
            }
        }
        return nodesWithNoIncomingEdges;
    }
}
