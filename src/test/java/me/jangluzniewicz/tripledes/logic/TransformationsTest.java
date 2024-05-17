package me.jangluzniewicz.tripledes.logic;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class TransformationsTest {
    @Test
    void leftShifts() {
        int[] shiftTable = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
        BitSet[] input = {
                Transformations.createBitSet(1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1),
                Transformations.createBitSet(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1)
        };
        BitSet[] expected = {
                Transformations.createBitSet(1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1),
                Transformations.createBitSet(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1)
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
        BitSet array1 = Transformations.createBitSet(1, 1, 1, 1, 1, 1, 1, 1);
        BitSet array2 = Transformations.createBitSet(0, 0, 0, 0, 0, 0, 0, 0);

        BitSet combinedArray = Transformations.arrayCombine(array1, array2);

        for (int i = 0; i < 8; i++) {
            assertTrue(combinedArray.get(i));
            assertFalse(combinedArray.get(i + 8));
        }
    }

    @Test
    void xor() {
        BitSet expandedTable = Transformations.createBitSet(
                0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1,
                0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0,
                0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1
        );
        BitSet key = Transformations.createBitSet(
                0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0,
                1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0
        );
        BitSet expected = Transformations.createBitSet(
                0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1,
                1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0,
                0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1
        );

        BitSet result = Transformations.xor(expandedTable, key);

        assertEquals(expected, result);
    }
}