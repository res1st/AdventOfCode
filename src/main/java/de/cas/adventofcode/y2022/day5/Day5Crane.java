package de.cas.adventofcode.y2022.day5;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.Block;

public class Day5Crane {

	private static final int TOTAL_STACKS = 9;

	public static void main(String[] args) throws IOException {
		List<String> data = AdventUtil.readFileByLineAsString("2022", "day5_input.txt");
		List<Block> blocks = AdventUtil.findBlocks(data);

		Deque<Character>[] stacks = IntStream.range(0, TOTAL_STACKS).boxed().map(i -> new ArrayDeque<Character>()).toArray(ArrayDeque[]::new);
		
		setStartConfigurationn(stacks, blocks.get(0));
		System.out.println("Initilaized with " + Arrays.toString(stacks));

		doCraneOperations(stacks, blocks.get(1));
		System.out.println("Done " + Arrays.toString(stacks));
	}

	private static void doCraneOperations(Deque<Character>[] stacks, Block operations) {
		operations.stream().map(line -> parseOperations(line)).forEach(op -> doOperation(op, stacks));;
	}

	private static Void doOperation(Operation op, Deque<Character>[] stacks) {
		// PART 1: IntStream.range(0, op.numElements()).boxed().forEach(i -> stacks[op.to()].push(stacks[op.from()].pop()));
		// PART 2: 
		List<Character> poppedCrates = IntStream.range(0, op.numElements()).boxed().map(i -> (Character) stacks[op.from()].pop()).toList();
		
		for (int i=poppedCrates.size()-1; i>=0; i--) {
			stacks[op.to()].push(poppedCrates.get(i));
		}
		
		return null;
	}

	private static Operation parseOperations(String line) {
		if (line.isBlank()) return null;
		String[] splitted = line.split(" ");
		
		return new Operation(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[3])-1, Integer.parseInt(splitted[5])-1); 
	}

	private static void setStartConfigurationn(Deque<Character>[] stacks, Block block) {
		block.subList(0, block.size()-1).stream().forEach(line -> parseLine(line, stacks));
	}

	private static Void parseLine(String line, Deque<Character>[] stacks) {
		for (int i=1, stackNr=0; i<line.length(); i=i+4, stackNr++) {
			if (line.charAt(i) == ' ') continue;
			stacks[stackNr].addLast(line.charAt(i));
		}
		return null;
	}
}
