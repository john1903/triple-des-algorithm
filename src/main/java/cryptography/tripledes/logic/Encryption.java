package cryptography.tripledes.logic;

import java.util.Arrays;

public class Encryption {
    private static final int[] shiftTable = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    private static byte[] function(byte[] input, byte[] key) {
        input = Permutations.expansionPermutation(input);
        input = Transformations.xor(input, key);
        input = Permutations.sBoxPermutation(input);
        return Permutations.pPermutation(input);
    }

    private static byte[][] encryption(byte[] left, byte[] right, byte[] key) {
        right = Transformations.xor(left, function(right, key));
        return new byte[][]{right, left};
    }

    public static byte[] encryption(byte[] input, byte[] key, int mode) {
        byte[][] inputArray = Permutations.initialPermutation(input);
        byte[] left = inputArray[0];
        byte[] right = inputArray[1];
        byte[][] byteArray = new byte[][]{left, right};
        byte[][] keyArray = Permutations.permutedChoice1(key);
        byte[] key1;
        if (mode == 0) {
            for (int i = 0; i < 16; i++) {
                keyArray = new byte[][]{Transformations.leftShift(keyArray[0], shiftTable[i]),
                        Transformations.leftShift(keyArray[1], shiftTable[i])};
                key1 = Permutations.permutedChoice2(Transformations.arrayCombine(keyArray[0], keyArray[1]));
                System.out.println(Arrays.toString(Transformations.arrayCombine(keyArray[0], keyArray[1])));
                byteArray = Encryption.encryption(byteArray[0], byteArray[1], key1);
            }
        } else {
            byte[][][] keyArray2 = new byte[16][][];
            for (int i = 0; i < 16; i++) {
                keyArray = new byte[][]{Transformations.leftShift(keyArray[0], shiftTable[i]),
                        Transformations.leftShift(keyArray[1], shiftTable[i])};
                keyArray2[i] = new byte[][]{keyArray[0], keyArray[1]};
            }
            for (int i = 15; i >= 0; i--) {
                key1 = Permutations.permutedChoice2(Transformations.arrayCombine(keyArray2[i][0], keyArray2[i][1]));
                System.out.println(Arrays.toString(Transformations.arrayCombine(keyArray2[i][0], keyArray2[i][1])));
                byteArray = Encryption.encryption(byteArray[0], byteArray[1], key1);
            }
        }
        return Permutations.finalPermutation(byteArray[0], byteArray[1]);
    }
}
