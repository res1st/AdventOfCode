package de.cas.adventofcode.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphNode {

	private String name;

	private int x;
	
	private int y;

	private List<GraphNode> shortestPath = new LinkedList<>();

	private int distance = Integer.MAX_VALUE;

	Map<GraphNode, Integer> adjacentNodes = new HashMap<>();

	public GraphNode(String name) {
		this.name = name;
	}

	public GraphNode(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void addDestination(GraphNode destination, int distance) {
		adjacentNodes.put(destination, distance);
	}

	public List<GraphNode> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<GraphNode> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Map<GraphNode, Integer> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<GraphNode, Integer> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
