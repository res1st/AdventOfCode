package de.cas.adventofcode.y2021.day2;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import de.cas.adventofcode.util.AdventUtil;
import de.cas.adventofcode.util.Point;
import de.cas.adventofcode.util.RowMappingFunction;

public class Day2SubmarineDive {

	public static void main(String[] args) throws IOException {
		Day2SubmarineDive dive = new Day2SubmarineDive();
		dive.divePuzzplePart2();
	}
	
	private void dive() throws IOException {
		List<Course> plannedCourse = AdventUtil.readFileByLineWithMapping("2021", "day2_input.txt", new CourseMappingFunction());

		Point point = new Point();
		printPosition(point);
		plannedCourse.stream().forEach(course -> calculate(course, point));
		printPosition(point);
		System.out.println("puzzle result: " + point.x * point.y);
	}

	private void calculate(Course course, Point point) {
		switch (course.direction()) {
		case "forward":
			point.x += course.amount();
			break;
		case "down":
			point.y += course.amount();
			break;
		case "up":
			point.y -= course.amount();
			break;
		default:
			throw new IllegalArgumentException(course.direction());
		}

	}

	private void divePuzzplePart2() throws IOException {
		List<Course> plannedCourse = AdventUtil.readFileByLineWithMapping("2021", "day2_input.txt", new CourseMappingFunction());

		AimPoint point = new AimPoint();
		printPosition(point);
		plannedCourse.stream().forEach(course -> calculatePart2(course, point));
		printPosition(point);
		System.out.println("puzzle result: " + point.x * point.y);
	}

	private void calculatePart2(Course course, AimPoint point) {
		switch (course.direction()) {
		case "forward":
			point.x += course.amount();
			point.y += (point.aim * course.amount());
			break;
		case "down":
			point.aim += course.amount();
			break;
		case "up":
			point.aim -= course.amount();
			break;
		default:
			throw new IllegalArgumentException(course.direction());
		}

	}

	private void printPosition(Point coordinates) {
		System.out.println("pos: " + coordinates.toString());
	}

	private void printPosition(AimPoint coordinates) {
		System.out.println("pos: " + coordinates.toString());
	}

	private Course mapToCourse(String row) {
		String[] splittedValues = row.split(" ");
		Assert.isTrue(splittedValues.length == 2, "invalid row");
		return new Course(StringUtils.trim(splittedValues[0]), Integer.parseInt(StringUtils.trim(splittedValues[1])));
	}

	public class CourseMappingFunction implements RowMappingFunction<Course> {

		@Override
		public Course apply(String row) {
			return mapToCourse(row);
		}
	}

}
