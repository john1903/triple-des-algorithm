package me.jangluzniewicz.tripledes.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorTest {
    KeyGenerator keyGenerator;

    @BeforeEach
    void setUp() {
        keyGenerator = new KeyGenerator();
    }

    @Test
    void hexStringToBits() {
        byte[] bits = keyGenerator.convertKeyToBits(keyGenerator.generateKey());
        assertNotNull(bits);
        assertEquals(64, bits.length);
        for (byte bit : bits) {
            assertTrue(bit == 0 || bit == 1);
        }
    }

    @Test
    void generateKey() {
        String key = keyGenerator.generateKey();
        assertNotNull(key);
        assertEquals(16, key.length());
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            assertTrue(Character.isDigit(c) || (c >= 'a' && c <= 'f'));
        }
    }
}