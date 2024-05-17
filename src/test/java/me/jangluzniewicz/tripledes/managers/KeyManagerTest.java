package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.KeyGeneratorInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyManagerTest {
    private KeyManager keyManager;

    @BeforeEach
    void setUp() {
        KeyGeneratorInterface keyGenerator = new MockKeyGenerator();
        keyManager = new KeyManager(keyGenerator);
    }

    @Test
    void testGenerateKey() {
        BitSet expectedKey = createBitSet("11110000111100001111000011110000");
        BitSet generatedKey = keyManager.generateKey();

        assertEquals(expectedKey, generatedKey);
    }

    @Test
    void testBitsToHexString() {
        BitSet bits = createBitSet("11110000111100001111000011110000");
        String expectedHexString = "f0f0f0f0";
        String actualHexString = keyManager.bitsToHexString(bits);

        assertEquals(expectedHexString, actualHexString);
    }

    @Test
    void testHexStringToBitSet() {
        String hexString = "f0f0f0f0";
        BitSet expectedBits = createBitSet("11110000111100001111000011110000");
        BitSet actualBits = keyManager.hexStringToBitSet(hexString);

        assertEquals(expectedBits, actualBits);
    }

    private BitSet createBitSet(String binaryString) {
        BitSet bitSet = new BitSet(binaryString.length());
        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '1') {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    private class MockKeyGenerator implements KeyGeneratorInterface {
        @Override
        public BitSet generateKey() {
            return createBitSet("11110000111100001111000011110000");
        }
    }
}