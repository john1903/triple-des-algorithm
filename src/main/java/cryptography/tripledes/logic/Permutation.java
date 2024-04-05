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