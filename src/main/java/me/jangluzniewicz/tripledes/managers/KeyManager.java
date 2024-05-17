package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.KeyGeneratorInterface;

import java.util.BitSet;

public class KeyManager {
    private final KeyGeneratorInterface generator;

    public KeyManager(KeyGeneratorInterface generator) {
        this.generator = generator;
    }

    public BitSet generateKey() {
        return generator.generateKey();
    }

    public String bitsToHexString(BitSet bits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length(); i += 8) {
            byte b = 0;
            for (int j = 0; j < 8; j++) {
                if (bits.get(i + j)) {
                    b |= (byte) (1 << (7 - j));
                }
            }
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public BitSet hexStringToBitSet(String hex) {
        BitSet bits = new BitSet(hex.length() * 4);
        for (int i = 0; i < hex.length(); i += 2) {
            byte b = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            for (int j = 0; j < 8; j++) {
                if ((b & (1 << (7 - j))) > 0) {
                    bits.set((i / 2) * 8 + j);
                }
            }
        }
        return bits;
    }
}