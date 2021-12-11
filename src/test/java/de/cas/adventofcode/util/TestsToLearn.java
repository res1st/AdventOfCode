package de.cas.adventofcode.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class TestsToLearn {

	/**
	 * supplier: creates target object that collects later the elements of the stream
	 * 
	 * accumulator: describes how to collect an element of the stream in the target object
	 * 
	 * combiner: only used for parallel streams! how combine two target objects
	 */
	@Test
	void reduceTest() {
		Stream<String> zeroToNineStream = createNumberStream();

		String concatReduce = zeroToNineStream.reduce("", (prev, number) -> prev + number);
		assertEquals("0123456789", concatReduce);

		// supplier, accumulator, combiner
		String concatCollect = createNumberStream()
				.collect(() -> new StringBuilder(),
						(StringBuilder target, String streamedElement) -> target.append(streamedElement),
						(StringBuilder target1, StringBuilder target2) -> target1.append(target2))
				.toString();
		assertEquals(concatReduce, concatCollect);

		String concatCollectMethodRefs = createNumberStream()
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
		assertEquals(concatCollect, concatCollectMethodRefs);

		String concatCollectShortCut = createNumberStream()
				.collect(Collectors.joining());
		assertEquals(concatCollectMethodRefs, concatCollectShortCut);
	}

	private Stream<String> createNumberStream() {
		return IntStream.range(0, 10).mapToObj(Integer::toString);
	}

}
