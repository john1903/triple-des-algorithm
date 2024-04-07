package cryptography.tripledes.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PermutationsTest {

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

    private static final int[][] pTable = {
            {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10},
            {2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25}
    };

    @Test
    void initialPermutation() {
        byte[] input = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1};
        byte[][] expected = {
                {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0}
        };
        byte[][] result = Permutations.initialPermutation(input);
        System.out.println("My result:");
        System.out.println(Arrays.toString(result[0]));
        System.out.println(Arrays.toString(result[1]));
        System.out.println("Expected:");
        System.out.println(Arrays.toString(expected[0]));
        System.out.println(Arrays.toString(expected[1]));
        for (int i = 0; i < expected[0].length; i++) {
            assertEquals(expected[0][i], result[0][i]);
            assertEquals(expected[1][i], result[1][i]);
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
    void permutedChoice1Test2() {
        byte[] key =
                { 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1,
                        0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1 };
        byte[][] permuted = Permutations.permutedChoice1(key);
        byte[][] expected = {
                { 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1 },
                { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1 }
        };
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 28; j++) {
                assertEquals(expected[i][j], permuted[i][j]);
            }
        }
    }

    @Test
    void permutedChoice2Test() {
        byte[][] keyArray = {
                {1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}
        };
        byte[] key = Transformations.arrayCombine(keyArray[0], keyArray[1]);
        byte[] expected = {0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0};
        byte[] permutedKey = Permutations.permutedChoice2(key);
        assertNotNull(permutedKey);
        assertEquals(48, permutedKey.length);
        for (int i = 0; i < 48; i++) {
            assertEquals(expected[i], permutedKey[i]);
        }
    }

    @Test
    void expansionPermutation() {
        byte[] input = {1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0};
        byte[] expected = {0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
        byte[] result = Permutations.expansionPermutation(input);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    void sBoxPermutation() {
        byte[] input = {0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1,
                0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1};
        byte[] expected = {0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0,
                1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1};
        byte[] result = Permutations.sBoxPermutation(input);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    void pTable() {
        byte[] input = {0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0,
                1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1};
        byte[] expected = {0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0,
                1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1};
        byte[] result = Permutations.pPermutation(input);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }
}