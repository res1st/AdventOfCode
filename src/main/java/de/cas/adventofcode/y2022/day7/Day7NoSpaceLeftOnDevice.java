package de.cas.adventofcode.y2022.day7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import de.cas.adventofcode.util.AdventUtil;

public class Day7NoSpaceLeftOnDevice {

	private static final int MAX_FOLDER_SIZE = 100000;
	private static final int FS_TOTAL_SIZZE = 70000000;
	private static final int REQUIRED_SPACE = 30000000;

	public static void main(String[] args) throws IOException {
		List<String> consoleOutput = AdventUtil.readFileByLineAsString("2022", "day7_input.txt");
		System.out.println("Input " + consoleOutput);

		FileSystemElement root = new FileSystemElement("ROOTNODE", new AtomicInteger(0), false, new ArrayList<>(),
				null);
		root.subElements().add(new FileSystemElement("/", new AtomicInteger(0), false, new ArrayList<>(), root));
		parseConsoleOutput(consoleOutput, root);
		List<FileSystemElement> folders = getAllFolders(root);
		int part1Sum = folders.stream().filter(f -> f.size().get() < MAX_FOLDER_SIZE).mapToInt(f -> f.size().get())
				.sum();
		System.out.println("Part 1 sum: " + part1Sum);
		
		FileSystemElement findSmallestFolderThatFreesEnoughDiskSpace = findSmallestFolderThatFreesEnoughDiskSpace(root, folders);
		System.out.println("Part 2 size: " + findSmallestFolderThatFreesEnoughDiskSpace.size());
	}

	private static FileSystemElement findSmallestFolderThatFreesEnoughDiskSpace(FileSystemElement root, List<FileSystemElement> dirs) {
		int availableSpace = FS_TOTAL_SIZZE - root.size().get();
		int required = REQUIRED_SPACE - availableSpace;
		return dirs.stream().filter(d -> d.size().get() >= required).min((a, b) -> a.size().get() - b.size().get()).orElseThrow();
	}

	private static List<FileSystemElement> getAllFolders(FileSystemElement root) {
		List<FileSystemElement> folders = new ArrayList<>();

		for (var fsElement : root.subElements()) {
			if (!fsElement.isFile()) {
				folders.add(fsElement);
				folders.addAll(getAllFolders(fsElement));
			}
		}

		return folders;
	}

	private static void parseConsoleOutput(List<String> consoleOutput, FileSystemElement root) {
		FileSystemElement current = root;

		for (int i = 0; i < consoleOutput.size(); i++) {
			current = processLine(consoleOutput.get(i), current);
		}

		calculateSizes(root);
	}

	private static void calculateSizes(FileSystemElement current) {
		current.subElements().stream().forEach(e -> calculateSizes(e));

		if (!current.isFile()) {
			int totalSize = current.subElements().stream().mapToInt(e -> e.size().get()).sum();
			current.size().set(totalSize);
		}

	}

	private static FileSystemElement processLine(String line, FileSystemElement current) {
		return switch (line) {
		case String s when s.startsWith("$ cd") -> changeFolder(line, current);
		case String s when s.startsWith("$ ls") -> current;
		case String s when s.startsWith("dir") -> readDirectory(line, current);

		default -> readFile(line, current);
		};
	}

	// 199949 rdsz.dng
	private static FileSystemElement readFile(String line, FileSystemElement current) {
		String[] split = line.split(" ");
		int size = Integer.parseInt(split[0]);
		String fileName = split[1].trim();
		FileSystemElement file = new FileSystemElement(fileName, new AtomicInteger(size), true, Collections.emptyList(),
				current);
		current.subElements().add(file);
		return current;
	}

	// dir a
	// current dir has another dir named a
	private static FileSystemElement readDirectory(String line, FileSystemElement current) {
		String[] split = line.split(" ");
		String dirName = split[1].trim();

		FileSystemElement dir = new FileSystemElement(dirName, new AtomicInteger(0), false, new ArrayList<>(), current);
		current.subElements().add(dir);
		return current;
	}

	private static FileSystemElement changeFolder(String line, FileSystemElement current) {
		String newFolderName = line.replace("$ cd", "").trim();

		return switch (newFolderName) {
		case ".." -> current.parent();
		default -> current.subElements().stream().filter(e -> e.name().equals(newFolderName)).findAny()
				.orElseThrow(() -> new NoSuchElementException(newFolderName));
		};
	}
}
