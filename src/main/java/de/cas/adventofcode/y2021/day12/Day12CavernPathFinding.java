package de.cas.adventofcode.y2021.day12;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import de.cas.adventofcode.util.AdventUtil;

public class Day12CavernPathFinding {

	private static final String FILENAME = "day12_input.txt";

	public static final String START_TOKEN = "start";
	public static final String END_TOKEN = "end";

	public static void main(String[] args) throws IOException {
		Day12CavernPathFinding day12 = new Day12CavernPathFinding();
		List<String> pathsAsString = AdventUtil.readFileByLineAsString("2021", FILENAME);
		ListValuedMap<String, String> allPaths = day12.convertPathsToMap(pathsAsString);
		System.out.println("all paths: " + allPaths);
		day12.findPaths(allPaths);
		day12.findPathsSmallTwice(allPaths);
	}

	private void findPaths(ListValuedMap<String, String> allPaths) {
		long result = findPathsToEnd(START_TOKEN, allPaths, Set.of(START_TOKEN));
		System.out.println("paths part1: " + result);
	}

	private ListValuedMap<String, String> convertPathsToMap(List<String> pathsAsString) {
		Stream<String[]> pathsInBothDirections = pathsAsString.stream().map(path -> path.split("-"))
				.flatMap(e -> Stream.of(new String[] { e[0], e[1] }, new String[] { e[1], e[0] }));

		return pathsInBothDirections.collect(
				() -> new ArrayListValuedHashMap<String, String>(),
				(ListValuedMap<String, String> target, String[] streamedElement) -> target.put(streamedElement[0], streamedElement[1]),
				(ListValuedMap<String, String> target1, ListValuedMap<String, String> target2) -> target1.putAll(target2));

	}

	private long findPathsToEnd(String s, ListValuedMap<String, String> allTransisions, Set<String> visitedSmallCaves) {
		if (s.equals(END_TOKEN)) {
			return 1;
		}

		long foundPathToEnd = 0;
		List<String> reachableCaverns = allTransisions.get(s);

		for (String reachableCavern : reachableCaverns) {
			if (!visitedSmallCaves.contains(reachableCavern)) {
				// we found a valid new path: new small cave or big cave
				
				Set<String> newVisitedCaves = new HashSet<>(visitedSmallCaves);

				if (StringUtils.isAllLowerCase(reachableCavern)) {
					newVisitedCaves.add(reachableCavern);
				}
				foundPathToEnd += findPathsToEnd(reachableCavern, allTransisions, newVisitedCaves);
			}
		}
		return foundPathToEnd;
	}

	private void findPathsSmallTwice(ListValuedMap<String, String> allPaths) {
		// init with 2 to avoid to use start only once
		long result = findPathsToEndPart2(START_TOKEN, allPaths, Map.of(START_TOKEN, 2));
		System.out.println("paths part2: " + result);
	}
	
	private long findPathsToEndPart2(String s, ListValuedMap<String, String> allTransisions, Map<String, Integer> visitedSmallCaves) {
		if (s.equals(END_TOKEN)) {
			return 1;
		}
		
//		System.out.println("visits " + visitedSmallCaves);

		long foundPathToEnd = 0;
		List<String> reachableCaverns = allTransisions.get(s);

		for (String reachableCavern : reachableCaverns) {
			Integer visitCountOfCavern = visitedSmallCaves.get(reachableCavern);
			if ((neverVisited(visitedSmallCaves, reachableCavern) || visitedNotTwice(visitCountOfCavern)) && isMulipleSmallCavesVisitedTwice(visitedSmallCaves)) {
				
				Map<String, Integer> newVisitedCaves = new HashMap<String, Integer>(visitedSmallCaves);

				if (StringUtils.isAllLowerCase(reachableCavern)) {
					visitCountOfCavern = visitCountOfCavern==null? Integer.valueOf(0) : visitCountOfCavern;
					newVisitedCaves.put(reachableCavern, visitCountOfCavern+1);
				}
				foundPathToEnd += findPathsToEndPart2(reachableCavern, allTransisions, newVisitedCaves);
			}
		}
		return foundPathToEnd;
	}

	private boolean neverVisited(Map<String, Integer> visitedSmallCaves, String reachableCavern) {
		return !visitedSmallCaves.containsKey(reachableCavern);
	}

	private boolean visitedNotTwice(Integer visitCountOfCavern) {
		if (visitCountOfCavern == null) {
			return true;
		}
		return visitCountOfCavern < 2;
	}

	/**
	 * "a single small cave can be visited at most twice, and the remaining small caves can be visited at most once"
	 */
	private boolean isMulipleSmallCavesVisitedTwice(Map<String, Integer> visitedSmallCaves) {
		return visitedSmallCaves.values().stream().filter(visitCount -> visitCount==2).count() < 3;
	}
}
