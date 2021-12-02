package de.cas.adventofcode.y2021.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import de.cas.adventofcode.util.AdventUtil;

public class Day1 {

	public static void main(String[] args) throws IOException {
		part2();
	}

	private static void part1() throws IOException {
		int[] numbersArray = readFile();
		System.out.println("Number of total lines: " + numbersArray.length);

		int numberOfIncreases = 0;
		for (int i = 0; i < numbersArray.length - 1; i++) {
			Integer first = numbersArray[i];
			Integer second = numbersArray[i + 1];
			if (isIncrease(first, second)) {
				System.out.println(first + "<" + second);
				numberOfIncreases++;
			} else {
				System.out.println(first + ">" + second);
			}
		}
		System.out.println("Number of increases: " + numberOfIncreases);
	}

	private static void part2() throws IOException {
		int[] numbersArray = AdventUtil.readFileByLineAsInts("2021", "day1_input.txt");

		int numberOfIncreases = 0;
		for (int i = 0; i < numbersArray.length - 3; i++) {
			int firstWindow = sum3Window(i, numbersArray);
			int secondWindow = sum3Window(i+1, numbersArray);
			
			if (isIncrease(firstWindow, secondWindow)) {
				System.out.println(firstWindow + "<" + secondWindow);
				numberOfIncreases++;
			} else {
				System.out.println(firstWindow + ">" + secondWindow);
			}
		}
		System.out.println("Number of increases: " + numberOfIncreases);
	}

	private static int sum3Window(int i, int[] numbersArray) {
		return numbersArray[i] + numbersArray[i+1] + numbersArray[i+2];
	}

	private static boolean isIncrease(Integer first, int second) {
		return first < second;
	}

	private static int[] readFile() throws IOException {
		Path path = Paths.get("src", "main", "resources", "day1_input.txt");

		try (Stream<String> linesStream = Files.lines(path)) {
			return linesStream.mapToInt(row -> Integer.parseInt(row)).toArray();
		}
	}
}
