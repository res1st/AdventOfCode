package de.cas.adventofcode.y2021.day13;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.Point;
import de.cas.adventofcode.util.Points;

public class Day13HeatFolding {

	private static final String FILENAME = "day13_input.txt";

	private static final Pattern foldPattern = Pattern.compile("fold along (\\w)=(\\d+)");

	public static void main(String[] args) throws IOException {
		Day13HeatFolding day13 = new Day13HeatFolding();

		List<String> input = AdventUtil.readFileByLineAsString("2021", FILENAME);
		List<List<String>> inputLists = AdventUtil.splitAtEmptyLines(input);
		System.out.println(inputLists);

		Points points = new Points(inputLists.get(0));
		System.out.println(points.toString());

		List<FoldingDescription> foldingDescriptions = inputLists.get(1).stream().map(s -> mapToFoldingDescription(s))
				.collect(Collectors.toList());
		System.out.println("Foldings: " + foldingDescriptions);

		day13.countDots(points, foldingDescriptions);
		day13.foldAll(points, foldingDescriptions);
	}

	private void countDots(Points points, List<FoldingDescription> foldingDescriptions) {
		long dots = points.stream().map(e -> fold(foldingDescriptions.get(0), e)).distinct().count();
		System.out.println("Number of dots: " + dots);
	}

	private Point fold(FoldingDescription foldingDesc, Point point) {

		switch (foldingDesc.axis) {
		case "y":
			if (point.y > foldingDesc.n) {
				return new Point(point.x, (foldingDesc.n - (point.y - foldingDesc.n)));
			}
			return point;
		case "x":
			if (point.x > foldingDesc.n) {
				return new Point(foldingDesc.n - (point.x - foldingDesc.n), point.y);
			}
			return point;
		default:
			throw new RuntimeException("unknown axis " + foldingDesc.axis());
		}
	}

	public void foldAll(Points points, List<FoldingDescription> foldingDescriptions) throws IOException {
		Points lastPoints = points;
		for (FoldingDescription foldingDesc : foldingDescriptions) {
			Set<Point> foldedPoints = lastPoints.stream().map(point -> fold(foldingDesc, point)).distinct()
					.collect(Collectors.toSet());
			lastPoints = new Points(foldedPoints);
		}
		System.out.println("Final points: " + lastPoints);

		AdventUtil.writePointsAsImage(lastPoints, "target" + File.separator + "thermalImagingCameraCode.png");
	}

	private static FoldingDescription mapToFoldingDescription(String s) {
		Matcher matcher = foldPattern.matcher(s);
		if (matcher.matches()) {
			FoldingDescription foldingDescription = new FoldingDescription(matcher.group(1),
					Integer.parseInt(matcher.group(2)));
			return foldingDescription;
		}
		throw new RuntimeException(s);
	}

	public record FoldingDescription(String axis, int n) {
	}
}
