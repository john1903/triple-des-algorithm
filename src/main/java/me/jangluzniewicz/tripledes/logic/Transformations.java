package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

public class Transformations {
    public static BitSet leftShift(BitSet bits, int shift) {
        int size = 28;
        BitSet shiftedBits = new BitSet(size);
        for (int i = 0; i < size; i++) {
            shiftedBits.set(i, bits.get((i + shift) % size));
        }
        return shiftedBits;
    }

    public static BitSet createBitSet(int... bits) {
        BitSet bitSet = new BitSet(bits.length);
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 1) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    public static BitSet arrayCombine(BitSet bitSet1, BitSet bitSet2) {
        int len1 = bitSet1.length();
        int len2 = bitSet2.length();
        BitSet combinedBitSet = new BitSet(len1 + len2);

        combinedBitSet.or(bitSet1);
        for (int i = 0; i < len2; i++) {
            if (bitSet2.get(i)) {
                combinedBitSet.set(len1 + i);
            }
        }

        return combinedBitSet;
    }

    public static BitSet xor(BitSet bitSet1, BitSet bitSet2) {
        BitSet result = (BitSet) bitSet1.clone();
        result.xor(bitSet2);
        return result;
    }
}