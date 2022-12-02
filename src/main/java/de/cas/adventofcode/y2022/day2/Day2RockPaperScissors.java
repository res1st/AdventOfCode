package de.cas.adventofcode.y2022.day2;

import java.io.IOException;
import java.util.List;

import de.cas.adventofcode.util.AdventUtil;

public class Day2RockPaperScissors {

	public static void main(String[] args) throws IOException {
		List<Character[]> data = AdventUtil.readFileByLineWithMapping("2022", "day2_input.txt",
				row -> new Character[] { row.charAt(0), row.charAt(2) });

		long totalScore = data.stream().map(singleGame -> calculatePoints(singleGame)).mapToLong(l -> l).sum();
		System.out.printf("The total score of %d played games is %d %n", data.size(), totalScore);
		
		// part 2: X means loose, Y means draw and Z means win
		long totalScorePart2 = data.stream().map(singleGame -> calculatePointsPart2(singleGame)).mapToLong(l -> l).sum();
		System.out.printf("The total score PART TWO of %d played games is %d", data.size(), totalScorePart2);
	}

	private static long calculatePoints(Character[] singleGame) {
		char oppMove = singleGame[0];
		char myMove = singleGame[1];

		long scoreMyMove = calculateScoreOfMyMove(myMove);
		long scoreWinLoss = calculateScoreWinLoss(myMove, oppMove);
		return scoreMyMove + scoreWinLoss;
	}

	private static long calculatePointsPart2(Character[] singleGame) {
		char oppMove = singleGame[0];
		char requiredResult = singleGame[1];

		char calculatedMove = calculateMyMove(requiredResult, oppMove);
		long scoreMyMove = calculateScoreOfMyMove(calculatedMove);
		
		long scoreWinLoss = switch (requiredResult) {
		case 'X' -> 0;
		case 'Y' -> 3;
		case 'Z' -> 6;
		default ->
		throw new IllegalArgumentException("Unexpected value: " + requiredResult);
		};
		
		return scoreMyMove + scoreWinLoss;
	}

	private static long calculateScoreWinLoss(char myMove, char oppMove) {
		long scoreWinLoss = switch (myMove) {
		case 'X' -> switch (oppMove) {
			case 'A' -> 3;
			case 'B' -> 0;
			case 'C' -> 6;
			default -> throw new IllegalArgumentException("Unexpected value: " + oppMove);
			};
		case 'Y' -> switch (oppMove) {
			case 'A' -> 6;
			case 'B' -> 3;
			case 'C' -> 0;
			default -> throw new IllegalArgumentException("Unexpected value: " + oppMove);
			};
		case 'Z' -> switch (oppMove) {
			case 'A' -> 0;
			case 'B' -> 6;
			case 'C' -> 3;
			default -> throw new IllegalArgumentException("Unexpected value: " + oppMove);
			};
		default -> throw new IllegalArgumentException("Unexpected value: " + myMove);
		};
		return scoreWinLoss;
	}

	// X means loose, Y means draw and Z means win
	private static char calculateMyMove(char requiredResult, char oppMove) {
		return switch (requiredResult) {
		case 'X' -> switch (oppMove) {
			case 'A' -> 'Z';
			case 'B' -> 'X';
			case 'C' -> 'Y';
			default -> throw new IllegalArgumentException("Unexpected value: " + oppMove);
			};
		case 'Y' -> switch (oppMove) {
			case 'A' -> 'X';
			case 'B' -> 'Y';
			case 'C' -> 'Z';
			default -> throw new IllegalArgumentException("Unexpected value: " + oppMove);
			};
		case 'Z' -> switch (oppMove) {
			case 'A' -> 'Y';
			case 'B' -> 'Z';
			case 'C' -> 'X';
			default -> throw new IllegalArgumentException("Unexpected value: " + oppMove);
			};
		default -> throw new IllegalArgumentException("Unexpected value: " + requiredResult);
		};
	}

	private static long calculateScoreOfMyMove(char myMove) {
		long scoreMyMove = switch (myMove) {
		case 'X' -> 1;
		case 'Y' -> 2;
		case 'Z' -> 3;

		default -> throw new IllegalArgumentException("Unexpected value: " + myMove);
		};
		return scoreMyMove;
	}

}
