package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.KeyGeneratorInterface;

import java.util.BitSet;

/**
 * The KeyManager class provides methods for key generation and conversion between
 * BitSet and hexadecimal string representations.
 */
public class KeyManager {
    private final KeyGeneratorInterface generator; // Key generator interface

    /**
     * Constructs a KeyManager with the given KeyGeneratorInterface implementation.
     *
     * @param generator the KeyGeneratorInterface implementation
     */
    public KeyManager(KeyGeneratorInterface generator) {
        this.generator = generator;
    }

    /**
     * Generates a key using the provided KeyGeneratorInterface implementation.
     *
     * @return a BitSet representing the generated key
     */
    public BitSet generateKey() {
        return generator.generateKey();
    }

    /**
     * Converts a BitSet to its hexadecimal string representation.
     *
     * @param bits the BitSet to be converted
     * @return a hexadecimal string representing the BitSet
     */
    public String bitsToHexString(BitSet bits) {
        StringBuilder sb = new StringBuilder();
        int byteLength = (bits.length() + 7) / 8;
        for (int i = 0; i < byteLength; i++) {
            byte b = 0;
            for (int j = 0; j < 8; j++) {
                if (bits.get(i * 8 + j)) {
                    b |= (byte) (1 << (7 - j));
                }
            }
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Converts a hexadecimal string to a BitSet.
     *
     * @param hex the hexadecimal string to be converted
     * @return a BitSet representing the hexadecimal string
     */
    public BitSet hexStringToBitSet(String hex) {
        BitSet bits = new BitSet(hex.length() * 4);
        for (int i = 0; i < hex.length(); i += 2) {
            byte b = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            for (int j = 0; j < 8; j++) {
                bits.set((i / 2) * 8 + j, (b & (1 << (7 - j))) != 0);
            }
        }
        return bits;
    }
}