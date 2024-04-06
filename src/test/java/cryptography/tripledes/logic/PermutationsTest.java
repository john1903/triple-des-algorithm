package cryptography.tripledes.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermutationsTest {
    private static final int[][] initialPermutationTable = {
            {58, 50, 42, 34, 26, 18, 10, 2},
            {60, 52, 44, 36, 28, 20, 12, 4},
            {62, 54, 46, 38, 30, 22, 14, 6},
            {64, 56, 48, 40, 32, 24, 16, 8},
            {57, 49, 41, 33, 25, 17, 9, 1},
            {59, 51, 43, 35, 27, 19, 11, 3},
            {61, 53, 45, 37, 29, 21, 13, 5},
            {63, 55, 47, 39, 31, 23, 15, 7}
    };

    private static final int[][] finalPermutationTable = {
            {40, 8, 48, 16, 56, 24, 64, 32},
            {39, 7, 47, 15, 55, 23, 63, 31},
            {38, 6, 46, 14, 54, 22, 62, 30},
            {37, 5, 45, 13, 53, 21, 61, 29},
            {36, 4, 44, 12, 52, 20, 60, 28},
            {35, 3, 43, 11, 51, 19, 59, 27},
            {34, 2, 42, 10, 50, 18, 58, 26},
            {33, 1, 41, 9, 49, 17, 57, 25}
    };

    private static final int[][] permutedChoice1Table = {
            {57, 49, 41, 33, 25, 17, 9, 1},
            {58, 50, 42, 34, 26, 18, 10, 2},
            {59, 51, 43, 35, 27, 19, 1, 3},
            {60, 52, 44, 36, 63, 55, 47, 39},
            {31, 23, 15, 7, 62, 54, 46, 38},
            {30, 22, 14, 6, 61, 53, 45, 37},
            {29, 21, 13, 5, 28, 20, 12, 4}
    };

    private static final int[][] permutedChoice2Table = {
            {14, 17, 11, 24, 1, 5, 3, 28},
            {15, 6, 21, 10, 23, 19, 12, 4},
            {26, 8, 16, 7, 27, 20, 13, 2},
            {41, 52, 31, 37, 47, 55, 30, 40},
            {51, 45, 33, 48, 44, 49, 39, 56},
            {34, 53, 46, 42, 50, 36, 29, 32}
    };

    private static final int[][] expansionTable = {
            {32, 1, 2, 3, 4, 5, 4, 5},
            {6, 7, 8, 9, 8, 9, 10, 11},
            {12, 13, 12, 13, 14, 15, 16, 17},
            {16, 17, 18, 19, 20, 21, 20, 21},
            {22, 23, 24, 25, 24, 25, 26, 27},
            {28, 29, 28, 29, 30, 31, 32, 1}
    };
    @Test
    void initialPermutation() {
        String string = "01234567";
        byte[] input = Transformations.stringToBits(string);
        byte[][] result = Permutations.initialPermutation(input);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(32, result[0].length);
        assertEquals(32, result[1].length);
        int index;
        int expectedValue;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                index = initialPermutationTable[i][j] - 1;
                if (index < 32) {
                    assertEquals(input[i * 8 + j], result[0][index], "Left bits should be equal");
                } else {
                    expectedValue = input[i * 8 + j];
                    assertEquals(expectedValue, result[1][index - 32], "Right bits should be equal");
                }
            }
        }
    }

    @Test
    void finalPermutation() {
        String string = "0123";
        byte[] left = Transformations.stringToBits(string);
        byte[] right = Transformations.stringToBits(string);
        byte[] output = Permutations.finalPermutation(left, right);
        assertNotNull(output);
        assertEquals(64, output.length);
        int index;
        int expected;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                index = finalPermutationTable[i][j] - 1;
                if (index < 32) {
                    expected = left[(i * 8 + j) % 32];
                    assertEquals(expected, output[index], "Left bits should be equal");
                } else {
                    expected = right[(i * 8 + j) % 32];
                    assertEquals(expected, output[index], "Right bits should be equal");
                }
            }
        }
    }

    @Test
    void permutedChoice1Test1() {
        byte[] key = new byte[64];
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 7) {
                key[i] = (byte) (0);
            } else {
                key[i] = (byte) (1);
            }
        }
        byte[][] bits = Permutations.permutedChoice1(key);
        assertNotNull(bits);
        assertEquals(28, bits[0].length);
        assertEquals(28, bits[1].length);
        for (int i = 0; i < 28; i++) {
            assertEquals(1, bits[0][i]);
            assertEquals(1, bits[1][i]);
        }
    }

    @Test
    void permutedChoice1Test2() {
        byte[] key = Transformations.stringToBits("01234567");
        byte[][] permuted = Permutations.permutedChoice1(key);
        int index;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                index = i * 8 + j;
                if (index < 28) {
                    assertEquals(key[permutedChoice1Table[i][j] - 1], permuted[0][index]);
                } else {
                    index = (i * 8 + j) % 28;
                    assertEquals(key[permutedChoice1Table[i][j] - 1], permuted[1][index]);
                }
            }
        }
    }

    @Test
    void permutedChoice2Test() {
        String keyString = "0123456";
        byte[] key = Transformations.stringToBits(keyString);
        byte[] bits = Permutations.permutedChoice2(key);
        assertNotNull(bits);
        assertEquals(48, bits.length);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                int expectedIndex = permutedChoice2Table[i][j] - 1;
                assertEquals(key[expectedIndex], bits[i * 8 + j]);
            }
        }
    }

    @Test
    void expansionPermutation() {
        String plainString = "1234";
        byte[] plainT = Transformations.stringToBits(plainString);
        byte[] expandedBits = Permutations.expansionPermutation(plainT);
        assertEquals(48, expandedBits.length);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                int expectedIndex = expansionTable[i][j] - 1;
                assertEquals(plainT[expectedIndex], expandedBits[i * 8 + j]);
            }
        }
    }

}