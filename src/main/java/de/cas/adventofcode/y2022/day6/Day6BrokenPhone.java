package de.cas.adventofcode.y2022.day6;

import java.io.IOException;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.StreamUtil;

public class Day6BrokenPhone {

	public static void main(String[] args) throws IOException {
		List<String> data = AdventUtil.readFileByLineAsString("2022", "day6_input.txt");
		String input = data.get(0);
		System.out.println("Input " + input);

		List<Character> chars = StreamUtil.convertStringToCharList(input);
		OptionalInt startOfPacketMarkerIndex = slidingFinder(chars, 4);
		System.out.println("Start of packet: " + (startOfPacketMarkerIndex.getAsInt() + 4));
		
		OptionalInt startOfMessageMarkerIndex = slidingFinder(chars, 14);
		System.out.println("Start of message: " + (startOfMessageMarkerIndex.getAsInt() + 14));
	}

	public static OptionalInt slidingFinder(List<Character> list, int windowSize) {
		if (windowSize > list.size()) {
			return OptionalInt.empty();
		}

		return IntStream.range(0, list.size() - windowSize + 1)
				.map(start -> isStartStream(start, start+windowSize, list)).filter(i -> i>0).findFirst();
	}
	
	public static int isStartStream(int start, int end, List<Character> chars) {
		List<Character> window = chars.subList(start, end);
		
		String string = window.stream()
				.map(String::valueOf)
				.collect(Collectors.joining());
		
		if (AdventUtil.isUniqueChars(string)) {
			return start;
		}
		
		return -1;
	}
}
