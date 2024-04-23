package me.jangluzniewicz.tripledes.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DesEncryptionTest {
    private DesEncryption desEncryption;

    @BeforeEach
    void setUp() {
        desEncryption = new DesEncryption();
    }

    @Test
    void encryption() {
        byte[] key = { 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1,
                1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1 };
        byte[] input = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1};
        byte[] encrypted = desEncryption.encryption(input, key);
        assertNotNull(encrypted);
        assertEquals(64, encrypted.length);
        System.out.println();
        byte[] decrypted = desEncryption.decryption(encrypted, key);
        assertArrayEquals(input, decrypted);
    }
}