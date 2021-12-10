package de.cas.adventofcode.y2021.day10;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import de.cas.adventofcode.util.AdventUtil;

public class Day10CorruptNavi {

	private static final String FILENAME = "day10_input.txt";
	private static Map<String, String> closingCharMap = new HashMap<>();
	private static Map<String, AtomicInteger> failures = new HashMap<>();

	static {
		closingCharMap.put(")", "(");
		closingCharMap.put("]", "[");
		closingCharMap.put("}", "{");
		closingCharMap.put(">", "<");

		failures.put(")", new AtomicInteger());
		failures.put("]", new AtomicInteger());
		failures.put("}", new AtomicInteger());
		failures.put(">", new AtomicInteger());
	}

	public static void main(String[] args) throws IOException {
		Day10CorruptNavi day10 = new Day10CorruptNavi();
		List<String> input = AdventUtil.readFileByLineAsString("2021", FILENAME);
		System.out.println("Input " + input);
		day10.parseNaviChunks(input);
		day10.autoCompplete(input);
	}

	private void autoCompplete(List<String> input) {
		List<Long> autoCompleteScores = input.stream().map(chunk -> parseSingleChunk(chunk))
				.filter(c -> c.isIncomplete()).map(ic -> autoCompleteSingleChunk(ic)).collect(Collectors.toList());
		autoCompleteScores.sort(Long::compare);
		System.out.println("all auto complete scores: " + autoCompleteScores);
		System.out.println("total length is " + autoCompleteScores.size());
		System.out.println("middle index is " + autoCompleteScores.size() / 2);
		Long middleValue = autoCompleteScores.get(autoCompleteScores.size() / 2);
		System.out.println("Auto completion score (middle) is " + middleValue);
	}

	private long autoCompleteSingleChunk(ChunkState chunkState) {
		List<Integer> scoresOfTokens = chunkState.stack().stream().map(token -> scoreOfToken(token)).collect(Collectors.toList());
//		System.out.println("token scores " + scoresOfTokens);
//		long score = 0;
//		for (int i=scoresOfTokens.size()-1; i>=0; i--) {
//			Integer tokenValue = scoresOfTokens.get(i);
//			score = (score*5) + tokenValue;
//		}
		
		long scoreWithReduce = AdventUtil.reverse(scoresOfTokens.stream()).mapToLong(i -> Long.valueOf(i)).reduce(0, (a,b) -> a*5 + b);
		return scoreWithReduce;
	}

	/** ): 1 point.
	 * ]: 2 points.
	 * }: 3 points.
	 * >: 4 points.
	 */
	private int scoreOfToken(String token) {
		switch (token) {
		case "(":
			return 1;
		case "[":
			return 2;
		case "{":
			return 3;
		case "<":
			return 4;
		default:
			throw new IllegalArgumentException("Unexpected value: " + token);
		}
	}

	private void parseNaviChunks(List<String> input) {
		input.stream().forEach(chunk -> parseSingleChunk(chunk));

		long score = failures.get(")").intValue() * 3 + failures.get("]").intValue() * 57
				+ failures.get("}").intValue() * 1197 + failures.get(">").intValue() * 25137;
		System.out.println("Syntax score is " + score);
	}

	private ChunkState parseSingleChunk(String chunk) {
		Stack<String> callStack = new Stack<>();

		String[] chars = chunk.trim().split("");
		for (int i = 0; i < chars.length; i++) {
			String newChar = chars[i];
//			if (callStack.isEmpty()) {
//				callStack.push(newChar);
//				continue;
//			}

			if (isOpeningChar(newChar)) {
				callStack.push(newChar);
				continue;
			}

			if (isMatchingClosingChar(newChar, callStack.peek())) {
				callStack.pop();
				continue;
			}

			failures.get(newChar).incrementAndGet();
			return new ChunkState(callStack, true, false);
		}

		if (!callStack.isEmpty()) {
			System.out.println("incomplete chunk " + chunk + " with remaining stack" + callStack);
			return new ChunkState(callStack, false, true);
		}
		System.out.println("Valid chunk " + chunk);
		return new ChunkState(callStack, false, false);
	}

	private boolean isMatchingClosingChar(String newChar, String lastOnStack) {
		if (closingCharMap.containsKey(newChar)) {
			return closingCharMap.get(newChar).equals(lastOnStack);
		}
		return false;
	}

	private boolean isClosingChar(String newChar) {
		return closingCharMap.containsKey(newChar);
	}
	
	private boolean isOpeningChar(String newChar) {
		return closingCharMap.containsValue(newChar);
	}

	public record ChunkState(Stack<String> stack, boolean isCorrupt, boolean isIncomplete) {
		
	}
}
