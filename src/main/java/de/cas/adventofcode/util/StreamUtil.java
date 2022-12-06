package de.cas.adventofcode.util;

import java.util.List;

public class StreamUtil {

	public static List<Character> convertStringToCharList(String string) {
		return string.chars()
				.mapToObj(c -> (char) c)
				.toList();
	}

}
