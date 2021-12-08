package de.cas.adventofcode.y2021.day8;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import de.cas.adventofcode.util.AdventUtil;

public class Day8SevenSegmentMalfunction {

	private static final String FILENAME = "day8_input.txt";

	private static final List<String> TWO = Arrays.asList("a", "c", "d", "e", "g");
	private static final List<String> THREE = Arrays.asList("a", "c", "d", "f", "g");
	private static final List<String> FIVE = Arrays.asList("a", "b", "d", "f", "g");

	private static final List<String> ZERO = Arrays.asList("a", "b", "c", "e", "f", "g");
	private static final List<String> SIX = Arrays.asList("a", "b", "d", "e", "f", "g");
	private static final List<String> NINE = Arrays.asList("a", "b", "c", "d", "f", "g");

	public static void main(String[] args) throws IOException {
		Day8SevenSegmentMalfunction day8 = new Day8SevenSegmentMalfunction();
		List<SevenSegmentPattern> input = day8.parseInput();
		System.out.println(input);
		day8.count1478(input);
	}

	private void count1478(List<SevenSegmentPattern> input) {
		int sum = input.stream().map(p -> p.output).flatMap(List::stream).map(singleOutput -> countEasyNumbers(singleOutput)).mapToInt(i -> i).sum();
		System.out.println("Sum of 1,4,7,8 is " + sum);
	}

	private int countEasyNumbers(String singleOutput) {
		switch (singleOutput.length()) {
		case 2:
		case 4:
		case 3:
		case 7:
			return 1;
		default:
			return 0;
		}
	}

	private List<SevenSegmentPattern> parseInput() throws IOException {
		List<String> inputLines = AdventUtil.readFileByLineAsString("2021", FILENAME);
		return inputLines.stream().map(line -> map7SegmentPatterns(line)).collect(Collectors.toList());
	}

	private SevenSegmentPattern map7SegmentPatterns(String line) {
		String[] splittedLine = line.split("\\|");
		Assert.isTrue(splittedLine.length == 2, "expected 2 but was " + splittedLine.length);
		List<String> numbers = map7Segments(splittedLine[0]);
		List<String> output = map7Segments(splittedLine[1]);
		return new SevenSegmentPattern(numbers, output);
	}

	private List<String> map7Segments(String string) {
		return Arrays.stream(string.split(" ")).map(s -> StringUtils.trim(s)).filter(t -> !t.isEmpty()).collect(Collectors.toList());
	}

	public record SevenSegmentPattern(List<String> numbers, List<String> output) {
	}
}
