package de.cas.adventofcode.y2021.day15;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.Graph;
import de.cas.adventofcode.util.GraphNode;
import de.cas.adventofcode.util.ImmutablePoint;

public class Day15Chiton {

	private static final String FILENAME = "day15_testinput.txt";

	public static void main(String[] args) throws IOException {
		Day15Chiton day15 = new Day15Chiton();
		Integer[][] cave = AdventUtil.readFileByLineByCharAsInteger("2021", FILENAME);
		System.out.println("Cave:\n" + Arrays.deepToString(cave));
//		day15.findlowsetRisk(cave);
	}

	private void findlowsetRisk(int[][] cave) {
//		findPaths(cave, 0, 0);
		
		Graph graph = new Graph();
		
		// nodeB.addDestination(nodeF, 15);
		GraphNode startNode = new GraphNode(0, 0);
		startNode.setDistance(0);
		startNode.addDestination(startNode, 0);
		
		List<GraphNode> nodes;
		
		
//		graph.getNodes().addAll(nodes);
//		calculateShortestPathFromSource(graph, );
	}
	
	private List<GraphNode> createGraphNodes(Integer[][] cave) {
		// <int[][], ImmutablePoint, GrahpNode>
		List<GraphNode> allNodes = AdventUtil.iterate2dArray(cave, (m,p) -> createGraphNode(m, p));
		return allNodes;
	}

	private GraphNode createGraphNode(Integer[][] cave, ImmutablePoint p) {
		GraphNode node = new GraphNode(p.x(), p.y());
		node.setDistance(Integer.MAX_VALUE);

//		node.addDestination(destNode, 0);
		return node;
	}

	private void findPaths(int[][] cave, int x, int y) {

	}

	public static Graph calculateShortestPathFromSource(Graph graph, GraphNode source) {
		source.setDistance(0);

		Set<GraphNode> visitedNodes = new HashSet<>();
		Set<GraphNode> nodesToCalc = new HashSet<>();

		nodesToCalc.add(source);

		while (nodesToCalc.size() != 0) {
			GraphNode currentNode = getLowestDistanceNode(nodesToCalc);
			nodesToCalc.remove(currentNode);
			for (Entry<GraphNode, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
				GraphNode adjacentNode = adjacencyPair.getKey();
				Integer edgeWeight = adjacencyPair.getValue();
				if (!visitedNodes.contains(adjacentNode)) {
					calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
					nodesToCalc.add(adjacentNode);
				}
			}
			visitedNodes.add(currentNode);
		}
		return graph;
	}

	private static GraphNode getLowestDistanceNode(Set<GraphNode> unsettledNodes) {
		GraphNode lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (GraphNode node : unsettledNodes) {
			int nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		return lowestDistanceNode;
	}

	private static void calculateMinimumDistance(GraphNode evaluationNode, Integer edgeWeigh, GraphNode predecessorNode) {
		Integer sourceDistance = predecessorNode.getDistance();
		if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			evaluationNode.setDistance(sourceDistance + edgeWeigh);
			LinkedList<GraphNode> shortestPath = new LinkedList<>(predecessorNode.getShortestPath());
			shortestPath.add(predecessorNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}
}
