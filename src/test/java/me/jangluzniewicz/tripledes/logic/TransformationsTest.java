package me.jangluzniewicz.tripledes.logic;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class TransformationsTest {

    private BitSet createBitSet(int... bits) {
        BitSet bitSet = new BitSet(bits.length);
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 1) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    @Test
    void leftShifts() {
        int[] shiftTable = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
        BitSet[] input = {
                createBitSet(1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1),
                createBitSet(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1)
        };
        BitSet[] expected = {
                createBitSet(1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1),
                createBitSet(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1)
        };

        BitSet[] result = new BitSet[]{(BitSet) input[0].clone(), (BitSet) input[1].clone()};

        for (int i = 0; i < 16; i++) {
            result[0] = Transformations.leftShift(result[0], shiftTable[i]);
            result[1] = Transformations.leftShift(result[1], shiftTable[i]);
        }

        for (int i = 0; i < 2; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    void arrayCombine() {
        BitSet array1 = createBitSet(1, 1, 1, 1, 1, 1, 1, 1);
        BitSet array2 = createBitSet(0, 0, 0, 0, 0, 0, 0, 0);

        BitSet combinedArray = Transformations.arrayCombine(array1, array2);

        for (int i = 0; i < 8; i++) {
            assertTrue(combinedArray.get(i));
            assertFalse(combinedArray.get(i + 8));
        }
    }

    @Test
    void xor() {
        BitSet expandedTable = createBitSet(
                0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1,
                0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0,
                0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1
        );
        BitSet key = createBitSet(
                0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0,
                1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0
        );
        BitSet expected = createBitSet(
                0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1,
                1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0,
                0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1
        );

        BitSet result = Transformations.xor(expandedTable, key);

        assertEquals(expected, result);
    }
}