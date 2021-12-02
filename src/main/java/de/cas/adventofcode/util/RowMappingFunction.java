package de.cas.adventofcode.util;

import java.util.function.Function;

/**
 * Used to map a single string of a file to an object
 * @author Ingo.Siebert
 *
 * @param <A> result type of mapping, i.e. Course
 */
public interface RowMappingFunction<A> extends Function<String, A> {

}
