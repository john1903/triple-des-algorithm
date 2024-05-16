package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.KeyGeneratorInterface;

public class KeyManager {
    private final KeyGeneratorInterface generator;

    public KeyManager(KeyGeneratorInterface generator) {
        this.generator = generator;
    }

    public byte[] generateKey() {
        return generator.generateKey();
    }

    public String bitsToHexString(byte[] bits) {
        byte[] bytes = new byte[bits.length / 8];
        for (int i = 0; i < bits.length; i += 8) {
            byte b = 0;
            for (int j = 0; j < 8; j++) {
                b = (byte) (b << 1);
                b = (byte) (b | bits[i + j]);
            }
            bytes[i / 8] = b;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public byte[] hexStringToBits(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        }
        byte[] bits = new byte[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bits[i * 8 + j] = (byte) ((bytes[i] >> (7 - j)) & 1);
            }
        }
        return bits;
    }
}
