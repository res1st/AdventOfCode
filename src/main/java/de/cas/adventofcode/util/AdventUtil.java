package de.cas.adventofcode.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdventUtil {

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

	public static <G> List<G> readFileByLineWithMapping(String folder, String fileName, RowMappingFunction<G> mapping) throws IOException {
		Path path = Paths.get("src", "main", "resources", folder, fileName);

		try (Stream<String> linesStream = Files.lines(path)) {
			return linesStream.map(row -> mapping.apply(row)).collect(Collectors.toList());
		}
	}
}
