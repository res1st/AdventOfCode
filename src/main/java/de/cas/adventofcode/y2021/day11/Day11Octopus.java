package de.cas.adventofcode.y2021.day11;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.ImmutablePoint;

public class Day11Octopus {

	public static void main(String[] args) throws IOException {
		int[][] input = AdventUtil.readFileByLineByCharAsInt("2021", "day11_input.txt");
		LuminescentOctopus[][] octopuses = createOctopuses(input);
		Day11Octopus day11 = new Day11Octopus();
		day11.printOctopuses(octopuses);
		day11.agingOctopuses(octopuses);
	}

	private AtomicInteger numberOfFlashes = new AtomicInteger();

	private void agingOctopuses(LuminescentOctopus[][] octopuses) {
		for (int round = 0; round < 400; round++) {
			System.out.println("\n Round " + round);
			iterateAllOctopuses(octopuses, o -> o.increaseEnergy());

			boolean newFlashings = false;
			do {
				newFlashings = false;
				for (int y = 0; y < octopuses.length; y++) {
					for (int x = 0; x < octopuses[0].length; x++) {
						ImmutablePoint p = new ImmutablePoint(x, y);
						boolean isFlashing = isFlashing(octopuses, p);
						if (isFlashing) {
							newFlashings = true;
							octopuses[y][x] = octopuses[y][x].doFlashing();
							AdventUtil.doSurroundingsWithDiagonal(octopuses, x, y, o -> o.increaseEnergy());
							numberOfFlashes.incrementAndGet();
						}
					}
				}
			} while (newFlashings);

			iterateAllOctopuses(octopuses, o -> o.resetFlashState());
			printOctopuses(octopuses);
			System.out.println(" Flashes " + numberOfFlashes);

			checkForSimultaneousFlashing(octopuses);
		}
	}

	private void checkForSimultaneousFlashing(LuminescentOctopus[][] octopuses) {
		final AtomicInteger numberOfFlashings = new AtomicInteger();
		iterateAllOctopuses(octopuses, o -> {
			if (o.energy == 0) {
				numberOfFlashings.incrementAndGet();
			}
			return o;
		});
		if (numberOfFlashings.intValue() == 100) {
			System.out.println(" simultaneous flashing !!!");
		}
	}

	private void printOctopuses(LuminescentOctopus[][] octopuses) {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < octopuses.length; y++) {
			for (int x = 0; x < octopuses[0].length; x++) {
				builder.append(octopuses[y][x].energy);
			}
			builder.append("\n");
		}
		builder.deleteCharAt(builder.length()-1);
		System.out.println(builder.toString());
	}

	private boolean isFlashing(LuminescentOctopus[][] octopuses, ImmutablePoint point) {
		LuminescentOctopus octopus = octopuses[point.y()][point.x()];
		if (octopus.energy > 9 && !octopus.flashing) {
			return true;
		}
		return false;
	}

	private void iterateAllOctopuses(LuminescentOctopus[][] octopuses,
			Function<LuminescentOctopus, LuminescentOctopus> action) {
		for (int y = 0; y < octopuses.length; y++) {
			for (int x = 0; x < octopuses[0].length; x++) {
				octopuses[y][x] = action.apply(octopuses[y][x]);
			}
		}
	}

	private static LuminescentOctopus[][] createOctopuses(int[][] input) {
		LuminescentOctopus[][] octopuses = new LuminescentOctopus[input.length][input[0].length];
		for (int r = 0; r < input.length; r++) {
			for (int c = 0; c < input[0].length; c++) {
				octopuses[r][c] = new LuminescentOctopus(input[r][c], false);
			}
		}
		return octopuses;
	}

	private record LuminescentOctopus(int energy, boolean flashing) {

		public LuminescentOctopus increaseEnergy() {
			if (flashing) {
				return this;
			}
			return new LuminescentOctopus(energy + 1, flashing);
		}

		public LuminescentOctopus resetFlashState() {
			return new LuminescentOctopus(energy, false);
		}

		public LuminescentOctopus doFlashing() {
			return new LuminescentOctopus(0, true);
		}
	}
}
