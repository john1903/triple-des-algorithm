package cryptography.tripledes.logic;

public class Permutation {
    int[][] initialPermutationTable = {
            {58, 50, 42, 34, 26, 18, 10, 2},
            {60, 52, 44, 36, 28, 20, 12, 4},
            {62, 54, 46, 38, 30, 22, 14, 6},
            {64, 56, 48, 40, 32, 24, 16, 8},
            {57, 49, 41, 33, 25, 17, 9, 1},
            {59, 51, 43, 35, 27, 19, 11, 3},
            {61, 53, 45, 37, 29, 21, 13, 5},
            {63, 55, 47, 39, 31, 23, 15, 7}
    };

    int[][] finalPermutationTable = {
            {40, 8, 48, 16, 56, 24, 64, 32},
            {39, 7, 47, 15, 55, 23, 63, 31},
            {38, 6, 46, 14, 54, 22, 62, 30},
            {37, 5, 45, 13, 53, 21, 61, 29},
            {36, 4, 44, 12, 52, 20, 60, 28},
            {35, 3, 43, 11, 51, 19, 59, 27},
            {34, 2, 42, 10, 50, 18, 58, 26},
            {33, 1, 41, 9, 49, 17, 57, 25}
    };

    public byte[][] initialPermutation(String input) {
        byte[] bits = stringToBits(input);
        byte[] bitSet = new byte[64];
        for (int i = 0; i < initialPermutationTable.length; i++) {
            for (int j = 0; j < initialPermutationTable[i].length; j++) {
                if (bits[i * 8 + j] == 1) {
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

    public byte[] finalPermutation(byte[] left, byte[] right) {
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

    private byte[] stringToBits(String input) {
        byte[] bits = new byte[input.length() * 8];
        for (int i = 0; i < input.length(); i++) {
            String charBits = Integer.toBinaryString(input.charAt(i));
            int padding = 8 - charBits.length();
            for (int j = 0; j < padding; j++) {
                bits[i * 8 + j] = 0;
            }
            for (int j = 0; j < charBits.length(); j++) {
                bits[i * 8 + padding + j] = (byte) (charBits.charAt(j) - '0');
            }
        }
        return bits;
    }
}