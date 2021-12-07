package de.cas.adventofcode.y2021.day7;

import java.io.IOException;
import java.util.List;

import de.cas.adventofcode.util.AdventUtil;

public class Day7CrabSubmarines {

	private static final String FILENAME = "day7_input.txt";

	public static void main(String[] args) throws IOException {
		Day7CrabSubmarines day7 = new Day7CrabSubmarines();
//		day7.findBestHorizontalPosition();
		day7.findBestHorizontalPositionIncreasingConsumption();
	}

	private void findBestHorizontalPosition() throws IOException {
		String input = AdventUtil.readFileByLineAsString("2021", FILENAME).get(0);
		List<Integer> crabPositions = AdventUtil.parseToType(input, ",", Integer::valueOf);
		System.out.println("Crab positions: " + crabPositions);

		int minValue = crabPositions.stream().mapToInt(i -> i).min().getAsInt();
		int maxValue = crabPositions.stream().mapToInt(i -> i).max().getAsInt();

		PositionChange bestPos = new PositionChange(Integer.MAX_VALUE, -1);
		for (int hPos = minValue; hPos < maxValue; hPos++) {
			final int finalHPos = hPos;
			int requiredFuel = crabPositions.stream().map(pos -> Math.abs(finalHPos - pos)).mapToInt(i -> i).sum();
			if (requiredFuel < bestPos.requiredFuel()) {
				bestPos = new PositionChange(requiredFuel, finalHPos);
			}
		}

		System.out.println("Best horizontal position is " + bestPos);
	}

	private void findBestHorizontalPositionIncreasingConsumption() throws IOException {
		String input = AdventUtil.readFileByLineAsString("2021", FILENAME).get(0);
		List<Integer> crabPositions = AdventUtil.parseToType(input, ",", Integer::valueOf);
		System.out.println("Crab positions: " + crabPositions);

		int minValue = crabPositions.stream().mapToInt(i -> i).min().getAsInt();
		int maxValue = crabPositions.stream().mapToInt(i -> i).max().getAsInt();

		PositionChange bestPos = new PositionChange(Integer.MAX_VALUE, -1);
		for (int hPos = minValue; hPos < maxValue; hPos++) {
			final int finalHPos = hPos;
			int requiredFuel = crabPositions.stream().map(pos -> calculateUsedFuel(pos, finalHPos)).mapToInt(z -> z)
					.sum();
			if (requiredFuel < bestPos.requiredFuel()) {
				bestPos = new PositionChange(requiredFuel, finalHPos);
			}
		}

		System.out.println("Best horizontal position is " + bestPos);
	}

	/**
	 * 1 diff = 0 + 1 = 1
	 * 2 diff = 1 + 1 = 2 
	 * 3 diff = 2 + 1 = 3 
	 * 4 diff = 3 + 1 = 4
	 * ..
	 * + sum so far
	 */
	private int calculateUsedFuel(Integer crabPos, int hPos) {
		int absDiff = Math.abs(hPos - crabPos);

		int usedFuel = 0;
		for (int i = 0; i < absDiff; i++) {
			usedFuel += i + 1;
		}
		return usedFuel;
	}
}
