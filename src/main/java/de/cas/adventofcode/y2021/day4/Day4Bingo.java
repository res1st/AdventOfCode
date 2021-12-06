package de.cas.adventofcode.y2021.day4;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.linear.RealMatrix;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.Block;

public class Day4Bingo {

	public static void main(String[] args) throws IOException {
		Day4Bingo day4 = new Day4Bingo();
		day4.bingoWithSquid();
	}

	private void bingoWithSquid() throws IOException {
		List<String> data = AdventUtil.readFileByLineAsString("2021", "day4_testinput.txt");
		List<Integer> randomNumbers = AdventUtil.parseToType(data.get(0), ",", Integer::parseInt);

		List<String> bingoTables = data.subList(1, data.size()-1);
		List<Block> blocks = AdventUtil.findBlocks(bingoTables);
		List<RealMatrix> parsedBingoTables = blocks.stream().map(block -> AdventUtil.toRealMatrix(block.toArray(new String[] {}), " ")).collect(Collectors.toList());
//		System.out.println(parsedBingoTables);
		
		Set<Integer> fetchedNumbers = new HashSet<>(randomNumbers.size()*2);
		randomNumbers.stream().forEach(n -> findBingo(n, fetchedNumbers, parsedBingoTables));
	}

	private void findBingo(Integer n, Set<Integer> fetchedNumbers, List<RealMatrix> parsedBingoTables) {
		fetchedNumbers.add(n);
		parsedBingoTables.stream().filter(b -> isBingo(b, fetchedNumbers)).findFirst();
	}

	private boolean isBingo(RealMatrix bingoTable, Set<Integer> fetchedNumbers) {
		Optional<Integer> horizontalBingoLine = checkHorizontal(bingoTable, fetchedNumbers);
		return false;
	}

	private Optional<Integer> checkHorizontal(RealMatrix bingoTable, Set<Integer> fetchedNumbers) {
		for (int i=0; i<bingoTable.getRowDimension(); i++) {
			double[] row = bingoTable.getRow(i);
			Set<Integer> rowAsInt = Arrays.stream(row).boxed().map(d -> d.intValue()).collect(Collectors.toSet());
			if (fetchedNumbers.containsAll(rowAsInt)) {
				System.out.println("Bingo " + rowAsInt);
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}
}
