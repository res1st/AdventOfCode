package de.cas.adventofcode.y2021.day16;

import java.io.IOException;
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
//		decodedSum = day16.decode(AdventUtil.hexToBinary("EE00D40C823060"));
//		System.out.println("Sum of versions: " + decodedSum);

		String inputTest;
//		inputTest = AdventUtil.hexToBinary("8A004A801A8002F478");
//		System.out.println("input: " + inputTest);
//		decodedSum = day16.decode(inputTest);
//		System.out.println("Sum of versions: " + decodedSum + " for " + "8A004A801A8002F478");

		// represents an operator packet (version 3) which contains two sub-packets; each sub-packet is an operator packet that contains two literal values. This packet has a version sum of 12.
		inputTest = AdventUtil.hexToBinary("620080001611562C8802118E34");
		System.out.println("input: " + inputTest);
		decodedSum = day16.decode(inputTest);
		System.out.println("Sum of versions: " + decodedSum + " for " + "620080001611562C8802118E34");


//		inputTest = AdventUtil.hexToBinary("C0015000016115A2E0802F182340");
//		System.out.println("input: " + inputTest);
//		decodedSum = day16.decode(inputTest);
//		System.out.println("Sum of versions: " + decodedSum + " for " + "C0015000016115A2E0802F182340");
//		
//		inputTest = AdventUtil.hexToBinary("A0016C880162017C3686B18A3D4780");
//		System.out.println("input: " + inputTest);
//		decodedSum = day16.decode(inputTest);
//		System.out.println("Sum of versions: " + decodedSum + " for " + "A0016C880162017C3686B18A3D4780");
		
//		List<String> input = AdventUtil.readFileByLineAsString("2021", "day16_input.txt");
//		decodedSum = day16.decode(AdventUtil.hexToBinary(input.get(0)));
//		System.out.println("Sum of versions: " + decodedSum);
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
			System.out.println("version:" +  version + " typeId:" + typeId);

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
				System.out.println("Decoded literal is " + AdventUtil.binaryToDecimal(literalString.toString()));
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
					sumOfVersion += decode(packetString.substring(i, i + numberOfSubPacketsOrBits));
					i += numberOfSubPacketsOrBits;
				} else {
					// 11
					System.out.println(numberOfSubPacketsOrBits + " immediately embedded sub-packes without headers ");
					i += (numberOfSubPacketsOrBits*11);
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
}
