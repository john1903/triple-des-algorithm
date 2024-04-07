package cryptography.tripledes.managers;

import cryptography.tripledes.dao.KeyReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static cryptography.tripledes.logic.KeyGenerator.hexToBitsArray;
import static org.junit.jupiter.api.Assertions.*;

class KeyManagerTest {
    KeyManager keyManager;

    @BeforeEach
    void setUp() {
        keyManager = new KeyManager(new KeyReader());
    }

    @Test
    void generateKeys() {
        try {
            keyManager.generateKeys("src/test/java/cryptography/tripledes/managers/keys_copy.txt");
        } catch (Exception e) {
            throw new RuntimeException("Error generating keys: " + e.getMessage());
        }
        byte[][] key;
        try {
            key = keyManager.readKeys("src/test/java/cryptography/tripledes/managers/keys_copy.txt");
        } catch (Exception e) {
            throw new RuntimeException("Error reading keys from file: " + e.getMessage());
        }
        for (int i = 0; i < 3; i++) {
            assertNotNull(key[i]);
        }
        File fileCopy = new File("src/test/java/cryptography/tripledes/managers/keys_copy.txt");
        if (fileCopy.exists()) {
            assertTrue(fileCopy.delete());
        }
    }

    @Test
    void readKeys() {
        String[] keys =
                {
                "183775d35176d874",
                "e082e6891f9c4c39",
                "a61b9c471d215429"
                };
        byte[][] key;
        try {
            key = keyManager.readKeys("src/test/java/cryptography/tripledes/managers/keys.txt");
        } catch (Exception e) {
            throw new RuntimeException("Error writing keys to file: " + e.getMessage());
        }
        for (int i = 0; i < 3; i++) {
            assertNotNull(key[i]);
            assertArrayEquals(key[i], hexToBitsArray(keys[i]));
        }
    }
}