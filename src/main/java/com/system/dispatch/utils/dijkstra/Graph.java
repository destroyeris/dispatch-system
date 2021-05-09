package com.system.dispatch.utils.dijkstra;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private final Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return this.nodes;
    }
}
