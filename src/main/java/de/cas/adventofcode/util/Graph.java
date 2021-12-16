package de.cas.adventofcode.util;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<GraphNode> nodes = new HashSet<>();
    
    public void addNode(GraphNode nodeA) {
        nodes.add(nodeA);
    }

    public Set<GraphNode> getNodes() {
		return nodes;
	}
}
