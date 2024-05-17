package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

/**
 * The DesEncryption class implements the EncryptionInterface and provides methods for
 * performing DES (Data Encryption Standard) encryption and decryption.
 */
public class DesEncryption implements EncryptionInterface {
    // Shift table used for key schedule generation
    private static final int[] SHIFT_TABLE = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    /**
     * Generates an array of 16 subkeys from the initial key.
     *
     * @param key the initial 64-bit key BitSet
     * @return an array of 16 subkeys each of 48-bit BitSet
     */
    private static BitSet[] getKeysArray(BitSet key) {
        BitSet[] keyArray = Permutations.permutedChoice1(key);
        BitSet[] permutedChoice2 = new BitSet[16];
        BitSet left = keyArray[0];
        BitSet right = keyArray[1];

        // Generate 16 subkeys
        for (int i = 0; i < 16; i++) {
            left = Transformations.leftShift(left, SHIFT_TABLE[i]);
            right = Transformations.leftShift(right, SHIFT_TABLE[i]);
            BitSet combined = Transformations.arrayCombine(left, right);
            permutedChoice2[i] = Permutations.permutedChoice2(combined);
        }
        return permutedChoice2;
    }

    /**
     * Applies the DES function to the input using the given subkey.
     *
     * @param input the 32-bit BitSet to be processed
     * @param key the 48-bit subkey BitSet
     * @return the 32-bit BitSet result after applying the DES function
     */
    private static BitSet function(BitSet input, BitSet key) {
        BitSet expanded = Permutations.extensionPermutation(input);
        BitSet xored = Transformations.xor(expanded, key);
        BitSet substituted = Permutations.sBoxSubstitution(xored);
        return Permutations.pBoxPermutation(substituted);
    }

    /**
     * Encrypts the given input BitSet using the provided key BitSet.
     *
     * @param input the 64-bit input BitSet to be encrypted
     * @param key the 64-bit key BitSet
     * @return the encrypted 64-bit BitSet
     */
    @Override
    public BitSet encryption(BitSet input, BitSet key) {
        BitSet[] inputArray = Permutations.initialPermutation(input);
        BitSet left = inputArray[0];
        BitSet right = inputArray[1];
        BitSet[] keysArray = getKeysArray(key);

        // Perform 16 rounds of encryption
        for (int i = 0; i < 16; i++) {
            BitSet temp = right;
            right = Transformations.xor(left, function(right, keysArray[i]));
            left = temp;
        }
        return Permutations.finalPermutation(left, right);
    }

    /**
     * Decrypts the given input BitSet using the provided key BitSet.
     *
     * @param input the 64-bit input BitSet to be decrypted
     * @param key the 64-bit key BitSet
     * @return the decrypted 64-bit BitSet
     */
    @Override
    public BitSet decryption(BitSet input, BitSet key) {
        BitSet[] inputArray = Permutations.initialPermutation(input);
        BitSet left = inputArray[0];
        BitSet right = inputArray[1];
        BitSet[] keysArray = getKeysArray(key);

        // Perform 16 rounds of decryption in reverse order
        for (int i = 15; i >= 0; i--) {
            BitSet temp = left;
            left = Transformations.xor(right, function(left, keysArray[i]));
            right = temp;
        }
        return Permutations.finalPermutation(left, right);
    }
}