package cryptography.tripledes.logic;

public class Encryption {
    private static final int[] shiftTable = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    private static byte[][] getKeysArray(byte[] key) {
        byte[][] keyArray = Permutations.permutedChoice1(key);
        byte[][] shifted  = new byte[16][];
        byte[][] permutedChoice2 = new byte[16][];
        for (int i = 0; i < 16; i++) {
            keyArray[0] = Transformations.leftShift(keyArray[0], shiftTable[i]);
            keyArray[1] = Transformations.leftShift(keyArray[1], shiftTable[i]);
            shifted[i] = Transformations.arrayCombine(keyArray[0].clone(), keyArray[1].clone());
            permutedChoice2[i] = Permutations.permutedChoice2(shifted[i]);
        }
        return permutedChoice2;
    }

    private static byte[] function(byte[] input, byte[] key) {
        input = Permutations.expansionPermutation(input);
        input = Transformations.xor(input, key);
        input = Permutations.sBoxPermutation(input);
        return Permutations.pPermutation(input);
    }

    public static byte[] encryption(byte[] input, byte[] key) {
        byte[][] inputArray = Permutations.initialPermutation(input);
        byte[] left = inputArray[0];
        byte[] right = inputArray[1];
        byte[][] keysArray = getKeysArray(key);
        for (int i = 0; i < 16; i++) {
            byte[] temp = right.clone();
            right = Transformations.xor(left, function(right, keysArray[i]));
            left = temp;
        }
        return Permutations.finalPermutation(left, right);
    }

    public static byte[] decryption(byte[] input, byte[] key) {
        byte[][] inputArray = Permutations.initialPermutation(input);
        byte[] left = inputArray[0];
        byte[] right = inputArray[1];
        byte[][] keysArray = getKeysArray(key);
        for (int i = 15; i >= 0; i--) {
            byte[] temp = left.clone();
            left = Transformations.xor(right, function(left, keysArray[i]));
            right = temp;
        }
        return Permutations.finalPermutation(left, right);
    }
}