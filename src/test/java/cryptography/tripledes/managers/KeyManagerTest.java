package cryptography.tripledes.managers;

import cryptography.tripledes.dao.KeyReader;
import cryptography.tripledes.logic.KeyGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class KeyManagerTest {
    KeyManager keyManager;

    @BeforeEach
    void setUp() {
        keyManager = new KeyManager(new KeyReader(), new KeyGenerator());
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
        byte[][] key;
        try {
            key = keyManager.readKeys("src/test/java/cryptography/tripledes/managers/keys.txt");
        } catch (Exception e) {
            throw new RuntimeException("Error writing keys to file: " + e.getMessage());
        }
        for (int i = 0; i < 3; i++) {
            assertNotNull(key[i]);
            assertEquals(64, key[i].length);
        }
    }
}