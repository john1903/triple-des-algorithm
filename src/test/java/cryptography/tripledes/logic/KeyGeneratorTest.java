package cryptography.tripledes.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorTest {
    @Test
    void hexStringToBits() {
        byte[] bits = KeyGenerator.hexStringToBits(KeyGenerator.generateKey());
        assertNotNull(bits);
        assertEquals(64, bits.length);
        for (byte bit : bits) {
            assertTrue(bit == 0 || bit == 1);
        }
    }

    @Test
    void generateKey() {
        String key = KeyGenerator.generateKey();
        assertNotNull(key);
        assertEquals(16, key.length());
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            assertTrue(Character.isDigit(c) || (c >= 'a' && c <= 'f'));
        }
    }
}