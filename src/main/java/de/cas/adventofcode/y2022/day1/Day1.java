package de.cas.adventofcode.y2022.day1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.Block;

public class Day1 {

	public static void main(String[] args) throws IOException {
		Day1 day1 = new Day1();
		List<String> data = AdventUtil.readFileByLineAsString("2022", "day1_input.txt");
		List<Block> blocks = AdventUtil.findBlocks(data);
//		System.out.println("File:\n" + blocks);

		List<Long> sums = day1.sum(blocks);
		OptionalLong max = sums.stream().mapToLong(l -> l).max();
		System.out.printf("Max calories carried is %d %n", max.getAsLong());

		// part2: top3 
		long sumTop3 = sums.stream().sorted(Collections.reverseOrder()).limit(3).mapToLong(l -> l).sum();
		System.out.printf("Top 3 calories are %d", sumTop3);
	}

	private List<Long> sum(List<Block> blocks) {
		return blocks.stream().map(block -> sumSingleBlock(block)).toList();
	}

	private Long sumSingleBlock(Block block) {
		return block.stream().mapToLong(s -> Long.parseLong(s)).sum();
	}

}
