package de.cas.adventofcode.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class AdventUtil {

	public static final int[][] allDirectionsWithDiagonal = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 },
			{ 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 } };

	public static <T> void doSurroundingsWithDiagonal(T[][] matrix, int x, int y,
			Function<T, T> action) {
		for (int[] direction : AdventUtil.allDirectionsWithDiagonal) {
			int mx = x + direction[0];
			int my = y + direction[1];
			if (my >= 0 && my < matrix.length) {
				if (mx >= 0 && mx < matrix[my].length) {
					matrix[my][mx] = action.apply(matrix[my][mx]);
				}
			}
		}
	}

	public static int[] readFileByLineAsInts(String folder, String fileName) throws IOException {
		Path path = Paths.get("src", "main", "resources", folder, fileName);

		try (Stream<String> linesStream = Files.lines(path)) {
			return linesStream.mapToInt(row -> Integer.parseInt(row)).toArray();
		}
	}

	public static List<String> readFileByLineAsString(String folder, String fileName) throws IOException {
		Path path = Paths.get("src", "main", "resources", folder, fileName);

		try (Stream<String> linesStream = Files.lines(path)) {
			return linesStream.collect(Collectors.toList());
		}
	}

	public static <G> List<G> readFileByLineWithMapping(String folder, String fileName, RowMappingFunction<G> mapping)
			throws IOException {
		Path path = Paths.get("src", "main", "resources", folder, fileName);

		try (Stream<String> linesStream = Files.lines(path)) {
			return linesStream.map(row -> mapping.apply(row)).collect(Collectors.toList());
		}
	}

	public static int[][] readFileByLineByCharAsInt(String folder, String fileName) throws IOException {
		Path path = Paths.get("src", "main", "resources", folder, fileName);

		try (Stream<String> linesStream = Files.lines(path)) {
			return linesStream.map(row -> stringCharsToInts(row)).toArray(int[][]::new);
		}
	}

	private static int[] stringCharsToInts(String line) {
		String[] chars = line.split("");
		return Arrays.stream(chars).map(c -> Integer.parseInt(c)).mapToInt(i -> i).toArray();
	}

	public static double[][] parseMatrix(String[] rows, String delimiter) {
		int numberOfRows = rows.length;
		int numberOfColumns = rows[0].split(delimiter).length;
		double[][] matrix = new double[numberOfColumns][numberOfRows];

		for (int i = 0; i < numberOfRows; i++) {
			String rowData = rows[i].trim();
			double[] row = Arrays.stream(rowData.split(delimiter)).filter(n -> StringUtils.isNotBlank(n))
					.mapToDouble(n -> Double.parseDouble(n)).toArray();
			matrix[i] = row;
		}

		return matrix;
	}

	public static int[][] parseIntMatrix(String[] rows, String delimiter) {
		int numberOfRows = rows.length;
		int numberOfColumns = rows[0].split(delimiter).length;
		int[][] matrix = new int[numberOfColumns][numberOfRows];

		for (int i = 0; i < numberOfRows; i++) {
			String rowData = rows[i].trim();
			int[] row = Arrays.stream(rowData.split(delimiter)).filter(n -> StringUtils.isNotBlank(n))
					.mapToInt(n -> Integer.parseInt(n)).toArray();
			matrix[i] = row;
		}

		return matrix;
	}

//	public static <T> T[][] parseTypedMatrix(String[] rows, String delimiter, Function<String, T> mapping) {
//		int numberOfRows = rows.length;
//		int numberOfColumns = rows[0].split(delimiter).length;
//		T[][] matrix = new T[numberOfColumns][numberOfRows];
//
//		for(int i=0; i<numberOfRows; i++) {
//			String rowData = rows[i].trim();
//			T[] row = Arrays.stream(rowData.split(delimiter)).filter(n -> StringUtils.isNotBlank(n)).map(n -> mapping.apply(n)).toArray();
//			matrix[i] = row;
//		}
//		
//		return matrix;
//	}

	public static RealMatrix toRealMatrix(String[] rows, String delimiter) {
		double[][] parsedMatrix = parseMatrix(rows, delimiter);
		Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(parsedMatrix);
		return matrix;
	}

//	public static <T> Array2DRowFieldMatrix toIntMatrix(String[] rows, String delimiter) {
//		Integer[][] parsedMatrix = parseIntMatrix(rows, delimiter);
//		Array2DRowFieldMatrix<Integer> matrix = new Array2DRowFieldMatrix<Integer>(parsedMatrix);
//		return matrix;
//	}

	public static <R> List<R> parseToType(String data, String separator, Function<String, R> mapping) {
		return Arrays.stream(data.split(separator)).map(s -> StringUtils.trim(s)).map(s -> mapping.apply(s))
				.collect(Collectors.toList());
	}

	public static List<Block> findBlocks(List<String> data) {
		List<Block> blocks = new ArrayList<>();
		Block newBlock = new Block();

		for (int i = 0; i < data.size(); i++) {
			String line = data.get(i);
			if (StringUtils.isNoneBlank(line)) {
				newBlock.add(line);
			} else if (!newBlock.isEmpty()) {
				blocks.add(newBlock);
				newBlock = new Block();
			}
		}
		return blocks;
	}

	public static <T> Stream<T> reverse(Stream<T> stream) {
		LinkedList<T> stack = new LinkedList<>();
		stream.forEach(stack::push);
		return stack.stream();
	}

}
