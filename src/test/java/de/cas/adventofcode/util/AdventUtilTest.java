package de.cas.adventofcode.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;

class AdventUtilTest {

	String matrix = """
			10 83 98 12 33
			38 68  2 99 85
			16 89 54 50 97
			31  8 17 11 76
			 0 55 66 32 87""";

	@Test
	void testParseMatrix() {
		String lines[] = matrix.split("\\r?\\n");
		double[][] parsedMattrix = AdventUtil.parseMatrix(lines, " ");
		System.out.println("Parsed matrix: " + Arrays.deepToString(parsedMattrix));
	}

	@Test
	void testToRealMatrix() {
		String lines[] = matrix.split("\\r?\\n");
		RealMatrix realMatrix = AdventUtil.toRealMatrix(lines, " ");
		System.out.println("RealMatrix: " + realMatrix);
	}
	
	@Test
	void test() {
		Integer[][] testMatrix = new Integer[][] {
			{1,2,3,4,5},
			{11,12,13,15,15},
			{21,22,23,24,25},
			{31,32,33,34,35},
			{41,42,43,44,45}
		};
		
		System.out.println("Test point value is " + testMatrix[0][1]);

		List<Integer> foundSurroundingValues = new ArrayList<>();
		AdventUtil.<Integer>doSurroundingsWithDiagonal(testMatrix, 1, 0, i -> {
			foundSurroundingValues.add(i);
			return printInt(i);
		});
		assertEquals(5, foundSurroundingValues.size());
		assertTrue(foundSurroundingValues.containsAll(Arrays.asList(1, 3, 11, 12, 13)));
	}

	public Integer printInt(Integer a) {
		System.out.println(a);
		return a;
	}
	
}
