package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

public class DesEncryption implements EncryptionInterface {
    private static final int[] shiftTable = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    private static BitSet[] getKeysArray(BitSet key) {
        BitSet[] keyArray = Permutations.permutedChoice1(key);
        BitSet[] shifted = new BitSet[16];
        BitSet[] permutedChoice2 = new BitSet[16];
        for (int i = 0; i < 16; i++) {
            keyArray[0] = Transformations.leftShift(keyArray[0], shiftTable[i]);
            keyArray[1] = Transformations.leftShift(keyArray[1], shiftTable[i]);
            shifted[i] = Transformations.arrayCombine((BitSet) keyArray[0].clone(), (BitSet) keyArray[1].clone());
            permutedChoice2[i] = Permutations.permutedChoice2(shifted[i]);
        }
        return permutedChoice2;
    }

    private static BitSet function(BitSet input, BitSet key) {
        input = Permutations.extensionPermutation(input);
        input = Transformations.xor(input, key);
        input = Permutations.sBoxSubstitution(input);
        return Permutations.pBoxPermutation(input);
    }

    @Override
    public BitSet encryption(BitSet input, BitSet key) {
        BitSet[] inputArray = Permutations.initialPermutation(input);
        BitSet left = inputArray[0];
        BitSet right = inputArray[1];
        BitSet[] keysArray = getKeysArray(key);
        for (int i = 0; i < 16; i++) {
            BitSet temp = (BitSet) right.clone();
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
            BitSet temp = (BitSet) left.clone();
            left = Transformations.xor(right, function(left, keysArray[i]));
            right = temp;
        }
        return Permutations.finalPermutation(left, right);
    }
}
