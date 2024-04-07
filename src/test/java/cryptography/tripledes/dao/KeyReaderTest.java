package cryptography.tripledes.dao;

import cryptography.tripledes.logic.KeyGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class KeyReaderTest {
    KeyReader keyReader;
    KeyGenerator keyGenerator;

    @BeforeEach
    void setUp() {
        keyReader = new KeyReader();
        keyGenerator = new KeyGenerator();
    }

    @Test
    void testReadKeys() {
        try {
            String[] keys = keyReader.readKeys("src/test/java/cryptography/tripledes/dao/keys.txt");
            assertNotNull(keys);
            assertEquals(3, keys.length);
            for (String key : keys) {
                assertNotNull(key);
                assertFalse(key.isEmpty());
            }
        } catch (Exception e) {
            fail("Error reading keys from file: " + e.getMessage());
        }
    }

    @Test
    void testWriteKeys() {
        try {
            String[] keys = new String[3];
            for (int i = 0; i < 3; i++) {
                keys[i] = keyGenerator.generateKey();
            }
            keyReader.writeKeys("src/test/java/cryptography/tripledes/dao/keys_copy.txt", keys);
            String[] keysCopy = keyReader.readKeys("src/test/java/cryptography/tripledes/dao/keys_copy.txt");
            assertArrayEquals(keys, keysCopy);
            File fileCopy = new File("src/test/java/cryptography/tripledes/dao/keys_copy.txt");
            if (fileCopy.exists()) {
                assertTrue(fileCopy.delete());
            }
        } catch (Exception e) {
            fail("Error writing keys to file: " + e.getMessage());
        }
    }
}