package de.cas.adventofcode.util;

import java.util.ArrayList;
import java.util.Collection;

public class Block extends ArrayList<String> {

	private static final long serialVersionUID = 8311113073734368828L;

	public Block() {
		super();
	}

	public Block(Collection<? extends String> c) {
		super(c);
	}

	public Block(int initialCapacity) {
		super(initialCapacity);
	}
}
