package de.cas.adventofcode.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class AdventUtil {

	public static final int[][] allDirectionsWithDiagonal = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 },
			{ 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 } };

	public static final int[][] allDirections = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

	public static <T> void doSurroundingsWithDiagonal(T[][] matrix, int x, int y, Function<T, T> action) {
		doSurroundings(matrix, x, y, action, allDirectionsWithDiagonal);
	}

	public static <T> void doSurroundings(T[][] matrix, int x, int y, Function<T, T> action) {
		doSurroundings(matrix, x, y, action, allDirections);
	}

	private static <T> void doSurroundings(T[][] matrix, int x, int y, Function<T, T> action, int[][] validDirections) {
		for (int[] direction : validDirections) {
			int mx = x + direction[0];
			int my = y + direction[1];
			if (my >= 0 && my < matrix.length) {
				if (mx >= 0 && mx < matrix[my].length) {
					matrix[my][mx] = action.apply(matrix[my][mx]);
				}
			}
		}
	}

	public static <T, R> List<R> iterate2dArray(T[][] matrix, BiFunction<T[][], ImmutablePoint, R> convertFunction) {
		List<R> resultList = new ArrayList<R>();
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				R conversionResult = convertFunction.apply(matrix, new ImmutablePoint(x, y));
				resultList.add(conversionResult);
			}
		}
		return resultList;
	}

//	public static List<Integer> getCoordinatesAroundPoint(int x, int y, int maxX, int maxY) {
//		List<ImmutablePoint> coordinates = new ArrayList<>(4);
//		
//	}

	/**
	 * FIXME: bi function with coord
	 */
	public static <T, R> List<R> convertSurroundings(T[][] matrix, int x, int y, Function<T, R> action,
			int[][] validDirections) {
		List<R> createdElements = new ArrayList<>();
		for (int[] direction : validDirections) {
			int mx = x + direction[0];
			int my = y + direction[1];
			if (my >= 0 && my < matrix.length) {
				if (mx >= 0 && mx < matrix[my].length) {
					createdElements.add(action.apply(matrix[my][mx]));
				}
			}
		}
		return createdElements;
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

	public static Integer[][] readFileByLineByCharAsInteger(String folder, String fileName) throws IOException {
		Path path = Paths.get("src", "main", "resources", folder, fileName);

		try (Stream<String> linesStream = Files.lines(path)) {
			return linesStream.map(row -> stringCharsToIntegers(row)).toArray(Integer[][]::new);
		}
	}

	private static int[] stringCharsToInts(String line) {
		String[] chars = line.split("");
		return Arrays.stream(chars).map(c -> Integer.parseInt(c)).mapToInt(i -> i).toArray();
	}

	private static Integer[] stringCharsToIntegers(String line) {
		String[] chars = line.split("");
		return Arrays.stream(chars).map(c -> Integer.valueOf(c)).toArray(Integer[]::new);
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

	public static List<List<String>> splitAtEmptyLines(List<String> input) {
		List<List<String>> splittedLines = new ArrayList<>();
		splittedLines.add(new ArrayList<>());

		for (String line : input) {
			if (StringUtils.isBlank(line)) {
				splittedLines.add(new ArrayList<>());
				continue;
			}

			List<String> lastCreatedList = splittedLines.get(splittedLines.size() - 1);
			lastCreatedList.add(line);
		}

		splittedLines = removeEmptyLists(splittedLines);
		return splittedLines;
	}

	private static List<List<String>> removeEmptyLists(List<List<String>> splittedLines) {
		splittedLines = splittedLines.stream().filter(l -> !l.isEmpty()).collect(Collectors.toList());
		return splittedLines;
	}

	public static void writePointsAsImage(Points lastPoints, String fileName) throws IOException {
		BufferedImage img = new BufferedImage(lastPoints.getMaxX() + 1, lastPoints.getMaxY() + 1,
				BufferedImage.TYPE_INT_RGB);
		lastPoints.stream().forEach(p -> setPixel(p, img));

		File file = new File(fileName);
		ImageIO.write(img, "png", file);
	}

	private static void setPixel(Point p, BufferedImage img) {
		img.setRGB(p.x, p.y, 0xFFFFFF);
	}

	/**
	 * Each character of hexadecimal corresponds to four bits of binary data.
	 */
	public static String hexToBinary4Bits(String hexString) {
		return String.format("%" + (hexString.length() * 4) + "s", hexToBinary(hexString)).replace(" ", "0");
	}

	public static String hexToBinary(String hexString) {
		return new BigInteger(hexString, 16).toString(2);
	}

	public static int binaryToDecimal(String binaryString) {
		return Integer.parseInt(binaryString, 2);
	}

	public static long binaryToDecimalLong(String binaryString) {
		return Long.parseLong(binaryString, 2);
	}

}
