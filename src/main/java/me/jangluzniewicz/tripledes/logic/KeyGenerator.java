package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

public class KeyGenerator implements KeyGeneratorInterface {
    @Override
    public BitSet generateKey() {
        BitSet key = new BitSet(128);
        for (int i = 0; i < 16; i++) {
            byte b = (byte) (Math.random() * 256);
            for (int j = 0; j < 8; j++) {
                if (((b >> (7 - j)) & 1) == 1) {
                    key.set(i * 8 + j);
                }
            }
        }
        return key;
    }
}