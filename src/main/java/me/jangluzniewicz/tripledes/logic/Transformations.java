package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

/**
 * The Transformations class provides utility methods for performing various bit-level
 * transformations used in the DES (Data Encryption Standard) and 3DES (Triple DES) algorithms.
 */
public class Transformations {

    /**
     * Performs a left shift on the given BitSet by the specified number of positions.
     *
     * @param bits the BitSet to be shifted
     * @param shift the number of positions to shift
     * @return the shifted BitSet
     */
    public static BitSet leftShift(BitSet bits, int shift) {
        int size = 28;
        BitSet shiftedBits = new BitSet(size);
        for (int i = 0; i < size; i++) {
            shiftedBits.set(i, bits.get((i + shift) % size));
        }
        return shiftedBits;
    }

    /**
     * Creates a BitSet from an array of integers, where each integer is either 0 or 1.
     *
     * @param bits an array of integers representing bits (0 or 1)
     * @return the created BitSet
     */
    public static BitSet createBitSet(int... bits) {
        BitSet bitSet = new BitSet(bits.length);
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 1) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    /**
     * Combines two BitSets into one, with the bits of the second BitSet appended to the first.
     *
     * @param bitSet1 the first BitSet
     * @param bitSet2 the second BitSet
     * @return the combined BitSet
     */
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

    /**
     * Performs an XOR operation between two BitSets.
     *
     * @param bitSet1 the first BitSet
     * @param bitSet2 the second BitSet
     * @return the resulting BitSet after the XOR operation
     */
    public static BitSet xor(BitSet bitSet1, BitSet bitSet2) {
        BitSet result = (BitSet) bitSet1.clone();
        result.xor(bitSet2);
        return result;
    }
}