package me.jangluzniewicz.tripledes.logic;

public class KeyGenerator implements KeyGeneratorInterface {
    @Override
    public byte[] generateKey() {
        byte[] key = new byte[128];
        for (int i = 0; i < 16; i++) {
            byte b = (byte) (Math.random() * 256);
            for (int j = 0; j < 8; j++) {
                key[i * 8 + j] = (byte) ((b >> (7 - j)) & 1);
            }
        }
        return key;
    }
}