package de.cas.adventofcode.util;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Points {

	public Set<Point> points;

	public Points(Set<Point> points) {
		this.points = points;
	}

	public Points(List<String> listOfPoints) {
		points = listOfPoints.stream().map(s -> s.split(",")).map(e -> new Point(Integer.parseInt(e[0]), Integer.parseInt(e[1]))).collect(Collectors.toSet());
	}

	public int getMaxX() {
		return stream().mapToInt(p -> p.x).max().getAsInt();
	}

	public int getMaxY() {
		return stream().mapToInt(p -> p.y).max().getAsInt();
	}

	public Stream<Point> stream() {
		return points.stream();
	}

	@Override
	public String toString() {
		return points.size() + " points with max x,y of " + getMaxX() + "," + getMaxY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(points);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Points other = (Points) obj;
		return Objects.equals(points, other.points);
	}
}
