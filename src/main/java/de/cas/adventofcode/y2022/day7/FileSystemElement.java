package de.cas.adventofcode.y2022.day7;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public record FileSystemElement(String name, AtomicInteger size, boolean isFile, List<FileSystemElement> subElements, FileSystemElement parent) {


}
