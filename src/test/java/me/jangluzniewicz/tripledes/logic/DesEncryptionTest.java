package me.jangluzniewicz.tripledes.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class DesEncryptionTest {
    private DesEncryption desEncryption;
    @BeforeEach
    void setUp() {
        desEncryption = new DesEncryption();
    }

    @Test
    void encryption() {
        BitSet key = Transformations.createBitSet(0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1,
                1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1);
        BitSet input = Transformations.createBitSet(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1);

        BitSet encrypted = desEncryption.encryption(input, key);
        assertNotNull(encrypted);
        assertEquals(64, encrypted.length());

        BitSet decrypted = desEncryption.decryption(encrypted, key);
        assertBitSetEquals(input, decrypted);
    }

    private void assertBitSetEquals(BitSet expected, BitSet actual) {
        assertEquals(expected.length(), actual.length());
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}
