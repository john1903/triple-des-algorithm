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

    private static final int[][] permutedChoice2Table = {
            {12, 45, 23, 6, 2, 41, 35, 19},
            {33, 9, 14, 28, 42, 5, 18, 31},
            {26, 16, 11, 3, 38, 29, 21, 44},
            {36, 10, 4, 30, 8, 15, 43, 20},
            {13, 7, 37, 25, 34, 22, 1, 46},
            {17, 27, 40, 32, 39, 24, 47, 48}
    };

    public static byte[][] initialPermutation(byte[] input) {
        byte[] bitSet = new byte[64];
        for (int i = 0; i < initialPermutationTable.length; i++) {
            for (int j = 0; j < initialPermutationTable[i].length; j++) {
                if (input[i * 8 + j] == 1) {
                    bitSet[initialPermutationTable[i][j] - 1] = 1;
                } else {
                    bitSet[initialPermutationTable[i][j] - 1] = 0;
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
        for (int i = 0; i < finalPermutationTable.length; i++) {
            for (int j = 0; j < finalPermutationTable[i].length; j++) {
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
        byte[] bits = new byte[56];
        int index = 0;
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 7) {
                continue;
            }
            bits[index++] = key[i];
        }
        byte[][] bitsArray = new byte[2][28];
        for (int i = 0; i < 28; i++) {
            bitsArray[0][i] = bits[i];
            bitsArray[1][i] = bits[i + 28];
        }
        return bitsArray;
    }

    public static byte[] permutedChoice2(byte[] key) {
        byte[] rearrangeKey = new byte[48];
        int index = 0;
        for (int i = 0; i < key.length; i++) {
            if (i % 7 == 6) {
                continue;
            }
            rearrangeKey[index++] = key[i];
        }
        byte[] permutedKey = new byte[48];
        int tableIndex;
        int counter = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                tableIndex = permutedChoice2Table[i][j] - 1;
                permutedKey[counter++] = rearrangeKey[tableIndex];
            }
        }
        return permutedKey;
    }
}