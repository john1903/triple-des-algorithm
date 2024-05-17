package me.jangluzniewicz.tripledes.logic;

import java.security.SecureRandom;
import java.util.BitSet;

public class KeyGenerator implements KeyGeneratorInterface {
    private static final int KEY_SIZE = 128;
    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public BitSet generateKey() {
        BitSet key = new BitSet(KEY_SIZE);
        byte[] randomBytes = new byte[KEY_SIZE / 8];
        secureRandom.nextBytes(randomBytes);

        for (int i = 0; i < randomBytes.length; i++) {
            byte b = randomBytes[i];
            for (int j = 0; j < 8; j++) {
                key.set(i * 8 + j, (b & (1 << (7 - j))) != 0);
            }
        }

        return key;
    }
}