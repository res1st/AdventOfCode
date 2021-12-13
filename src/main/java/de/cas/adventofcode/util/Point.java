package de.cas.adventofcode.util;

import java.util.Objects;

public class Point {
	
	public int x = 0;
	public int y = 0;

	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}
}
