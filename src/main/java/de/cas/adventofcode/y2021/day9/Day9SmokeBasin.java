package de.cas.adventofcode.y2021.day9;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.ImmutablePoint;

/**
 * @author Ingo.Siebert
 *
 */
public class Day9SmokeBasin {

	private static final String FILENAME = "day9_input.txt";

	public static void main(String[] args) throws IOException {
		Day9SmokeBasin day9 = new Day9SmokeBasin();
		int[][] input = AdventUtil.readFileByLineByCharAsInt("2021", FILENAME);
//		System.out.println(Arrays.deepToString(input));
		List<ImmutablePoint> lowPoints = day9.findLowPoints(input); // solution part 1
		day9.calculateSizeOfBasins(lowPoints, input); // solution part 2
	}

	private void calculateSizeOfBasins(final List<ImmutablePoint> lowPoints, final int[][] input) {
		List<Set<ImmutablePoint>> basins = lowPoints.stream()
				.map(lowPoint -> calculateSizeOfBasin(lowPoint, input, new HashSet<>())).collect(Collectors.toList());
		System.out.println("Found basins: " + basins);

//		List<Integer> allSizes = basins.stream().mapToInt(b -> b.size()).sorted().boxed().collect(Collectors.toList());
//		System.err.println("All sizes = " + allSizes); // 3, 9, 9, 14

		List<Integer> threeBiggestSizes = basins.stream().map(b -> b.size()).sorted(Collections.reverseOrder()).limit(3)
				.collect(Collectors.toList());
		System.out.println("3 biggest sizes = " + threeBiggestSizes);
		System.out.println("Part 2 result " + threeBiggestSizes.stream().reduce(1, (a, b) -> a * b));
	}

	private Set<ImmutablePoint> calculateSizeOfBasin(ImmutablePoint lowpoint, int[][] input,
			Set<ImmutablePoint> alreadyKnownPoints) {
		if (alreadyKnownPoints.isEmpty()) {
			System.out.println("***");
		}

		// point is always part of a basin
		alreadyKnownPoints.add(lowpoint);

		List<ImmutablePoint> increasingNeighbours = findIncreasingNeighbours(lowpoint, input);
		System.out.println("increasing neighbours for " + lowpoint + " are " + increasingNeighbours);

		List<ImmutablePoint> unknownIncreasingNeighbours = increasingNeighbours.stream()
				.filter(p -> !alreadyKnownPoints.contains(p)).collect(Collectors.toList());
		System.out.println("unknown neighbours for " + lowpoint + " are " + unknownIncreasingNeighbours);

		alreadyKnownPoints.addAll(unknownIncreasingNeighbours);
		unknownIncreasingNeighbours.stream().forEach(p -> calculateSizeOfBasin(p, input, alreadyKnownPoints));

		return alreadyKnownPoints;
	}

	private List<ImmutablePoint> findIncreasingNeighbours(ImmutablePoint point, final int[][] input) {
		List<ImmutablePoint> neighbours = new ArrayList<>();
		int valueOfPoint = input[point.x()][point.y()];
		if (valueOfPoint == 9) {
			return Collections.emptyList();
		}

		if (point.x() - 1 >= 0) {
			int diff = Math.abs(valueOfPoint - input[point.x() - 1][point.y()]);
			if (input[point.x() - 1][point.y()] < 9 && (diff == 1 || diff == 0)) {
				neighbours.add(new ImmutablePoint(point.x() - 1, point.y()));
			}
		}
		if (point.x() + 1 < input.length) {
			int diff = Math.abs(valueOfPoint - input[point.x() + 1][point.y()]);
			if (input[point.x() + 1][point.y()] < 9 && (diff == 1 || diff == 0)) {
				neighbours.add(new ImmutablePoint(point.x() + 1, point.y()));
			}
		}
		if (point.y() - 1 >= 0) {
			int diff = Math.abs(valueOfPoint - input[point.x()][point.y() - 1]);
			if (input[point.x()][point.y() - 1] < 9 && (diff == 1 || diff == 0)) {
				neighbours.add(new ImmutablePoint(point.x(), point.y() - 1));
			}
		}
		if (point.y() + 1 < input[0].length)  {
			int diff = Math.abs(valueOfPoint - input[point.x()][point.y() + 1]);
			if (input[point.x()][point.y() + 1] < 9 && (diff == 1 || diff == 0)) {
				neighbours.add(new ImmutablePoint(point.x(), point.y() + 1));
			}
		}
		return neighbours;
	}

	private List<ImmutablePoint> findLowPoints(int[][] input) {
		int maxRows = input.length;
		int maxCols = input[0].length;

		final List<ImmutablePoint> lowPoints = new ArrayList<>();

		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				ImmutablePoint point = new ImmutablePoint(row, col);
				if (isLowPoint(point, input)) {
					lowPoints.add(point);
				}
			}
		}
		System.out.println("low points: " + lowPoints);
		int risk = lowPoints.stream().map(p -> input[p.x()][p.y()]).map(c -> c + 1).mapToInt(i -> i).sum();

		System.out.println("Risk is " + risk);
		return lowPoints;
	}

	private boolean isLowPoint(ImmutablePoint point, int[][] input) {
		for (int x = point.x() - 1; x < point.x() + 2; x++) {
			for (int y = point.y() - 1; y < point.y() + 2; y++) {
//				System.out.println("Checking "+ x + "," + y + " for " + point);
				if (x < 0 || x >= input.length) {
					continue;
				}
				if (y < 0 || y >= input[0].length) {
					continue;
				}
				if (input[point.x()][point.y()] > input[x][y]) {
					return false;
				}
			}
		}
		return true;
	}

}
