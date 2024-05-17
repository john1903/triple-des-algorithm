package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

public class Transformations {
    public static BitSet leftShift(BitSet bits, int shift) {
        int size = 28;
        BitSet shiftedBits = new BitSet(size);
        for (int i = 0; i < size; i++) {
            boolean bit = bits.get((i + shift) % size);
            shiftedBits.set(i, bit);
        }
        return shiftedBits;
    }

    public static BitSet arrayCombine(BitSet bitSet1, BitSet bitSet2) {
        BitSet combinedBitSet = new BitSet(bitSet1.length() + bitSet2.length());

        for (int i = 0; i < bitSet1.length(); i++) {
            if (bitSet1.get(i)) {
                combinedBitSet.set(i);
            }
        }

        for (int i = 0; i < bitSet2.length(); i++) {
            if (bitSet2.get(i)) {
                combinedBitSet.set(bitSet1.length() + i);
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
