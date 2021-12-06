package de.cas.adventofcode.util;

import java.util.Arrays;

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
}
