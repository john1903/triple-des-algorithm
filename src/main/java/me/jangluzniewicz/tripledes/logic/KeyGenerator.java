package me.jangluzniewicz.tripledes.logic;

import java.security.SecureRandom;
import java.util.BitSet;

/**
 * The KeyGenerator class implements the KeyGeneratorInterface and provides a method
 * to generate a secure random key for encryption purposes.
 */
public class KeyGenerator implements KeyGeneratorInterface {
    private static final int KEY_SIZE = 128;  // Size of the key in bits
    private static final SecureRandom secureRandom = new SecureRandom();  // Secure random number generator

    /**
     * Generates a secure random key of size KEY_SIZE bits.
     *
     * @return a BitSet representing the generated key
     */
    @Override
    public BitSet generateKey() {
        BitSet key = new BitSet(KEY_SIZE);
        byte[] randomBytes = new byte[KEY_SIZE / 8];
        secureRandom.nextBytes(randomBytes);  // Generate random bytes

        // Convert each byte to bits and set them in the BitSet
        for (int i = 0; i < randomBytes.length; i++) {
            byte b = randomBytes[i];
            for (int j = 0; j < 8; j++) {
                key.set(i * 8 + j, (b & (1 << (7 - j))) != 0);
            }
        }

        return key;
    }
}