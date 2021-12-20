package de.cas.adventofcode.y2021.day16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.cas.adventofcode.util.AdventUtil;

public class Day16PacketDecoder {

	private static final int TYPE_LITERAL = 4;

	public static void main(String[] args) throws IOException {
		Day16PacketDecoder day16 = new Day16PacketDecoder();

		int decodedSum = 0;
//		decodedSum = day16.decode("110100101111111000101000");
//		System.out.println("Sum of versions: " + decodedSum);
//		decodedSum = day16.decode("00111000000000000110111101000101001010010001001000000000");
//		System.out.println("Sum of versions: " + decodedSum);

//		decodedSum = day16.decode("11101110000000001101010000001100100000100011000001100000");
//		decodedSum = day16.decode(AdventUtil.hexToBinary4Bits("EE00D40C823060"));
//		System.out.println("Sum of versions: " + decodedSum);

		String inputTest;
//		inputTest = AdventUtil.hexToBinary4Bits("8A004A801A8002F478");
//		System.out.println("input: " + inputTest);
//		decodedSum = day16.decode(inputTest);
//		System.out.println("Sum of versions: " + decodedSum + " for " + "8A004A801A8002F478");

		// represents an operator packet (version 3) which contains two sub-packets;
		// each sub-packet is an operator packet that contains two literal values. This
		// packet has a version sum of 12.
//		inputTest = AdventUtil.hexToBinary4Bits("620080001611562C8802118E34");
//		System.out.println("input: " + inputTest);
//		decodedSum = day16.decode(inputTest);
//		System.out.println("Sum of versions: " + decodedSum + " for " + "620080001611562C8802118E34");

//		inputTest = AdventUtil.hexToBinary4Bits("C0015000016115A2E0802F182340");
//		System.out.println("input: " + inputTest);
//		decodedSum = day16.decode(inputTest);
//		System.out.println("Sum of versions: " + decodedSum + " for " + "C0015000016115A2E0802F182340");
//		
//		inputTest = AdventUtil.hexToBinary4Bits("A0016C880162017C3686B18A3D4780");
//		System.out.println("input: " + inputTest);
//		decodedSum = day16.decode(inputTest);
//		System.out.println("Sum of versions: " + decodedSum + " for " + "A0016C880162017C3686B18A3D4780");

//		inputTest = AdventUtil.hexToBinary4Bits("C200B40A82");
//		System.out.println("input: " + inputTest);
//		List<Long> resultPart2 = day16.decodePart2(inputTest, Integer.MAX_VALUE);
////		resultPart2.stream().su
//		System.out.println(" * part 2 calc: " + resultPart2); // 3

//		List<String> input = AdventUtil.readFileByLineAsString("2021", "day16_input.txt");
//		decodedSum = day16.decode(AdventUtil.hexToBinary4Bits(input.get(0)));
//		System.out.println("Sum of versions: " + decodedSum);
		
		// part 2
//		inputTest = AdventUtil.hexToBinary4Bits("04005AC33890");
//		System.out.println("input: " + inputTest);
//		List<Long> resultPart2_1 = day16.decodePart2(inputTest, Integer.MAX_VALUE);
//		System.out.println(" * part 2 calc: " + resultPart2_1); // 54
//
//		inputTest = AdventUtil.hexToBinary4Bits("880086C3E88112");
//		System.out.println("input: " + inputTest);
//		List<Long> resultPart2_2 = day16.decodePart2(inputTest, Integer.MAX_VALUE);
//		System.out.println(" * part 2 calc: " + resultPart2_2); // 7
//
//		inputTest = AdventUtil.hexToBinary4Bits("CE00C43D881120");
//		System.out.println("input: " + inputTest);
//		List<Long> resultPart2_3 = day16.decodePart2(inputTest, Integer.MAX_VALUE);
//		System.out.println(" * part 2 calc: " + resultPart2_3); // 9

		List<String> input = AdventUtil.readFileByLineAsString("2021", "day16_input.txt");
		List<Long> resultPart2Final = day16.decodePart2(AdventUtil.hexToBinary4Bits(input.get(0)), Integer.MAX_VALUE);
		System.out.println("* part 2 final calc: " + resultPart2Final);
	}

	private int decode(String packetString) {
		int sumOfVersion = 0;
		for (int i = 0; i < packetString.length();) {
			if (onlyZeroRemaining(packetString, i)) {
				break;
			}
			String substring = packetString.substring(i, i + 3);
			int version = AdventUtil.binaryToDecimal(substring);
			sumOfVersion += version;

			String typeIdString = packetString.substring(i + 3, i + 6);
			int typeId = AdventUtil.binaryToDecimal(typeIdString);
			System.out.println("version:" + version + " typeId:" + typeId);

			// skip version and typeId
			i += 6;

			switch (typeId) {
			case TYPE_LITERAL: {
				StringBuilder literalString = new StringBuilder();
				do {
					String numberString = packetString.substring(i + 1, i + 5);
					System.out.println("Decoded " + numberString);
					literalString.append(numberString);

					i += 5; // consumed
				} while (packetString.charAt(i - 5) == '1');
				System.out.println("Decoded literal is " + AdventUtil.binaryToDecimalLong(literalString.toString()));
				break;
			}
			default:
				int length11or15 = getLengthOfCommand(packetString, i);
				System.out.println("Operator command length " + length11or15);
				i++; // consumed

				String numberOfBitsInSubPacketsString = packetString.substring(i, i + length11or15);
				System.out.println("numberOfBitsInSubPacketsString: " + numberOfBitsInSubPacketsString);
				int numberOfSubPacketsOrBits = AdventUtil.binaryToDecimal(numberOfBitsInSubPacketsString);
				i += length11or15;
				System.out.println("numberOfSubPacketsOrBits: " + numberOfSubPacketsOrBits);

				if (length11or15 == 15) {
					sumOfVersion += decode(packetString.substring(i, (i + numberOfSubPacketsOrBits)));
					i += numberOfSubPacketsOrBits;
				} else {
					// 11
//					System.out.println(numberOfSubPacketsOrBits + " immediately embedded sub-packes without headers ");
//					i += (numberOfSubPacketsOrBits*11);
				}
			}

		}

		System.out.println("Sum: " + sumOfVersion);
		return sumOfVersion;
	}

	private int getLengthOfCommand(String packetString, int i) {
		boolean longCommand = packetString.charAt(i) == '0';
		if (longCommand) {
			return 15;
		} else {
			return 11;
		}
	}

	private boolean onlyZeroRemaining(String packetString, int i) {
		return packetString.substring(i).chars().allMatch(c -> c == '0');
	}

	private long calc(List<Long> literals, int operator) {
		switch (operator) {
		case 0:
			return literals.stream().mapToLong(e -> e).sum();
		case 1:
			return literals.stream().mapToLong(e -> e).reduce((a, b) -> a * b).getAsLong();
		case 2:
			return literals.stream().mapToLong(e -> e).min().getAsLong();
		case 3:
			return literals.stream().mapToLong(e -> e).max().getAsLong();
		case 5:
			// exactly two sub-packets
			return literals.get(0) > literals.get(1) ? 1L : 0L;
		case 6:
			// exactly two sub-packets
			return literals.get(0) < literals.get(1) ? 1L : 0L;
		case 7:
			// exactly two sub-packets
			return literals.get(0).equals(literals.get(1)) ? 1L : 0L;
		default:
			throw new IllegalArgumentException("Unexpected operator: " + operator);
		}
	}

	int previousIndexI = 0;

	private List<Long> decodePart2(String packetString, int parseLength) {
		List<Long> literals = new ArrayList<>();

		for (int i = 0, parsed = 0; i < packetString.length(); parsed++) {

			if (parsed >= parseLength) {
				break;
			}

			if (onlyZeroRemaining(packetString, i)) {
				break;
			}
			String substring = packetString.substring(i, i + 3);
			int version = AdventUtil.binaryToDecimal(substring);

			String typeIdString = packetString.substring(i + 3, i + 6);
			int typeId = AdventUtil.binaryToDecimal(typeIdString);
			System.out.println("version:" + version + " typeId:" + typeId);

			// skip version and typeId
			i += 6;

			switch (typeId) {
			case TYPE_LITERAL: {
				StringBuilder literalString = new StringBuilder();
				do {
					String numberString = packetString.substring(i + 1, i + 5);
					System.out.println("Decoded " + numberString);
					literalString.append(numberString);

					i += 5; // consumed
				} while (packetString.charAt(i - 5) == '1');
				System.out.println("Decoded literal is " + AdventUtil.binaryToDecimalLong(literalString.toString()));
				literals.add(AdventUtil.binaryToDecimalLong(literalString.toString()));
				break;
			}
			default:
				int length11or15 = getLengthOfCommand(packetString, i);
				System.out.println("Operator command length " + length11or15);
				i++; // consumed

				String numberOfBitsInSubPacketsString = packetString.substring(i, i + length11or15);
				System.out.println("numberOfBitsInSubPacketsString: " + numberOfBitsInSubPacketsString);
				int numberOfSubPacketsOrBits = AdventUtil.binaryToDecimal(numberOfBitsInSubPacketsString);
				i += length11or15;
				System.out.println("numberOfSubPacketsOrBits: " + numberOfSubPacketsOrBits);

				if (length11or15 == 11) {
					List<Long> receivedLiterals = decodePart2(packetString.substring(i, packetString.length()),
							numberOfSubPacketsOrBits);
					literals.add(calc(receivedLiterals, typeId));
					i += previousIndexI;
				} else {
					List<Long> receivedLiterals = decodePart2(packetString.substring(i, i + numberOfSubPacketsOrBits),
							Integer.MAX_VALUE);
					literals.add(calc(receivedLiterals, typeId));
					i += numberOfSubPacketsOrBits;
				}
			}
			previousIndexI = i;
		}

		return literals;
	}
}
