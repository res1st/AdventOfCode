package de.cas.adventofcode.y2021.day3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.BitUtil;

public class Day3BinaryDiagnostic {

	private String FILENAME = "day3_testinput.txt";

	public static void main(String[] args) throws IOException {
		Day3BinaryDiagnostic day3 = new Day3BinaryDiagnostic();
//		day3.puzzlePart1();
//		day3.puzzlePart2();
		day3.puzzlePart1WithBitSet();
	}

	private void puzzlePart1WithBitSet() throws IOException {
		int inputDataLength = getInputDataLength(FILENAME);

		List<BitSet> inputData = AdventUtil.readFileByLineWithMapping("2021", FILENAME,
				row -> BitUtil.fromStringReverseToBitSet(row));
		System.out.println(inputData);

		int[] oneBitsPerPosition = new int[inputDataLength];
		inputData.stream().forEach(binrayRow -> countOnes(binrayRow, oneBitsPerPosition, inputDataLength));
		System.out.println("1 bits per position: " + Arrays.toString(oneBitsPerPosition));

		BitSet gammaRate = calculateGammaRate2(oneBitsPerPosition, inputData.size(), inputDataLength);
		long gammaRateLong = BitUtil.toLong(gammaRate);
		System.out.println("Gamme rate:" + gammaRate + " => " + gammaRateLong);

		BitSet epsilonRate = BitUtil.inverseBitmask(gammaRate, inputDataLength);
		long epsilonRateLong = BitUtil.toLong(epsilonRate);
		System.out.println("epsilon rate:" + epsilonRateLong);

		System.out.println("Result part 1: " + (gammaRateLong*epsilonRateLong));
	}

	private BitSet calculateGammaRate2(int[] oneBitsPerPosition, int totalProbes, int inputDataLength) {
		BitSet gammaRate = new BitSet(inputDataLength);
		for (int i = 0; i < inputDataLength; i++) {
			int numberOfOnes = oneBitsPerPosition[i];
			if (numberOfOnes > (totalProbes / 2)) {
				gammaRate.set(i);
			}
		}
		return gammaRate;
	}

	private void countOnes(BitSet binrayRow, int[] oneBits, int inputDataLength) {
		for (int i = 0; i < inputDataLength; i++) {
			if (isOneAtPosition(binrayRow, i)) {
				oneBits[i]++;
			}
		}
	}

	private boolean isOneAtPosition(BitSet number, int position) {
		return number.get(position);
	}

	private void puzzlePart2() throws IOException {
		int inputDataLength = getInputDataLength(FILENAME);
		List<Integer> inputData = AdventUtil.readFileByLineWithMapping("2021", FILENAME,
				row -> parseBinaryToInt(row));


		List<Integer> filteredInputData = new ArrayList<>(inputData);
		int oxygenGeneratorRating = 0;
		for (int i=inputDataLength-1; i>=0; i--) {
			int[] oneBitsPerPosition = count1Bits(inputDataLength, filteredInputData);

			boolean keepOnes = isKeepOneValues(oneBitsPerPosition[i], filteredInputData.size());

			filteredInputData = filterForPosition(i, keepOnes, filteredInputData);

			System.out.println("inputs left " + filteredInputData);
			System.out.println("inputs left " + printIntListAsBitMask(filteredInputData));
			if (filteredInputData.size() == 1)   {
				oxygenGeneratorRating = filteredInputData.get(0);
				System.out.println("oxygen generator rating is " + oxygenGeneratorRating);
				break;
			}
		}

		filteredInputData = new ArrayList<>(inputData);
		int co2ScrubberRating = 0;
		for (int i=inputDataLength-1; i>=0; i--) {
			int[] oneBitsPerPosition = count1Bits(inputDataLength, filteredInputData);

			boolean keepOnes = !isKeepOneValues(oneBitsPerPosition[i], filteredInputData.size());

			filteredInputData = filterForPosition(i, keepOnes, filteredInputData);

			System.out.println("inputs left " + filteredInputData);
			System.out.println("inputs left " + printIntListAsBitMask(filteredInputData));
			if (filteredInputData.size() == 1)   {
				co2ScrubberRating = filteredInputData.get(0);
				System.out.println("CO2 scrubber rating is " + co2ScrubberRating);
				break;
			}
		}
		
		System.out.println("Result puzzle part 2: " + (oxygenGeneratorRating*co2ScrubberRating));
	}

	private int[] count1Bits(int inputDataLength, List<Integer> filteredInputData) {
		int[] oneBitsPerPosition = new int[inputDataLength];
		filteredInputData.stream().forEach(binrayRow -> countOnes(binrayRow, oneBitsPerPosition, inputDataLength));
		System.out.println("1 bits per position: " + Arrays.toString(oneBitsPerPosition));
		return oneBitsPerPosition;
	}

	private String printIntListAsBitMask(List<Integer> data) {
		return data.stream().map(number -> printIntAsBitmask(number)).collect(Collectors.toList()).toString();
	}

	private String printIntAsBitmask(int number) {
		return Integer.toString(number, 2);
	}

	private List<Integer> filterForPosition(int i, boolean keepOnes, List<Integer> filteredInputData) {
		Iterator<Integer> inputDataIterator = filteredInputData.iterator();
		while (inputDataIterator.hasNext() && filteredInputData.size() > 1) {
			Integer binaryData = inputDataIterator.next();
			boolean oneAtPosition = isOneAtPosition(binaryData, i+1);
			System.out.println("Filtering check for " + printIntAsBitmask(binaryData));
			if (oneAtPosition != keepOnes) {
				inputDataIterator.remove();
			}
		}
		return filteredInputData;
	}

	private boolean isKeepOneValues(int numberOfOnes, int inputDataLength) {
		return numberOfOnes >= ((float)inputDataLength / 2);
	}

	private void puzzlePart1() throws IOException {
		int inputDataLength = getInputDataLength(FILENAME);

		List<Integer> inputData = AdventUtil.readFileByLineWithMapping("2021", FILENAME,
				row -> parseBinaryToInt(row));
		System.out.println(inputData);

		int[] oneBitsPerPosition = new int[inputDataLength];
		inputData.stream().forEach(binrayRow -> countOnes(binrayRow, oneBitsPerPosition, inputDataLength));
		System.out.println("1 bits per position: " + Arrays.toString(oneBitsPerPosition));

		int gammaRate = calculateGammaRate(oneBitsPerPosition, inputData.size(), inputDataLength);
		System.out.println("Gamme rate:" + gammaRate);

		int epsilonRate = inverseBitmask(gammaRate, inputDataLength);
		System.out.println("epsilon rate:" + epsilonRate);

		System.out.println("Result part 1: " + (gammaRate*epsilonRate));
	}

	private int getInputDataLength(String fileName) throws IOException {
		List<String> inputDataString = AdventUtil.readFileByLineAsString("2021", fileName);
		int inputDataLength = inputDataString.get(0).length();
		return inputDataLength;
	}

	private int calculateGammaRate(int[] oneBitsPerPosition, int totalProbes, int inputDataLength) {
		int gammaRate = 0;
		for (int i = 0; i < inputDataLength; i++) {
			int numberOfOnes = oneBitsPerPosition[i];
			if (numberOfOnes > (totalProbes / 2)) {
				gammaRate = setBit(i, gammaRate);
			}
		}
		return gammaRate;
	}

	private int inverseBitmask(int bitmask, int lengthOfMask) {
		int mask = (1 << lengthOfMask) - 1;
		// int mask =  0b1111; // 15
		return ~bitmask &  mask;
	}

	public int setBit(int bit, int number) {
		int mask = 1 << bit;
		return number | mask;
	}

	private int applyBitMask(int number, int mask) {
		int masked = number & mask;
		return masked;
	}

	private void countOnes(Integer binrayRow, int[] oneBits, int inputDataLength) {
		for (int i = 0; i < inputDataLength; i++) {
			if (isOneAtPosition(binrayRow, i+1)) {
				oneBits[i]++;
			}
		}
	}

	private int parseBinaryToInt(String stringRepresentation) {
		int intRepresentation = Integer.parseUnsignedInt(stringRepresentation, 2);
		return intRepresentation;
	}

	private boolean isOneAtPosition(int number, int position) {
		return ((number) & (1 << (position - 1))) != 0;
	}
}
