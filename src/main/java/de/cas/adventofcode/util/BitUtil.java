package de.cas.adventofcode.util;

import java.util.BitSet;

public class BitUtil {

	public static BitSet fromStringToBitSet(String binary) {
	    BitSet bitset = new BitSet(binary.length());
	    for (int i = 0; i < binary.length(); i++) {
	        if (binary.charAt(i) == '1') {
	            bitset.set(i);
	        }
	    }
	    return bitset;
	}

	public static BitSet fromStringReverseToBitSet(String binary) {
	    BitSet bitset = new BitSet(binary.length());
	    int len = binary.length();
	    for (int i = len-1; i >= 0; i--) {
	        if (binary.charAt(i) == '1') {
	            bitset.set(len-i-1);
	        }
	    }
	    return bitset;
	}

	public static BitSet inverseBitmask(BitSet bitmask, int lengthOfMask) {
		BitSet invertedMask = (BitSet) bitmask.clone();
		invertedMask.flip(0, lengthOfMask);
		return invertedMask;
	}

	public static long toLong(BitSet bitSet) {
		return bitSet.toLongArray()[0];
	}
}
