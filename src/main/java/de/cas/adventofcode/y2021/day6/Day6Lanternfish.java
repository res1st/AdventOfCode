package de.cas.adventofcode.y2021.day6;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import de.cas.adventofcode.util.AdventUtil;

public class Day6Lanternfish {
	
	private static final String FILENAME = "day6_input.txt";

	// part1: 80 days
	// part2: 256 days
	private int numberOfDays = 256;

	public static void main(String[] args) throws IOException {
		Day6Lanternfish day6 = new Day6Lanternfish();
		day6.lanternfishPart1WithMap();
	}

	private void lanternfishPart1WithMap() throws IOException {
		String input = AdventUtil.readFileByLineAsString("2021", FILENAME).get(0);
		List<Integer> fishesData = AdventUtil.parseToType(input, ",", Integer::valueOf);
		System.out.println("Start fishes: " + fishesData);

		Map<Integer, Long> fishes = new HashMap<>();
		fishes.put(0, 0L);
		fishes.put(1, 0L);
		fishes.put(2, 0L);
		fishes.put(3, 0L);
		fishes.put(4, 0L);
		fishes.put(5, 0L);
		fishes.put(6, 0L);
		fishes.put(7, 0L);
		fishes.put(8, 0L);
		fishesData.stream().forEach(fish -> addFishToMap(fish, fishes));
		System.out.println("Start fishes: " + fishes);

		for (int day = 0; day<numberOfDays; day++) {
			long numberOfNewFishes = 0;
			for (int fishAge=0; fishAge<9; fishAge++) {
				if (fishAge==0) {
					numberOfNewFishes = fishes.get(0);
				} else {
					fishes.put(fishAge-1, fishes.get(fishAge));
				}
			}
			fishes.put(8, numberOfNewFishes);
			fishes.put(6, fishes.get(6) + numberOfNewFishes);
			System.out.println("Number of fishes in day " + day + ": " + countFishes(fishes));
		}
	}
	
	private long countFishes(Map<Integer, Long> fishes) {
		return fishes.values().stream().mapToLong(a -> a).sum();
	}


	private void addFishToMap(Integer fish, Map<Integer, Long> fishes) {
		fishes.put(fish, fishes.get(fish) + 1);
	}

	/**
	 * max list length reached!
	 * @throws IOException
	 */
	private void lanternfishPart1() throws IOException {
		String input = AdventUtil.readFileByLineAsString("2021", FILENAME).get(0);
		List<Short> fishes = AdventUtil.parseToType(input, ",", Short::valueOf);
		System.out.println("Start fishes: " + fishes);

		for (int day = 0; day<numberOfDays; day++) {
			AtomicInteger numberOfNewFishes = new AtomicInteger();
			fishes = fishes.stream().map(fish -> grow(fish, numberOfNewFishes)).collect(Collectors.toList());
			List<Short> newFishes = Collections.nCopies(numberOfNewFishes.shortValue(), (short) 8);
			fishes.addAll(newFishes);
			numberOfNewFishes.set(0);
//			System.out.println("Day " + day + ": " + fishes);
			System.out.println("Number of fishes: " + fishes.size());
		}
	}

	private Short grow(Short fish, AtomicInteger numberOfNewFishes) {
		switch (fish) {
		case 0: {
			numberOfNewFishes.incrementAndGet();
			return 6;
		}
		default:
			return (short) (fish-1);
		}
	}
}
