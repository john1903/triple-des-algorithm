package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

public class DesEncryption implements EncryptionInterface {
    private static final int[] SHIFT_TABLE = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    private static BitSet[] getKeysArray(BitSet key) {
        BitSet[] keyArray = Permutations.permutedChoice1(key);
        BitSet[] permutedChoice2 = new BitSet[16];
        BitSet left = keyArray[0];
        BitSet right = keyArray[1];

        for (int i = 0; i < 16; i++) {
            left = Transformations.leftShift(left, SHIFT_TABLE[i]);
            right = Transformations.leftShift(right, SHIFT_TABLE[i]);
            BitSet combined = Transformations.arrayCombine(left, right);
            permutedChoice2[i] = Permutations.permutedChoice2(combined);
        }
        return permutedChoice2;
    }

    private static BitSet function(BitSet input, BitSet key) {
        BitSet expanded = Permutations.extensionPermutation(input);
        BitSet xored = Transformations.xor(expanded, key);
        BitSet substituted = Permutations.sBoxSubstitution(xored);
        return Permutations.pBoxPermutation(substituted);
    }

    @Override
    public BitSet encryption(BitSet input, BitSet key) {
        BitSet[] inputArray = Permutations.initialPermutation(input);
        BitSet left = inputArray[0];
        BitSet right = inputArray[1];
        BitSet[] keysArray = getKeysArray(key);

        for (int i = 0; i < 16; i++) {
            BitSet temp = right;
            right = Transformations.xor(left, function(right, keysArray[i]));
            left = temp;
        }
        return Permutations.finalPermutation(left, right);
    }

    @Override
    public BitSet decryption(BitSet input, BitSet key) {
        BitSet[] inputArray = Permutations.initialPermutation(input);
        BitSet left = inputArray[0];
        BitSet right = inputArray[1];
        BitSet[] keysArray = getKeysArray(key);

        for (int i = 15; i >= 0; i--) {
            BitSet temp = left;
            left = Transformations.xor(right, function(left, keysArray[i]));
            right = temp;
        }
        return Permutations.finalPermutation(left, right);
    }
}