package de.cas.adventofcode.y2022.day1;

import java.io.IOException;
import java.util.Arrays;

import de.cas.adventofcode.util.AdventUtil;

public class Day1 {

	public static void main(String[] args) throws IOException {
		Day1 day1 = new Day1();
		Integer[][] cave = AdventUtil.readFileByLineByCharAsInteger("2022", "");
		System.out.println("File:\n" + Arrays.deepToString(cave));
//		day1.findlowsetRisk(cave);

	}

}
