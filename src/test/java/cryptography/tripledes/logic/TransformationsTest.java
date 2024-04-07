package cryptography.tripledes.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformationsTest {
    @Test
    void stringToBits() {
        byte[] bits = Transformations.stringToBits("Hello");
        assertNotNull(bits);
        assertEquals(40, bits.length);
        assertEquals("Hello", Transformations.bitsToString(bits));
    }

    @Test
    void leftShifts() {
        int[] shiftTable = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
        byte[][] input =
                {
                        {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1},
                        {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1}
                };
        byte[][] expected =
                {
                        {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1},
                        {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1}
                };
        byte[][] result = new byte[][]{input[0], input[1]};
        for (int i = 0; i < 16; i++) {
            result[0] = Transformations.leftShift(result[0], shiftTable[i]);
            result[1] = Transformations.leftShift(result[1], shiftTable[i]);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 28; j++) {
                assertEquals(expected[i][j], result[i][j]);
            }
        }
    }

    @Test
    void arrayCombine() {
        byte[] array1 = new byte[8];
        byte[] array2 = new byte[8];
        for (int i = 0; i < 8; i++) {
            array1[i] = (byte) (1);
            array2[i] = (byte) (0);
        }
        byte[] combinedArray = Transformations.arrayCombine(array1, array2);
        assertNotNull(combinedArray);
        assertEquals(16, combinedArray.length);
        for (int i = 0; i < 8; i++) {
            assertEquals(1, combinedArray[i]);
            assertEquals(0, combinedArray[i + 8]);
        }
    }

    @Test
    void arrayDeCombine() {
        byte[] array = new byte[16];
        for (int i = 0; i < 8; i++) {
            array[i] = (byte) (1);
        }
        for (int i = 8; i < 16; i++) {
            array[i] = (byte) (0);
        }
        byte[][] deCombinedArray = Transformations.arrayDeCombine(array, 8);
        assertNotNull(deCombinedArray);
        assertEquals(2, deCombinedArray.length);
        assertEquals(8, deCombinedArray[0].length);
        assertEquals(8, deCombinedArray[1].length);
        for (int i = 0; i < 8; i++) {
            assertEquals(1, deCombinedArray[0][i]);
            assertEquals(0, deCombinedArray[1][i]);
        }
    }

    @Test
    void xor() {
        byte[] expandedTable = {0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
        byte[] key = {0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0};
        byte[] expected = {0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1,
                0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1};
        byte[] result = Transformations.xor(expandedTable, key);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }
}