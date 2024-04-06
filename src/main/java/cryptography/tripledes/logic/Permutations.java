package cryptography.tripledes.logic;

public class Permutations {
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

    public static byte[][] initialPermutation(byte[] input) {
        byte[] bitSet = new byte[64];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (input[i * 8 + j] == 1) {
                    bitSet[(initialPermutationTable[i][j]) - 1] = 1;
                } else {
                    bitSet[(initialPermutationTable[i][j]) - 1] = 0;
                }
            }
        }
        byte[] left = new byte[32];
        byte[] right = new byte[32];
        for (int i = 0; i < 32; i++) {
            left[i] = bitSet[i];
            right[i] = bitSet[i + 32];
        }
        return new byte[][]{left, right};
    }



    public static byte[] finalPermutation(byte[] left, byte[] right) {
        byte[] bitSet = new byte[64];
        for (int i = 0; i < 32; i++) {
            bitSet[i] = left[i];
            bitSet[i + 32] = right[i];
        }
        byte[] bits = new byte[64];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (bitSet[i * 8 + j] == 1) {
                    bits[finalPermutationTable[i][j] - 1] = 1;
                } else {
                    bits[finalPermutationTable[i][j] - 1] = 0;
                }
            }
        }
        return bits;
    }

    public static byte[][] permutedChoice1(byte[] key) {
        int index;
        byte[] permutedBits = new byte[56];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                index = permutedChoice1Table[i][j] - 1;
                permutedBits[i * 8 + j] = key[index];
            }
        }
        byte[][] bitsArray = new byte[2][28];
        for (int i = 0; i < 28; i++) {
            bitsArray[0][i] = permutedBits[i];
            bitsArray[1][i] = permutedBits[i + 28];
        }
        return bitsArray;
    }

    public static byte[] permutedChoice2(byte[] key) {
        byte[] permutedKey = new byte[48];
        int tableIndex;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                tableIndex = permutedChoice2Table[i][j] - 1;
                permutedKey[i * 8 + j] = key[tableIndex];
            }
        }
        return permutedKey;
    }

    public static byte[] expansionPermutation(byte[] plainRight) {
        byte[] expandedTable = new byte[48];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                int index = (i * 8) + j;
                expandedTable[index] = plainRight[expansionTable[i][j] - 1];
            }
        }
        return expandedTable;
    }
}