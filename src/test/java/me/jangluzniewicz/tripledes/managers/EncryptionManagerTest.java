package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.logic.EncryptionInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EncryptionManagerTest {
    private EncryptionManager encryptionManager;

    @BeforeEach
    void setUp() {
        EncryptionInterface encryptor = new MockEncryption();
        encryptionManager = new EncryptionManager(encryptor);
    }

    @AfterEach
    void tearDown() {
        encryptionManager.shutdown();
    }

    @Test
    void testEncrypt() throws InterruptedException, ExecutionException {
        BitSet data = createBitSet("11001100");
        BitSet key1 = createKey("11110000111100001111000011110000");
        BitSet key2 = createKey("00001111000011110000111100001111");
        BitSet key3 = createKey("10101010101010101010101010101010");

        BitSet encryptedData = encryptionManager.encrypt(data, key1, key2, key3);

        BitSet expectedEncryptedData = createBitSet("11001100");
        assertEquals(expectedEncryptedData, encryptedData);
    }

    @Test
    void testDecrypt() throws InterruptedException, ExecutionException {
        BitSet encryptedData = createBitSet("11001100");
        BitSet key1 = createKey("11110000111100001111000011110000");
        BitSet key2 = createKey("00001111000011110000111100001111");
        BitSet key3 = createKey("10101010101010101010101010101010");

        BitSet decryptedData = encryptionManager.decrypt(encryptedData, key1, key2, key3);

        BitSet expectedDecryptedData = createBitSet("11001100");
        assertEquals(expectedDecryptedData, decryptedData);
    }

    @Test
    void testEncryptWithPadding() throws InterruptedException, ExecutionException {
        BitSet data = createBitSet("1100");
        BitSet key1 = createKey("11110000111100001111000011110000");
        BitSet key2 = createKey("00001111000011110000111100001111");
        BitSet key3 = createKey("10101010101010101010101010101010");

        BitSet encryptedData = encryptionManager.encrypt(data, key1, key2, key3);

        BitSet expectedEncryptedData = createBitSet("11000000");
        assertEquals(expectedEncryptedData, encryptedData);
    }

    @Test
    void testDecryptWithPadding() throws InterruptedException, ExecutionException {
        BitSet encryptedData = createBitSet("11000000");
        BitSet key1 = createKey("11110000111100001111000011110000");
        BitSet key2 = createKey("00001111000011110000111100001111");
        BitSet key3 = createKey("10101010101010101010101010101010");

        BitSet decryptedData = encryptionManager.decrypt(encryptedData, key1, key2, key3);

        BitSet expectedDecryptedData = createBitSet("11000000");
        assertEquals(expectedDecryptedData, decryptedData);
    }

    @Test
    void testShutdown() {
        encryptionManager.shutdown();
        assertTrue(encryptionManager.executor.isShutdown());
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

    private BitSet createKey(String hexString) {
        BitSet key = new BitSet(128);
        for (int i = 0; i < hexString.length(); i++) {
            int hexDigit = Character.digit(hexString.charAt(i), 16);
            for (int j = 0; j < 4; j++) {
                if ((hexDigit & (1 << (3 - j))) != 0) {
                    key.set(i * 4 + j);
                }
            }
        }
        return key;
    }

    private class MockEncryption implements EncryptionInterface {
        @Override
        public BitSet encryption(BitSet data, BitSet key) {
            return data;
        }

        @Override
        public BitSet decryption(BitSet data, BitSet key) {
            return data;
        }
    }
}